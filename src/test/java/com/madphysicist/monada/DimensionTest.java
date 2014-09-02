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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("Before class!");
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

    /**
     * Provides data for {@link #compareComponentsTest(Dimension, Dimension)}. None of the dimensions returned by this
     * data provider actually contain any components. The goal is to demonstrate that differences in the name and
     * description (which affect both {@code equals()} and {@code compareTo()}) do not affect {@code
     * compareComponents()}. The following pairs are tested, all of which are expected to be equal component-wise:
     * <ul>
     *   <li>Two identical dimensions</li>
     *   <li>Two dimensions with different names, null descriptions</li>
     *   <li>Two dimensions with different names, one null, one non-null description</li>
     *   <li>Two dimensions with different names, different but non-null descriptions</li>
     *   <li>Two dimensions with the same name, one null, one non-null description</li>
     *   <li>Two dimensions with the same name, different but non-null descriptions</li>
     * </ul>
     *  
     * @return a list of dimension pairs to test with.
     * @since 1.0.0
     */
    @DataProvider(name = "compareComponentsData")
    protected Object[][] compareComponentsData()
    {
        Dimension otherDimension = new TestDimension(DEFAULT_NAME, DEFAULT_NAME);
        return new Object[][] {
                {oneArgDimension, oneArgDimension},
                {twoArgDimension, twoArgDimension},
                {noDescDimension, noDescDimension},
                {oneArgDimension, new TestDimension(DEFAULT_DESCRIPTION)},
                {twoArgDimension, new TestDimension(DEFAULT_DESCRIPTION)},
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

    @DataProvider(name = "compareToEqualData")
    protected Object[][] compareToEqulaData()
    {
        Dimension equalDimension = new TestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        System.out.println("compareToEqualsData!");
        System.out.println("    1: " + oneArgDimension);
        System.out.println("    2: " + twoArgDimension);
        System.out.println("    N: " + noDescDimension);
        System.out.println("    =: " + equalDimension);
        return new Object[][] {
                {oneArgDimension, oneArgDimension, true},
                {twoArgDimension, twoArgDimension, true},
                {oneArgDimension, noDescDimension, false},
                {twoArgDimension, equalDimension,  false},
        };
    }

    /**
     * Verifies that the {@code compareTo()} method compares equal instances correctly. This method only tests equal
     * dimensions. The test is performed both ways for thoroughness: both {@code dim1.compareTo(dim2) == 0} and {@code
     * dim2.compareTo(dim1) == 0} must be true. This test also double-checks consistency with {@code equals()}. A
     * similar check for unequal dimensions is done in {@link #compareToUnequalTest()}.
     *
     * @param dim1 the first dimension to compare.
     * @param dim2 the second dimension to compare.
     * @param same a flag indicating whether or not the two dimensions are the same reference. The appropriate assertion
     * (same or not same) is made to double-check this flag.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareToEqualData")
    public void compareToEqualTest(Dimension dim1, Dimension dim2, boolean same)
    {
        System.out.println("compareToEqualsTest!");
        if(same)
            Assert.assertSame(dim1, dim2);
        else
            Assert.assertNotSame(dim1, dim2);
        Assert.assertEquals(dim1, dim2);
        Assert.assertEquals(dim2, dim2);
        Assert.assertEquals(dim1.compareTo(dim2), 0);
        Assert.assertEquals(dim2.compareTo(dim1), 0);
    }

    @DataProvider(name = "compareToUnequalData")
    protected Object[][] compareToUnequalData()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Verifies that the {@code compareTo()} method compares instances with different names, descriptions and types
     * correctly. This method only tests unequal dimensions. The test is performed both ways for thoroughness: both
     * {@code greaterDim.compareTo(smallerDim) > 0} and {@code smallerDim.compareTo(greaterDim) < 0} must be true. This
     * test also verifies consistency with {@code equals()}: all comparisons must return {@code false}. A similar check
     * for equal dimensions is done in {@link #compareToEqualTest()}.
     *
     * @param greaterDim the larger of the two dimensions.
     * @param smallerDim the smaller of the two dimensions.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareToUnequalData")
    public void compareToUnequalTest(Dimension greaterDim, Dimension smallerDim)
    {
        Assert.assertTrue(greaterDim.compareTo(smallerDim) < 0);
        Assert.assertTrue(smallerDim.compareTo(greaterDim) > 0);
        Assert.assertFalse(greaterDim.equals(smallerDim));
        Assert.assertFalse(smallerDim.equals(greaterDim));
    }

    /**
     * Checks that the description of a dimension is the one it was initialized with. Dimensions initialized with the
     * one-argument constructor are expected to have a {@code null} description.
     *
     * @since 1.0.0
     */
    @Test
    public void descriptionTest()
    {
        Assert.assertSame(twoArgDimension.description(), DEFAULT_DESCRIPTION);
        Assert.assertNull(oneArgDimension.description());
        Assert.assertNull(noDescDimension.description());
    }

    /**
     * Checks that the {@code equals()} method returns expected results. The following scenarios are tested:
     * <table>
     *   <tr><th>Scenario Description</th><th>Expected Result</th></tr>
     *   <tr><td>Equal names, null descriptions</td><td>T</td></tr>
     *   <tr><td>Equal names, equal non-null descriptions</td><td>T</td></tr>
     *   <tr><td>Different names, both descriptions null</td><td>F</td></tr>
     *   <tr><td>Same name, one description null, one not</td><td>F</td></tr>
     *   <tr><td>Same name, descriptions different but non-null</td><td>F</td></tr>
     *   <tr><td>Different names, one description null, one not</td><td>F</td></tr>
     *   <tr><td>Different names, descriptions different but non-null</td><td>F</td></tr>
     * </table>
     *
     * @since 1.0.0
     */
    @Test
    public void equalsTest()
    {
    	// same name, null description
        Assert.assertEquals(oneArgDimension, noDescDimension);
    	// same name, same description
        Assert.assertEquals(twoArgDimension, new TestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION));

    	// diff name, null description
        Assert.assertNotEquals(oneArgDimension, new TestDimension(DEFAULT_DESCRIPTION));
    	// same name, one null description
        Assert.assertNotEquals(oneArgDimension, twoArgDimension);
    	// same name, diff description
        Assert.assertNotEquals(twoArgDimension, new TestDimension(DEFAULT_NAME, DEFAULT_NAME));
        // diff name, one null description
        Assert.assertNotEquals(twoArgDimension, new TestDimension(DEFAULT_DESCRIPTION));
        // diff name, diff description
        Assert.assertNotEquals(twoArgDimension, new TestDimension(DEFAULT_DESCRIPTION, DEFAULT_NAME));
    }

    /**
     * Checks that the hash code of two equal dimensions is the same.
     *
     * @since 1.0.0
     */
    @Test
    public void hashCodeTest()
    {
    	Assert.assertEquals(oneArgDimension.hashCode(), noDescDimension.hashCode());
    }

    /**
     * Checks that the name of the dimension is the one it was initialized with.
     *
     * @since 1.0.0
     */
    @Test
    public void nameTest()
    {
        Assert.assertSame(twoArgDimension.name(), DEFAULT_NAME);
        Assert.assertSame(oneArgDimension.name(), DEFAULT_NAME);
        Assert.assertSame(noDescDimension.name(), DEFAULT_NAME);
    }

    /**
     * Checks that dimension components are added to the map properly. Dimension components are added to an empty map
     * with exponent factors varying from {@code -2.0} to {@code 2.0} in fractional steps of {@code 0.5}. A check is
     * done to verify that they get added correctly. The components are added again with an exponent factor that is the
     * negative of the original factor. The map is then expected to be empty. When added with an exponent factor of
     * zero, the map is expected to stay empty during all stages of the check.
     *
     * @since 1.0.0
     */
    @Test
    public void combineDimensionComponentsTest()
    {
        // Create dimensions
        BaseDimension length = new BaseDimension("Length");
        BaseDimension time = new BaseDimension("Time");
        BaseDimension angle = new BaseDimension("Angle");

        // Create components
        DimensionComponent lengthComponent = new DimensionComponent(length);
        DimensionComponent timeComponent = new DimensionComponent(time, -1.0f);
        DimensionComponent angleComponent = new DimensionComponent(angle, 2.0f);

        // Create list
        List<DimensionComponent> componentList = Arrays.asList(new DimensionComponent[] {lengthComponent, timeComponent, angleComponent});

        Map<BaseDimension, DimensionComponent> testMap = new HashMap<>();
        for(float eFactor = -2.0f; eFactor <= 2.0f; eFactor += 0.5f) {
            Assert.assertTrue(testMap.isEmpty());
            // Add stuff to map
            for(DimensionComponent component : componentList) {
                Dimension.combineDimensionComponents(testMap, component, eFactor);
            }

            // Check that everything was added 
            if(eFactor == 0.0f) {
                Assert.assertEquals(testMap.size(), 0);
            } else {
                Assert.assertEquals(testMap.size(), 3);
                for(DimensionComponent component : componentList) {
                    DimensionComponent actual = testMap.get(component.dimension());
                    Assert.assertSame(actual.dimension(), component.dimension());
                    Assert.assertEquals(actual.exponent(), component.exponent() * eFactor);
                }
            }

            // Add stuff with negative exponent
            for(DimensionComponent component : componentList) {
                Dimension.combineDimensionComponents(testMap, component, -eFactor);
            }
        }
        Assert.assertTrue(testMap.isEmpty()); // Wrap-up call for symmetry
    }
}
