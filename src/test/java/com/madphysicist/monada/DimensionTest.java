/*
 * DimensionTest.java (TestClass: com.madphysicist.monada.DimensionTest)
 *
 * Mad Physicist Monada Project (Unit Conversion Library)
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 by Joseph Fox-Rabinovitz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.madphysicist.monada;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

/**
 * Tests each of the methods of {@link com.madphysicist.monada.Dimension} except {@code toString()}. The string
 * representation is provided for debugging and is not guaranteed to match anything.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 30 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 */
public class DimensionTest
{
    /**
     * The default name to use for test dimensions. This is guaranteed to be a non-{@code null} string that is different
     * from {@link #DEFAULT_DESCRIPTION}.
     *
     * @since 1.0.0
     */
    public static final String DEFAULT_NAME = "TestDimension";

    /**
     * The default description to use for test dimensions that have a description. This is guaranteed to be a non-{@code
     * null} string that is different from {@link #DEFAULT_NAME}.
     *
     * @since 1.0.0
     */
    public static final String DEFAULT_DESCRIPTION = "A Dimension for testing";

    /**
     * A {@link TestDimension} that is constructed using the two-argument constructor. The name is set to {@link
     * #DEFAULT_NAME} and the description is set to {@link #DEFAULT_DESCRIPTION}. This reference is initialized only
     * once by {@link #beforeClass()} because it is immutable.
     *
     * @since 1.0.0
     */
    private Dimension twoArgDimension;

    /**
     * A {@link TestDimension} that is constructed using the one-argument constructor. The name is set to {@link
     * #DEFAULT_NAME}. This reference is initialized only once by {@link #beforeClass()} because it is immutable.
     *
     * @since 1.0.0
     */
    private Dimension oneArgDimension;

    /**
     * A {@link TestDimension} that is constructed using the two-argument constructor without a description. The name is
     * set to {@link #DEFAULT_NAME} and the description is set to {@code null}. This reference is initialized only once
     * by {@link #beforeClass()} because it is immutable.
     *
     * @since 1.0.0
     */
    private Dimension noDescDimension;

    /**
     * Sets up the private fields of this class for use by all the tests. The following fields are initialized:
     * <ul>
     *   <li>{@link #twoArgDimension}</li>
     *   <li>{@link #oneArgDimension}</li>
     *   <li>{@link #noDescDimension}</li>
     * </ul>
     * Assertions are made that {@link #DEFAULT_NAME} and {@link #DEFAULT_DESCRIPTION} are non-{@code null} and not
     * equal to each other.
     * 
     * @since 1.0.0
     */
    @BeforeClass
    private void beforeClass()
    {
        Assert.assertNotNull(DEFAULT_NAME);
        Assert.assertNotNull(DEFAULT_DESCRIPTION);
        Assert.assertNotEquals(DEFAULT_NAME, DEFAULT_DESCRIPTION);

        twoArgDimension = new TestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        oneArgDimension = new TestDimension(DEFAULT_NAME);
        noDescDimension = new TestDimension(DEFAULT_NAME, null);
    }

    /**
     * Checks that the one-argument constructor fails when passes a {@code null} name. The expected failure is a {@code
     * NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void DimensionOneArgNullTest() throws NullPointerException
    {
        new TestDimension(null);
        Assert.fail();
    }

    /**
     * Checks that a dimension initialized with the one-argument constructor has the expected name and description. The
     * name is expected to be the input name and the description is expected to be {@code null}.
     *
     * @since 1.0.0
     */
    @Test
    public void DimensionOneArgTest()
    {
        Assert.assertSame(oneArgDimension.name(), DEFAULT_NAME);
        Assert.assertNull(oneArgDimension.description());
    }

    /**
     * Checks that the two-argument constructor fails when passes a {@code null} name but non-null description. The
     * expected failure is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void DimensionTwoArgNullTest() throws NullPointerException
    {
        new TestDimension(null, DEFAULT_DESCRIPTION);
        Assert.fail();
    }

    /**
     * Checks that a dimension initialized with the tow-argument constructor has the expected name and description. The
     * name is expected to be the input name and the description is expected to be the input description, even when it
     * is {@code null}.
     *
     * @since 1.0.0
     */
    @Test
    public void DimensionTwoArgTest()
    {
        Assert.assertSame(twoArgDimension.name(), DEFAULT_NAME);
        Assert.assertSame(twoArgDimension.description(), DEFAULT_DESCRIPTION);

        Assert.assertSame(noDescDimension.name(), DEFAULT_NAME);
        Assert.assertNull(noDescDimension.description());
    }

    @DataProvider(name = "compareComponentsData")
    protected Object[][] compareComponentsData()
    {
        Dimension otherDimension = new TestDimension(DEFAULT_NAME, DEFAULT_NAME);
        return new Object[][] {
                {oneArgDimension, oneArgDimension},
                {twoArgDimension, twoArgDimension},
                {noDescDimension, noDescDimension},
                {oneArgDimension, new TestDimension(DEFAULT_DESCRIPTION)},
                {noDescDimension, new TestDimension(DEFAULT_DESCRIPTION, null)},
                {twoArgDimension, new TestDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION)},
                {twoArgDimension, oneArgDimension},
                {twoArgDimension, otherDimension},
                {oneArgDimension, otherDimension},
                {noDescDimension, otherDimension}
        };
    }

    /**
     * Checks that component comparison is not affected by the name and description of the dimension. Specifically,
     * the following statements are verified using {@code TestDimension}s:
     * </ul>
     *   <li>A dimension is equal to itself</li>
     *   <li>The name of the dimensions does not affect component comparison</li>
     *   <li>The description of the dimensions does not affect component comparison</li>
     * </ul>
     * Additional verification of the {@code compareComponents()} method is provided by {@link
     * BaseDimensionTest#compareComponentsTest()} and {@link DerivedDimensionTest#compareComponentsTest()}. The latter
     * in particular is useful because it verifies the comparison with a non-trivial set of dimension components.
     *
     * @param firstDimension the first dimension to compare the dimensions are compared as {@code
     * firstDimension.compareComponents(secondDimension)}.
     * @param secondDimension the second dimension to compare. If {@code firstDimension != secondDimension}, then an
     * additional comparison is performed to demonstrate symmetry: {@code
     * secondDimension.compareComponents(firstDimension)}.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareComponentsData")
    public void compareComponentsTest(Dimension firstDimension, Dimension secondDimension)
    {
        Assert.assertEquals(firstDimension.compareComponents(secondDimension), 0);
        if(firstDimension != secondDimension)
            Assert.assertEquals(secondDimension.compareComponents(firstDimension), 0);
    }

    @Test
    public void compareToTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void descriptionTest()
    {
        Assert.assertSame(twoArgDimension.description(), DEFAULT_DESCRIPTION);
        Assert.assertNull(oneArgDimension.description());
        Assert.assertNull(noDescDimension.description());
    }

    @Test
    public void equalsTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void hashCodeTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void nameTest()
    {
        Assert.assertSame(twoArgDimension.name(), DEFAULT_NAME);
        Assert.assertSame(oneArgDimension.name(), DEFAULT_NAME);
        Assert.assertSame(noDescDimension.name(), DEFAULT_NAME);
    }

    @Test
    public void combineDimensionComponentsTest()
    {
        throw new RuntimeException("Test not implemented");
    }
}
