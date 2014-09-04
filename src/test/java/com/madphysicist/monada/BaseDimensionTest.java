/*
 * BaseDimensionTest.java (TestClass: com.madphysicist.monada.BaseDimensionTest)
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

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

/**
 * Tests each of the methods of {@link BaseDimension} except {@code toString()}. The string representation is provided
 * for debugging and is not guaranteed to match anything. The trivial getters ({@code name()}, {@code description()},
 * and {@code isNull()}) are tested along with the constructors.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 30 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 * @since 1.0
 */
public class BaseDimensionTest
{
    /**
     * The default name to use for test dimensions. This is guaranteed to be a non-{@code null} string that is different
     * from {@link #DEFAULT_DESCRIPTION}.
     *
     * @since 1.0.0
     */
    private static final String DEFAULT_NAME = "TestDimension";

    /**
     * The default description to use for test dimensions that have a description. This is guaranteed to be a non-{@code
     * null} string that is different from {@link #DEFAULT_NAME}.
     *
     * @since 1.0.0
     */
    private static final String DEFAULT_DESCRIPTION = "A Dimension for Testing";

    /**
     * A generic dimension that can be used for testing. This dimension has a description and is not a null dimension.
     * It is only initialized once by {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private BaseDimension someDimension;

    /**
     * Initializes the fields of this class for use by the tests. Specifically, {@link #someDimension} is initialized
     * to have a non-{@code null} description and a null status of {@code false}. Assertions are made that {@link
     * #DEFAULT_NAME} and {@link #DEFAULT_DESCRIPTION} are non-{@code null} and not equal to each other.
     *
     * @since 1.0.0
     */
    @BeforeClass
    private void beforeClass()
    {
        Assert.assertNotNull(DEFAULT_NAME);
        Assert.assertNotNull(DEFAULT_DESCRIPTION);
        Assert.assertNotEquals(DEFAULT_NAME, DEFAULT_DESCRIPTION);

        someDimension = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        Assert.assertNotNull(someDimension.description());
        Assert.assertFalse(someDimension.isNull());
    }

    /**
     * Checks that the one-argument constructor fails when passes a {@code null} name. The expected failure is a {@code
     * NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void nameOnlyConstructorNullTest() throws NullPointerException
    {
        new BaseDimension(null);
        Assert.fail();
    }

    /**
     * Checks that the one-argument constructor initializes an instance with the expected properties. The name is
     * expected to be the one passed in. The description is expected to be {@code null} and the null status is expected
     * to be {@code false}.
     *
     * @since 1.0.0
     */
    @Test
    public void nameOnlyConstructorTest()
    {
        BaseDimension dim = new BaseDimension(DEFAULT_NAME);
        Assert.assertSame(dim.name(), DEFAULT_NAME);
        Assert.assertNull(dim.description());
        Assert.assertFalse(dim.isNull());
    }

    /**
     * Checks that the two-argument name and description constructor fails when passes a {@code null} name. The expected
     * failure is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void nameDescriptionConstructorNullTest() throws NullPointerException
    {
        new BaseDimension(null, DEFAULT_DESCRIPTION);
        Assert.fail();
    }

    /**
     * Checks that the two-argument name and description constructor initializes an instance as expected. The name and
     * description are expected to be the ones that the object was initialized with. The null status is expected to be
     * {@code false}. This test checks both {@code null} and non-{@code null} descriptions.
     *
     * @since 1.0.0
     */
    @Test
    public void nameDescriptionConstructorTest()
    {
        BaseDimension dim1 = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        Assert.assertSame(dim1.name(), DEFAULT_NAME);
        Assert.assertSame(dim1.description(), DEFAULT_DESCRIPTION);
        Assert.assertFalse(dim1.isNull());

        BaseDimension dim2 = new BaseDimension(DEFAULT_NAME, null);
        Assert.assertSame(dim2.name(), DEFAULT_NAME);
        Assert.assertNull(dim2.description());
        Assert.assertFalse(dim2.isNull());
    }

