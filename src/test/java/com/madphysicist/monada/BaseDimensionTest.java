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
 * and {@code isNull()}) are tested along with the constructors. This test requires {@link DimensionTest} to have run
 * successfully.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 30 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 * @since 1.0
 */
@Test(groups = "BaseDimensionTest", dependsOnGroups = "DimensionTest")
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

    /**
     * This test only checks that the class does not affect component comparison. The other properties are verified by
     * {@link DimensionTest#compareComponentsTest(Dimension, Dimension)} since the {@code compareComponents()} method is
     * not overridden in {@code BaseDimension}. The actual test compares a {@code BaseDimension} and a {@code
     * DerivedDimension} that is linear in that base dimension and has no other components.
     *
     * @since 1.0.0
     */
    @Test
    public void compareComponentsEqualTest()
    {
        DerivedDimension otherDimension = new DerivedDimension("AnotherDimension", someDimension);

        Assert.assertNotEquals(someDimension, otherDimension);
        Assert.assertNotEquals(otherDimension, someDimension);

        Assert.assertEquals(someDimension.compareComponents(otherDimension), 0);
        Assert.assertEquals(otherDimension.compareComponents(someDimension), 0);

        // Check self just for caution
        Assert.assertEquals(someDimension.compareComponents(someDimension), 0);
    }

    /**
     * The {@code null} case is verified by {@link DimensionTest#compareComponentsNullTest()} because the {@code
     * compareComponents()} method is never overridden.
     *
     * @param dim1
     * @param dim2
     */
    @Test(dataProvider = "compareComponentsUnequalData")
    public void compareComponentsUnequalTest(BaseDimension dim1, Dimension dim2)
    {
        Assert.assertTrue(dim1.compareComponents(dim2) > 0);
        Assert.assertTrue(dim2.compareComponents(dim1) < 0);
    }

    /**
     * Checks that a dimension is never equal to {@code null} and that comparison against {@code null} fails. The
     * expected failure is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected when calling {@code compareTo(null)}.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void compareNullTest() throws NullPointerException
    {
        Assert.assertNotEquals(someDimension, null);
        someDimension.compareTo(null);
        Assert.fail();
    }

    /**
     * Provides data for {@link #compareEqualTest(BaseDimension, BaseDimension)} and to check that dimensions expected
     * to be equal are indeed equal. The following cases are verified:
     * <ul>
     *   <li>Same reference</li>
     *   <li>No description, non-null dimension</li>
     *   <li>No description, null dimension</li>
     *   <li>Yes description, non-null dimension</li>
     *   <li>Yes description, null dimension</li>
     * </ul>
     *
     * @return an array of pairs of dimensions that are expected to be equal.
     * @since 1.0.0
     */
    @DataProvider(name = "compareEqualData")
    protected Object[][] compareEqualData()
    {
        return new Object[][] {
                {someDimension, someDimension},
                {new BaseDimension(DEFAULT_NAME), new BaseDimension(DEFAULT_NAME)},
                {new BaseDimension(DEFAULT_NAME, true), new BaseDimension(DEFAULT_NAME, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true)}
        };
    }

    /**
     * Verifies that the {@code compareTo()} and {@code equals()} methods compare equal instances correctly. This method
     * only tests equal dimensions. The test is performed symmetrically for both methods: both {@code
     * dim1.compareTo(dim2) == 0} and {@code dim2.compareTo(dim1) == 0} must be true. Likewise, both {@code
     * dim1.equals(dim2)} and {@code dim2.equals(dim1)} must be true. This test is therefore also checking for
     * consistency with {@code equals()}. Similar checks for unequal dimensions are done in {@link
     * #compareUnequalTest(Dimension, Dimension)} and {@link #compareNullTest()}.
     *
     * @param dim1 the first dimension to compare.
     * @param dim2 the second dimension to compare.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareEqualData")
    public void compareEqualTest(BaseDimension dim1, BaseDimension dim2)
    {
        Assert.assertEquals(dim1, dim2);
        Assert.assertEquals(dim2, dim2);
        Assert.assertEquals(dim1.compareTo(dim2), 0);
        Assert.assertEquals(dim2.compareTo(dim1), 0);
    }

    /**
     * Provides data to {@link #compareUnequalTest(Dimension, Dimension)} to verify that unequal dimensions are marked
     * as such. The greater of the two dimensions always comes first in the pair. The following comparisons are
     * provided:
     * <ul>
     *   <li>Some instance of the same class, but with unequal properties.</li>
     *   <li>Instance of an extending class with all properties the same.</li>
     *   <li>Instances of an extending class with some properties different.</li>
     *   <li>Instance of {@code DerivedDimension} linear in the test base dimension.</li>
     *   <li>Instance of {@code DerivedDimension} linear in the test base dimension and other dimensions.</li>
     *   <li>Instance of {@code DerivedDimension} non-linear in the test base dimension.</li>
     * </ul>
     * The {@code null} case is checked by {@link #compareNullTest()}. A similar set of checks for equal dimensions is
     * performed by {@link #compareEqualTest(BaseDimension, BaseDimension)}.
     *
     * @return an array of dimension pairs none, of which are equal.
     * @since 1.0.0
     */
    @DataProvider(name = "compareUnequalData")
    protected Object[][] compareUnequalData()
    {
        String bigStr, lilStr;

        if(DEFAULT_NAME.compareTo(DEFAULT_DESCRIPTION) > 0) {
            bigStr = DEFAULT_NAME;
            lilStr = DEFAULT_DESCRIPTION;
        } else {
            bigStr = DEFAULT_DESCRIPTION;
            lilStr = DEFAULT_NAME;
        }

        return new Object[][] {
                // Diff class
                {someDimension, new TestDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION)},
                // Diff name
                {new BaseDimension(DEFAULT_NAME), new BaseDimension(DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, true), new BaseDimension(DEFAULT_DESCRIPTION, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION, true)},
                // Diff dimension
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_NAME)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_NAME, DEFAULT_NAME)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_NAME, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_NAME, DEFAULT_NAME, true)},
                // Diff null
                {new BaseDimension(DEFAULT_NAME), new BaseDimension(DEFAULT_NAME, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true)},
                // Diff name, description
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION, true)},
                // Diff name, null
                {new BaseDimension(DEFAULT_NAME, true), new BaseDimension(DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_DESCRIPTION)},
                // Diff name, description, null
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_DESCRIPTION, true)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_NAME)},
                {new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION), new BaseDimension(DEFAULT_DESCRIPTION, DEFAULT_NAME, true)}
        };
    }

    /**
     * Checks that dimensions that are not expected to be equal do not compare as equal. The comparison is symmetrical:
     * neither {@code firstDimension.equals(secondDimension)} nor {@code secondDimension.equals(firstDimension)} may be
     * true. Similarly {@code dim1.compareTo(dim2) > 0} and {@code dim2.compareTo(dim1) < 0} must both be true as well.
     * This test is therefore also checking for consistency with {@code equals()}. This method requires the first
     * parameter to be greater than the second and does not handle {@code null} inputs. {@code null}s are checked by
     * {@link #compareNullTest()}. Checks for equal dimensions are handled by {@link
     * #compareEqualTest(BaseDimension, BaseDimension)}.
     *
     * @param dim1 the (strictly) greater dimension to compare.
     * @param dim2 the (strictly) smaller dimension to compare.
     * @since 1.0.0
     */
    @Test(dataProvider = "compareUnequalData")
    public void compareUnequalTest(Dimension dim1, Dimension dim2)
    {
        Assert.assertNotEquals(dim1, dim2);
        Assert.assertNotEquals(dim2, dim1);

        Assert.assertTrue(dim1.compareTo(dim2) > 0);
        Assert.assertTrue(dim2.compareTo(dim1) < 0);
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

    /**
     * Checks that two dimensions initialized with the same parameters have the same hash code.
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

    /**
     * A base dimension type that does not override any of the methods of its parent type. This class is intended to be
     * test the class-differentiation properties of {@code BaseDimension}'s {@code equals()}, and {@code compareTo()}
     * methods. Access only to the full superconstructor is provided.
     *
     * @author Joseph Fox-Rabinovitz
     * @version 1.0.0, 04 Sep 2014 - J. Fox-Rabinovitz - Initial coding.
     * @since 1.0.0
     */
    private static class TestBaseDimension extends BaseDimension
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
         * @param description {@inheritDoc}
         * @since 1.0.0
         */
        public TestBaseDimension(String name, String description, boolean isNull)
        {
            super(name, description, isNull);
        }
    }
}
