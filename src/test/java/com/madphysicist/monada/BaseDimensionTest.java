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

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

/**
 * Tests each of the methods of {@link BaseDimension} except {@code toString()}. The string representation is provided
 * for debugging and is not guaranteed to match anything.
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
    }

    /**
     * Checks that the one-argument constructor fails when passes a {@code null} name. The expected failure is a {@code
     * NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void NameOnlyConstructorNullTest() throws NullPointerException
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
    public void NameOnlyConstructorTest()
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
    public void NameDescriptionConstructorNullTest() throws NullPointerException
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
    public void NameDescriptionConstructorTest()
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
    public void NameNullConstructorNullTest() throws NullPointerException
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
    public void NameNullConstructorTest()
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
    public void FullConstructorNullTest() throws NullPointerException
    {
        new BaseDimension(null, DEFAULT_DESCRIPTION, true);
        Assert.fail();
    }

    /**
     * Checks that the full constructor initializes an instance as expected. The name, description and null status are
     * expected to be the ones that the object was initialized with.
     *
     * @since 1.0.0
     */
    @Test
    public void FullConstructorTest()
    {
        BaseDimension dim1 = new BaseDimension(DEFAULT_NAME, null, false);
        Assert.assertSame(dim1.name(), DEFAULT_NAME);
        Assert.assertNull(dim1.description());
        Assert.assertFalse(dim1.isNull());

        BaseDimension dim2 = new BaseDimension(DEFAULT_NAME, null, true);
        Assert.assertSame(dim2.name(), DEFAULT_NAME);
        Assert.assertNull(dim2.description());
        Assert.assertTrue(dim2.isNull());

        BaseDimension dim3 = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, false);
        Assert.assertSame(dim3.name(), DEFAULT_NAME);
        Assert.assertSame(dim3.description(), DEFAULT_DESCRIPTION);
        Assert.assertFalse(dim3.isNull());

        BaseDimension dim4 = new BaseDimension(DEFAULT_NAME, DEFAULT_DESCRIPTION, true);
        Assert.assertSame(dim4.name(), DEFAULT_NAME);
        Assert.assertSame(dim4.description(), DEFAULT_DESCRIPTION);
        Assert.assertTrue(dim4.isNull());
    }

    @Test
    public void compareToTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void componentCountTest()
    {
        throw new RuntimeException("Test not implemented");
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
    public void iteratorTest()
    {
        throw new RuntimeException("Test not implemented");
    }

    @Test
    public void normalizeTest()
    {
        throw new RuntimeException("Test not implemented");
    }
}