    /**
     * Checks that the two-argument name and null status constructor fails when passes a {@code null} name. The expected
     * failure is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void nameNullConstructorNullTest() throws NullPointerException
    {
        new BaseDimension(null, true);
        Assert.fail();
    }

    /**
     * Checks that the two-argument name and null status constructor initializes an instance as expected. The name and
     * null status are expected to be the ones that the object was initialized with. The description is expected to be
     * {@code null}.
     *
     * @since 1.0.0
     */
    @Test
    public void nameNullConstructorTest()
    {
        BaseDimension dim1 = new BaseDimension(DEFAULT_NAME, true);
        Assert.assertSame(dim1.name(), DEFAULT_NAME);
        Assert.assertNull(dim1.description());
        Assert.assertTrue(dim1.isNull());

        BaseDimension dim2 = new BaseDimension(DEFAULT_NAME, false);
        Assert.assertSame(dim2.name(), DEFAULT_NAME);
        Assert.assertNull(dim2.description());
        Assert.assertFalse(dim2.isNull());
    }

    /**
     * Checks that the full constructor fails when passes a {@code null} name. The expected failure is a {@code
     * NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void fullConstructorNullTest() throws NullPointerException
    {
        new BaseDimension(null, DEFAULT_DESCRIPTION, true);
        Assert.fail();
    }

    /**
     * Provides data to test the full constructor. The following scenarios are tested:
     * <table border=1>
     *   <tr><th>Name</th><th>Description</th><th>isNull</th></tr>
     *   <tr><td>non-null</td><td>null</td><td>false</td></tr>
     *   <tr><td>non-null</td><td>null</td><td>true</td></tr>
     *   <tr><td>non-null</td><td>non-null</td><td>false</td></tr>
     *   <tr><td>non-null</td><td>non-null</td><td>true</td></tr>
     * </table>
     *
     * @return an array of name-description-isNull arrays containing inputs to the full constructor.
     * @since 1.0.0
     */
    @DataProvider(name = "fullConstructorData")
    protected Object[][] fullConstructorData()
    {
        return new Object[][] {
                {DEFAULT_NAME, null, false},
                {DEFAULT_NAME, null, true},
                {DEFAULT_NAME, DEFAULT_DESCRIPTION, false},
                {DEFAULT_NAME, DEFAULT_DESCRIPTION, true}
        };
    }

    /**
     * Checks that the full constructor initializes an instance as expected. The name, description and null status are
     * expected to be the ones that the object was initialized with.
     *
     * @since 1.0.0
     */
    @Test(dataProvider = "fullConstructorData")
    public void fullConstructorTest(String name, String description, boolean isNull)
    {
        BaseDimension dim = new BaseDimension(name, description, isNull);
        Assert.assertSame(dim.name(), name);
        Assert.assertSame(dim.description(), description);
        Assert.assertEquals(dim.isNull(), isNull);
    }

    @Test(dataProvider = "compareComponentsEqualData")
    public void compareComponentsEqualTest(Dimension firstDimension, Dimension secondDimension)
    {
        Assert.assertEquals(firstDimension.compareComponents(secondDimension), 0);
        if(firstDimension != secondDimension)
            Assert.assertEquals(secondDimension.compareComponents(firstDimension), 0);
    }

    @Test(dataProvider = "compareComponentsUnequalData")
    public void compareComponentsUnequalTest(Dimension firstDimension, Dimension secondDimension)
    {
        Assert.assertTrue(firstDimension.compareComponents(secondDimension) > 0);
        Assert.assertTrue(secondDimension.compareComponents(firstDimension) < 0);
    }

    @Test
    public void compareToTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    /**
     * Checks that the component count is {@code 1}.
     *
     * @since 1.0.0
     */
    @Test
    public void componentCountTest()
    {
        Assert.assertEquals(someDimension.componentCount(), 1);
    }

    @Test
    public void equalsTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    /**
     * Checks that two dimensions initialized with the same parameters have the same has code.
     *
     * @since 1.0.0
     */
    @Test
    public void hashCodeTest()
    {
        BaseDimension dim1 = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true);
        BaseDimension dim2 = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true);

        Assert.assertNotSame(dim1, dim2);
        Assert.assertEquals(dim1, dim2);
        Assert.assertEquals(dim1.hashCode(), dim2.hashCode());
    }

    /**
     * Checks that the iterator over a {@code BaseDimension} contains only one {@code DimensionComponent}, and that that
     * component is linear in the dimension itself.
     *
     * @since 1.0.0
     */
    @Test
    public void iteratorTest()
    {
        // Make iterator
        Iterator<DimensionComponent> iterator = someDimension.iterator();

        // Check that it has at least one item
        Assert.assertTrue(iterator.hasNext());

        // Get that item and test its properties
        DimensionComponent component = iterator.next();
        Assert.assertEquals(component.exponent(), 1.0f);
        Assert.assertSame(component.dimension(), someDimension);

        // Check that it had exactly one item
        Assert.assertFalse(iterator.hasNext());
    }
}
