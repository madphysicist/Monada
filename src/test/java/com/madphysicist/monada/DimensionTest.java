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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
 * representation is provided for debugging and is not guaranteed to match anything. The trivial getters ({@code name()}
 * and {@code description()}) are tested along with the constructor.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 30 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 * @since 1.0
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
     * A dimension that is the same as {@link #oneArgDimension} and {@link #noDescDimension} in most ways, except that
     * it has a different class. The name, description and components are the same. The class difference is implemented
     * through the private inner type {@link #InnerTestDimension}. This reference is initialized only once by {@link
     * #beforeClass()} because it is immutable.
     *
     * @since 1.0.0
     */
    private Dimension differentOneDimension;

    /**
     * A dimension that is the same as {@link #twoArgDimension} in most ways, except that it has a different class. The
     * name, description and components are the same. The class difference is implemented through the private inner
     * type {@link #InnerTestDimension}. This reference is initialized only once by {@link #beforeClass()} because it is
     * immutable.
     *
     * @since 1.0.0
     */
    private Dimension differentTwoDimension;

    /**
     * Sets up the private fields of this class for use by all the tests. The following fields are initialized:
     * <ul>
     *   <li>{@link #twoArgDimension}</li>
     *   <li>{@link #oneArgDimension}</li>
     *   <li>{@link #noDescDimension}</li>
     *   <li>{@link #differentOneDimension}</li>
     *   <li>{@link #differentTwoDimension}</li>
     * </ul>
     * Assertions are made that {@link #DEFAULT_NAME} and {@link #DEFAULT_DESCRIPTION} are non-{@code null} and not
     * equal to each other. The name, description and class properties of the "different*Dimension" dimensions are also
     * verified. Only the class properties should be different from the corresponding "*ArgDimension" dimensions.
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

        differentOneDimension = new InnerTestDimension(DEFAULT_NAME);
        differentTwoDimension = new InnerTestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);

        Assert.assertSame(oneArgDimension.name(), differentOneDimension.name());
        Assert.assertSame(oneArgDimension.description(), differentOneDimension.description());
        Assert.assertNotEquals(oneArgDimension.getClass(), differentOneDimension.getClass());

        Assert.assertSame(twoArgDimension.name(), differentTwoDimension.name());
        Assert.assertSame(twoArgDimension.description(), differentTwoDimension.description());
        Assert.assertNotEquals(twoArgDimension.getClass().getName(), differentTwoDimension.getClass().getName());
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
     * Checks that {@code compareComponents()} fails if a {@code null} reference is passed in. The expected failure is a
     * {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void compareComponentsNullTest() throws NullPointerException
    {
        twoArgDimension.compareComponents(null);
        Assert.fail();
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
     * @return an array of dimension pairs to test {@code compareComponents()} againts.
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

    /**
     * Checks that {@code compareTo()} fails if a {@code null} reference is passed in. The expected failure is a {@code
     * NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void compareToNullTest() throws NullPointerException
    {
        twoArgDimension.compareTo(null);
        Assert.fail();
    }

    /**
     * Provides data for {@link #compareToEqualTest()} to verify that dimensions that are expected to be equal are in
     * fact equal. Both identical and non-identical instances are tested.
     *
     * @return an array of equal dimension pairs.
     * @since 1.0.0
     */
    @DataProvider(name = "compareToEqualData")
    protected Object[][] compareToEqualData()
    {
        Dimension equalDimension = new TestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        return new Object[][] {
                {oneArgDimension, oneArgDimension},
                {twoArgDimension, twoArgDimension},
                {oneArgDimension, noDescDimension},
                {twoArgDimension, equalDimension},
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
    public void compareToEqualTest(Dimension dim1, Dimension dim2)
    {
        Assert.assertEquals(dim1, dim2);
        Assert.assertEquals(dim2, dim2);
        Assert.assertEquals(dim1.compareTo(dim2), 0);
        Assert.assertEquals(dim2.compareTo(dim1), 0);
    }

    /**
     * Provides data for {@link #compareToUnequalTest()} to verify that dimensions that are expected to be unequal are
     * in fact unequal. This data provider implements scenarios in which the name, description and class name are
     * different. The following is a list of the scenarios tested:
     * <table border="1">
     *   <tr><th>Name</th><th>Description</th><th>Class</th></tr>
     *   <tr><td>{@literal >}</td><td>=</td><td>=</td></tr>
     *   <tr><td>=</td><td>{@literal >}</td><td>=</td></tr>
     *   <tr><td>=</td><td>=</td><td>{@literal >}</td></tr>
     *   <tr><td>{@literal >}</td><td>{@literal >}</td><td>=</td></tr>
     *   <tr><td>{@literal >}</td><td>=</td><td>{@literal >}</td></tr>
     *   <tr><td>=</td><td>{@literal >}</td><td>{@literal >}</td></tr>
     *   <tr><td>{@literal >}</td><td>{@literal >}</td><td>{@literal >}</td></tr>
     * </table>
     * Additional tests check the order in which properties are tested:
     * <table border="1">
     *   <tr><th>Name</th><th>Description</th><th>Class</th></tr>
     *   <tr><td>{@literal >}</td><td>{@literal <}</td><td>{@literal <}</td></tr>
     *   <tr><td>{@literal >}</td><td>{@literal =}</td><td>{@literal <}</td></tr>
     *   <tr><td>{@literal >}</td><td>{@literal <}</td><td>{@literal =}</td></tr>
     *   <tr><td>=</td><td>{@literal >}</td><td>{@literal <}</td></tr>
     * </table>
     * The {@code {@literal >}} symbol indicates that the first dimension in the pair has this property greater,
     * while {@literal <} indicates a smaller vaule for the property. {@code =} means that the properties are the same
     * for both dimensions in the pair.
     *
     * @return an array of unequal dimension pairs to test {@code compareTo()} against. The greater dimension is always
     * the first of the pair.
     * @throws NoSuchMethodException if a two {@code String} constructor for either sub-type of {@code Dimension} being
     * tested with can not be found for some reason. This is not an expected condition and will cause failure. 
     * @throws SecurityException if a constructor for either sub-type of {@code Dimension} being tested with can not be
     * acquired. This is not an expected condition and will cause failure.
     * @throws InstantiationException if one of the sub-type constructors for {@code Dimension} can not be invoked for
     * some reason. This is not an expected condition and will cause failure.
     * @throws IllegalAccessException if one of the sub-type constructors for {@code Dimension} can not be invoked for
     * some reason. This is not an expected condition and will cause failure.
     * @throws IllegalArgumentException if one of the sub-type constructors for {@code Dimension} is passed an argument
     * of an invalid type for some reason. All constructors should accept two {@code String} arguments. This is not an
     * expected condition and will cause failure.
     * @throws InvocationTargetException if one of the sub-type constructors for {@code Dimension} throws an exception
     * when it is called. This is not an expected condition and will cause failure.
     * @since 1.0.0
     */
    @DataProvider(name = "compareToUnequalData")
    protected Object[][] compareToUnequalData() throws NoSuchMethodException, SecurityException,
                                                       InstantiationException, IllegalAccessException,
                                                       IllegalArgumentException, InvocationTargetException
    {
        String bigStr, lilStr;
        Class<? extends Dimension> bigClass, lilClass;
        Constructor<? extends Dimension> bigConstr, lilConstr;

        if(DEFAULT_NAME.compareTo(DEFAULT_DESCRIPTION) > 0) {
            bigStr = DEFAULT_NAME;
            lilStr = DEFAULT_DESCRIPTION;
        } else {
            bigStr = DEFAULT_DESCRIPTION;
            lilStr = DEFAULT_NAME;
        }

        if(TestDimension.class.getName().compareTo(InnerTestDimension.class.getName()) > 0) {
            bigClass = TestDimension.class;
            lilClass = InnerTestDimension.class;
        } else {
            bigClass = InnerTestDimension.class;
            lilClass = TestDimension.class;
        }
        bigConstr = bigClass.getConstructor(String.class, String.class);
        lilConstr = lilClass.getConstructor(String.class, String.class);

        return new Object[][] {
                // Basic: no less-thans
                {bigConstr.newInstance(bigStr, null),   bigConstr.newInstance(lilStr, null)},   // >, =, =
                {lilConstr.newInstance(bigStr, lilStr), lilConstr.newInstance(lilStr, lilStr)}, // >, =, =
                {bigConstr.newInstance(lilStr, lilStr), bigConstr.newInstance(lilStr, null)},   // =, >, =
                {lilConstr.newInstance(bigStr, bigStr), lilConstr.newInstance(bigStr, lilStr)}, // =, >, =
                {bigConstr.newInstance(lilStr, null),   lilConstr.newInstance(lilStr, null)},   // =, =, >
                {bigConstr.newInstance(bigStr, bigStr), lilConstr.newInstance(bigStr, bigStr)}, // =, =, >
                {lilConstr.newInstance(bigStr, bigStr), lilConstr.newInstance(lilStr, null)},   // >, >, =
                {bigConstr.newInstance(bigStr, bigStr), bigConstr.newInstance(lilStr, lilStr)}, // >, >, =
                {bigConstr.newInstance(bigStr, null),   lilConstr.newInstance(lilStr, null)},   // >, =, >
                {bigConstr.newInstance(bigStr, lilStr), lilConstr.newInstance(lilStr, lilStr)}, // >, =, >
                {bigConstr.newInstance(lilStr, lilStr), lilConstr.newInstance(lilStr, null)},   // =, >, >
                {bigConstr.newInstance(bigStr, bigStr), lilConstr.newInstance(bigStr, lilStr)}, // =, >, >
                {bigConstr.newInstance(bigStr, lilStr), lilConstr.newInstance(lilStr, null)},   // >, >, >
                {bigConstr.newInstance(bigStr, bigStr), lilConstr.newInstance(lilStr, lilStr)}, // >, >, >

                // Advanced: less-thans
                {lilConstr.newInstance(bigStr, null),   bigConstr.newInstance(lilStr, bigStr)}, // >, <, <
                {lilConstr.newInstance(bigStr, lilStr), bigConstr.newInstance(lilStr, bigStr)}, // >, <, <
                {lilConstr.newInstance(bigStr, null),   bigConstr.newInstance(lilStr, null)},   // >, =, <
                {lilConstr.newInstance(bigStr, lilStr), bigConstr.newInstance(lilStr, lilStr)}, // >, =, <
                {lilConstr.newInstance(bigStr, null),   lilConstr.newInstance(lilStr, lilStr)}, // >, <, =
                {bigConstr.newInstance(bigStr, lilStr), bigConstr.newInstance(lilStr, bigStr)}, // >, <, =
                {lilConstr.newInstance(bigStr, lilStr), bigConstr.newInstance(bigStr, null)},   // =, >, <
                {lilConstr.newInstance(lilStr, bigStr), bigConstr.newInstance(lilStr, lilStr)}  // =, >, <
        };
    }

    /**
     * Verifies that the {@code compareTo()} method compares instances with different names, descriptions and types
     * correctly. This method only tests unequal dimensions. The test is performed both ways for thoroughness: both
     * {@code bigDim.compareTo(lilDim) > 0} and {@code lilDim.compareTo(bigDim) < 0} must be true. This test also
     * verifies consistency with {@code equals()}: all comparisons must return {@code false}. A similar check for equal
     * dimensions is done in {@link #compareToEqualTest()}.
     *
     * @param bigDim the larger of the two dimensions.
     * @param lilDim the smaller of the two dimensions.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareToUnequalData")
    public void compareToUnequalTest(Dimension bigDim, Dimension lilDim)
    {
        Assert.assertTrue(bigDim.compareTo(lilDim) > 0);
        Assert.assertTrue(lilDim.compareTo(bigDim) < 0);

        Assert.assertFalse(bigDim.equals(lilDim));
        Assert.assertFalse(lilDim.equals(bigDim));
    }

    /**
     * Checks that the {@code equals()} method returns expected results. The following scenarios are tested:
     * <table border="1">
     *   <tr><th>Scenario Description</th><th>Expected Result</th></tr>
     *   <tr><td>Equal names, null descriptions</td><td>T</td></tr>
     *   <tr><td>Equal names, equal non-null descriptions</td><td>T</td></tr>
     *   <tr><td>Comparison against a null reference</td><td>F</td></tr>
     *   <tr><td>Different names, both descriptions null</td><td>F</td></tr>
     *   <tr><td>Same name, one description null, one not</td><td>F</td></tr>
     *   <tr><td>Same name, descriptions different but non-null</td><td>F</td></tr>
     *   <tr><td>Different names, one description null, one not</td><td>F</td></tr>
     *   <tr><td>Different names, descriptions different but non-null</td><td>F</td></tr>
     *   <tr><td>Equal names, equal both descriptions null, but different class</td><td>F</td></tr>
     *   <tr><td>Equal names, equal non-null descriptions, but different class</td><td>F</td></tr>
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

        // against null
        Assert.assertNotEquals(oneArgDimension, null);
        
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

        // Different class, null descriptions
        Assert.assertNotEquals(oneArgDimension, differentOneDimension);
        // Different class, non-null descriptions
        Assert.assertNotEquals(twoArgDimension, differentTwoDimension);
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

    /**
     * A dimension type that does not override any of the methods of its parent type. This class is intended to be test
     * the class-differentiation properties of {@code Dimension}'s {@code equals()}, and {@code compareTo()} methods.
     *
     * @author Joseph Fox-Rabinovitz
     * @version 1.0.0, 02 Sep 2014 - J. Fox-Rabinovitz - Initial coding.
     * @since 1.0.0
     */
    private static class InnerTestDimension extends TestDimension
    {
        /**
         * The version ID for serialization.
         *
         * @serial Increment the least significant three digits when
         * compatibility is not compromised by a structural change (e.g. adding
         * a new field with a sensible default value), and the upper digits when
         * the change makes serialized versions of of the class incompatible
         * with previous releases.
         * @since 1.0.0
         */
        private static final long serialVersionUID = 1L;

        /**
         * Redirects to the superconstructor with the same argument list.
         *
         * @param name {@inheritDoc}
         * @since 1.0.0
         */
        public InnerTestDimension(String name)
        {
            super(name);
        }

        /**
         * Redirects to the superconstructor with the same argument list.
         *
         * @param name {@inheritDoc}
         * @param description {@inheritDoc}
         * @since 1.0.0
         */
        public InnerTestDimension(String name, String description)
        {
            super(name, description);
        }
    }
}
