/*
 * DimensionComponentTest.java (TestClass: com.madphysicist.monada.DimensionComponentTest)
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
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

/**
 * Tests each of the methods of {@link com.madphysicist.monada.DimensionComponent} except {@code toString()}. The string
 * representation is provided for debugging and is not guaranteed to match anything.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 27 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 */
public class DimensionComponentTest
{
    /**
     * The default dimension to test with. This field is only initialized once by {@link #beforeClass()} since it is
     * immutable.
     *
     * @since 1.0.0
     */
    private BaseDimension dimension;

    /**
     * A dimension that is initialized with parameters that are equal to the parameters of {@link #dimension}, and so
     * should be equal to it. This field is only initialized once by {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private BaseDimension sameDimension;

    /**
     * A dimension that is greater than {@link #dimension} according to its natural order. This field is only
     * initialized once by {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private BaseDimension greaterDimension;

    /**
     * A dimension that is smaller than {@link #dimension} according to its natural order. This field is only
     * initialized once by {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private BaseDimension smallerDimension;

    /**
     * The default exponent to test with. This field is only initialized once by {@link #beforeClass()}.
     *
     * @since 1.0.0
     */
    private float exponent;

    /**
     * An exponent value guaranteed to be greater than {@link #exponent}. Components with this exponent should be
     * smaller than ones initialized with {@code exponent}. This field is only initialized once by {@link
     * #beforeClass()}.
     *
     * @since 1.0.0
     */
    private float greaterExponent;

    /**
     * An exponent value guaranteed to be smaller than {@link #exponent}. Components with this exponent should be
     * greater than ones initialized with {@code exponent}. This field is only initialized once by {@link
     * #beforeClass()}.
     *
     * @since 1.0.0
     */
    private float smallerExponent;

    /**
     * The default component to test against. This component is initialized with the default {@link #dimension} and
     * the default {@link #exponent}. This field is only initialized once by {@link #beforeClass()} since it is
     * immutable.
     *
     * @since 1.0.0
     */
    private DimensionComponent component;

    /**
     * A reference that is guaranteed to be different from {@link #component}, but initialized with the same dimension
     * reference and exponent. This component should be equal to {@code component}. The "identical" in the field name
     * refers to the initialization parameters, not to the reference itself. This field is only initialized once by
     * {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private DimensionComponent identicalComponent;

    /**
     * A reference that is equal to both {@link #component} and {@link #identicalComponent}, but not identical to either
     * one. This reference is created with a dimension that is equal to but a different reference from {@link
     * #dimension}. This field is only initialized once by {@link #beforeClass()} since it is immutable.
     *
     * @since 1.0.0
     */
    private DimensionComponent sameComponent;

    /**
     * A component that is constructed with using the single-argument constructor. This component is unequal to {@link
     * #component}, {@link #identicalComponent} and {@link #sameComponent}. This field is only initialized once by
     * {@link #beforeClass()} since it is immutable.
     */
    private DimensionComponent linearComponent;

    /**
     * Initializes the fields of this class for use by the tests. This method attempts to ensure the following:
     * <ul>
     *   <li>{@link #dimension}{@code .equals}({@link #sameDimension}{@code )}</li>
     *   <li>{@code dimension != sameDimension}</li>
     *   <li>{@link #smallerDimension} {@code {@literal <}} {@link #dimension} {@code {@literal <}} {@link #greaterDimension}</li>
     *   <li>{@link #smallerDimension} {@code {@literal <}} {@link #sameDimension} {@code {@literal <}} {@link #greaterDimension}</li>
     *   <li>{@link #smallerExponent} {@code {@literal <}} {@link #exponent} {@code {@literal <}} {@link #greaterExponent}</li>
     *   <li>{@code component != identicalComponent}</li>
     *   <li>{@code component != sameComponent}</li>
     *   <li>{@code sameComponent != identicalComponent}</li>
     *   <li>{@code component.dimension() == identicalComponent.dimension()}</li>
     *   <li>{@code component.exponent() == identicalComponent.exponent()}</li>
     *   <li>{@code component.dimension() != sameComponent.dimension()}</li>
     *   <li>{@code component.dimension().equals(sameComponent.dimension())}</li>
     *   <li>{@code component.exponent() == sameComponent.exponent()}</li>
     *   <li>{@code linearComponent.exponent() == 1.0f}</li>
     *   <li>{@code !component.equals(linearComponent)}</li>
     * </ul>
     * These relationships are asserted, so the entire test class will fail if any of them are not true. Note that the
     * assertions about the equality of the components are left for the {@link #equalsTest()} method exccept the last
     * one in the list.
     *
     * @since 1.0.0
     */
    @BeforeClass
    private void beforeClass()
    {
        // Setup
        dimension = new BaseDimension("Length", "Distance", false);
        sameDimension = new BaseDimension("Length", "Distance", false);
        smallerDimension = new BaseDimension("Angle", "Dimensionless in SI", false);
        greaterDimension = new BaseDimension("Time", "The fourth dimension", true);
        exponent = 3.0f;
        greaterExponent = 4.0f;
        smallerExponent = 2.0f;
        component = new DimensionComponent(dimension, exponent);
        identicalComponent = new DimensionComponent(dimension, exponent);
        sameComponent = new DimensionComponent(sameDimension, exponent);
        linearComponent = new DimensionComponent(greaterDimension);

        // Verification
        Assert.assertNotSame(dimension, sameDimension);
        Assert.assertEquals(dimension, sameDimension);

        Assert.assertNotSame(dimension, greaterDimension);
        Assert.assertNotEquals(dimension, greaterDimension);

        Assert.assertTrue(smallerDimension.compareTo(dimension) < 0);
        Assert.assertTrue(dimension.compareTo(greaterDimension) < 0);

        Assert.assertTrue(greaterDimension.compareTo(sameDimension) > 0);
        Assert.assertTrue(sameDimension.compareTo(smallerDimension) > 0);

        Assert.assertTrue(greaterExponent > exponent);
        Assert.assertTrue(smallerExponent < exponent);
        Assert.assertTrue(greaterExponent > smallerExponent); // Hopefully this is superfluous

        Assert.assertNotSame(component, identicalComponent);
        Assert.assertNotSame(component, sameComponent);
        Assert.assertNotSame(sameComponent, identicalComponent);

        Assert.assertSame(component.dimension(), identicalComponent.dimension());
        Assert.assertEquals(component.dimension(), identicalComponent.dimension());
        Assert.assertEquals(component.exponent(), identicalComponent.exponent());

        Assert.assertNotSame(component.dimension(), sameComponent.dimension());
        Assert.assertEquals(component.dimension(), sameComponent.dimension());
        Assert.assertEquals(component.exponent(), sameComponent.exponent());

        Assert.assertEquals(linearComponent.exponent(), 1.0f);
        Assert.assertNotSame(component, linearComponent);
        Assert.assertNotEquals(component, linearComponent);
    }

    /**
     * Checks that the single-argument constructor fails when a {@code null} dimension is passed in. The expected
     * exception is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void LinearConstructorNullTest() throws NullPointerException
    {
        new DimensionComponent(null);
        Assert.fail();
    }

    /**
     * Checks that the single-argument constructor creates a component linear in the input dimension. A component
     * created with this constructor must have an exponent of {@code 1.0f} and the same dimension reference that was
     * used to create it.
     *
     * @since 1.0.0
     */
    @Test
    public void LinearConstructorTest()
    {
        Assert.assertEquals(linearComponent.exponent(), 1.0f);
        Assert.assertSame(linearComponent.dimension(), greaterDimension);
    }

    /**
     * Checks that the full (two-argument) constructor fails when a {@code null} dimension is passed in. The expected
     * exception is a {@code NullPointerException}.
     *
     * @throws NullPointerException as expected.
     * @since 1.0.0
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void FullConstructorNullTest() throws NullPointerException
    {
        new DimensionComponent(null, exponent);
        Assert.fail();
    }

    /**
     * Checks that the full (two-argument) constructor creates a component with the expected exponent and dimension. A
     * component created with this constructor must have an exponent equal to the input exponent and the same dimension
     * reference that was used to create it.
     *
     * @since 1.0.0
     */
    @Test
    public void FullConstructorTest()
    {
        Assert.assertEquals(component.exponent(), exponent);
        Assert.assertSame(component.dimension(), dimension);
    }

    /**
     * Checks that {@code compareTo()} and {@code equals()} are consistent with each other. The check ensures three
     * things: 
     * <ol>
     * <li>For a dimension component {@code A}:
     *   <ul>
     *     <li>{@code A.equals(A)}</li>
     *     <li>{@code A.compareTo(A) == 0}</li>
     *   </ul>
     * </li>
     * <li>For a dimension component {@code B} that equals (but is not identical to) {@code A}:
     *   <ul>
     *     <li>{@code A.equals(B)}</li>
     *     <li>{@code B.equals(A)}</li>
     *     <li>{@code A.compareTo(B) == 0}</li>
     *     <li>{@code B.compareTo(A) == 0}</li>
     *   </ul>
     * </li>
     * <li>For a dimension component {@code C} which is not equal to {@code A}:
     *   <ul>
     *     <li>{@code !A.equals(C)}</li>
     *     <li>{@code !C.equals(A)}</li>
     *     <li>{@code A.compareTo(C) != 0}</li>
     *     <li>{@code C.compareTo(A) != 0}</li>
     *     <li>{@code Math.signum(A.compareTo(C)) == -Math.signum(C.compareTo(A))}</li>
     *   </ul>
     * </li>
     * </ol>
     *
     * @since 1.0.0
     */
    @Test
    public void comparisonConsistencyTest()
    {
        // Self compare
        Assert.assertTrue(component.equals(component));
        Assert.assertEquals(component.compareTo(component), 0);

        // Compare equal objects
        Assert.assertEquals(sameComponent, component);
        Assert.assertEquals(component, sameComponent);
        Assert.assertEquals(component.compareTo(sameComponent), 0);
        Assert.assertEquals(sameComponent.compareTo(component), 0);

        // Compare unequal objects
        DimensionComponent otherComponent = new DimensionComponent(greaterDimension);

        Assert.assertNotEquals(component, otherComponent);
        Assert.assertNotEquals(otherComponent, component);
        Assert.assertNotEquals(component.compareTo(otherComponent), 0);
        Assert.assertNotEquals(otherComponent.compareTo(component), 0);

        Assert.assertEquals(Math.signum(component.compareTo(otherComponent)),
                           -Math.signum(otherComponent.compareTo(component)));
    }

    /**
     * Checks that the natural order of components works as expected. Components are expected to be sorted first by
     * exponent in descending order, then by dimension in ascending order. This implies the following testable
     * statements:
     * <ul>
     *   <li>Of two components with the same dimension, the one with a larger exponent ranks smaller.</li>
     *   <li>Of two components with the same dimension, the one with a smaller exponent ranks larger.</li>
     *   <li>Of two components with the same exponent, the one with a larger dimension ranks larger.</li>
     *   <li>Of two components with the same exponent, the one with a smaller dimension ranks smaller.</li>
     *   <li>For three components {@code A}, {@code B}, {@code C}, if {@code A {@literal >} B} and {@code B
     *       {@literal >} C}, then {@code A {@literal >} C}.</li>
     * </ul>
     *
     * @since 1.0.0
     */
    @Test
    public void compareToTest()
    {
        // >exp means <component
        DimensionComponent greaterComponentByExp = new DimensionComponent(dimension, smallerExponent);
        DimensionComponent smallerComponentByExp = new DimensionComponent(dimension, greaterExponent);

        // >dim means >component
        DimensionComponent greaterComponentByDim = new DimensionComponent(greaterDimension, exponent);
        DimensionComponent smallerComponentByDim = new DimensionComponent(smallerDimension, exponent);

        Assert.assertTrue(component.compareTo(greaterComponentByExp) < 0);
        Assert.assertTrue(greaterComponentByExp.compareTo(component) > 0);

        Assert.assertTrue(component.compareTo(smallerComponentByExp) > 0);
        Assert.assertTrue(smallerComponentByExp.compareTo(component) < 0);

        Assert.assertTrue(component.compareTo(greaterComponentByDim) < 0);
        Assert.assertTrue(greaterComponentByDim.compareTo(component) > 0);

        Assert.assertTrue(component.compareTo(smallerComponentByDim) > 0);
        Assert.assertTrue(smallerComponentByDim.compareTo(component) < 0);

        Assert.assertTrue(smallerComponentByExp.compareTo(smallerComponentByDim) < 0);
        Assert.assertTrue(smallerComponentByDim.compareTo(smallerComponentByExp) > 0);

        Assert.assertTrue(greaterComponentByExp.compareTo(greaterComponentByDim) > 0);
        Assert.assertTrue(greaterComponentByDim.compareTo(greaterComponentByExp) < 0);
    }

    /**
     * Checks that the component count is always one. Components created with both 1- and 2- argument consutructors are
     * checked.
     *
     * @since 1.0.0
     */
    @Test
    public void componentCountTest()
    {
        // 2-arg constructor
        Assert.assertEquals(component.componentCount(), 1);
        Assert.assertEquals(identicalComponent.componentCount(), 1);
        Assert.assertEquals(sameComponent.componentCount(), 1);
        
        // 1-arg constructor
        Assert.assertEquals(linearComponent.componentCount(), 1);
    }

    /**
     * Checks that the result of {@code dimension()} is the same reference as the component was created with.
     *
     * @since 1.0.0
     */
    @Test
    public void dimensionTest()
    {
        Assert.assertSame(component.dimension(), dimension);
        Assert.assertSame(identicalComponent.dimension(), dimension);
        Assert.assertSame(sameComponent.dimension(), sameDimension);
        Assert.assertSame(linearComponent.dimension(), greaterDimension);
    }

    /**
     * Checks that components are labeled as equal or unequal correctly. The following are expected to hold true:
     * <table>
     *   <tr><th>Compare against component that has/is...</th><th>Result is Equal?</th></tr>
     *   <tr><td>Self</td><td>Y</td></tr>
     *   <tr><td>Exact same dimension reference and exponent</td><td>Y</td></tr>
     *   <tr><td>Equal (but not identical) dimension and same exponent</td><td>Y</td></tr>
     *   <tr><td>Unequal dimension but same exponent</td><td>N</td></tr>
     *   <tr><td>Exact same dimension reference, but different exponent</td><td>N</td></tr>
     *   <tr><td>Equal (but not identical) dimension and different exponent</td><td>N</td></tr>
     *   <tr><td>Unequal dimension and different exponent</td><td>N</td></tr>
     * </table>
     * <tr><td></td><td>Y</td>
     * 
     * @since 1.0.0
     */
    @Test
    public void equalsTest()
    {
        // Y: Self
        Assert.assertEquals(component, component);

        // Y: Same dimension, same exponent
        Assert.assertNotSame(component, identicalComponent);
        Assert.assertEquals(component, identicalComponent);

        // Y: Equal but not same dimension, same exponent
        Assert.assertNotSame(sameComponent, component);
        Assert.assertEquals(sameComponent, component);

        // N: Different dimension, same exponent
        DimensionComponent diffDimComponent = new DimensionComponent(greaterDimension, exponent);
        Assert.assertNotSame(diffDimComponent, component);
        Assert.assertNotEquals(diffDimComponent, component);

        // N: Same dimension, different exponent
        DimensionComponent diffExpSameDimComponent = new DimensionComponent(dimension, greaterExponent);
        Assert.assertNotSame(diffExpSameDimComponent, component);
        Assert.assertNotEquals(diffExpSameDimComponent, component);

        // N: Different but equal dimension, different exponent
        DimensionComponent diffExpEqualDimComponent = new DimensionComponent(sameDimension, greaterExponent);
        Assert.assertNotSame(diffExpEqualDimComponent, component);
        Assert.assertNotEquals(diffExpEqualDimComponent, component);

        // N: Different dimension, different exponent
        DimensionComponent differentComponent = new DimensionComponent(greaterDimension, greaterExponent);
        Assert.assertNotSame(differentComponent, component);
        Assert.assertNotEquals(differentComponent, component);
    }

    /**
     * Check the result of {@code exponent()} is the same as the exponent with which the component was constructed.
     *
     * @since 1.0.0
     */
    @Test
    public void exponentTest()
    {
        // 2-arg constructor
        Assert.assertEquals(component.exponent(), exponent);
        Assert.assertEquals(identicalComponent.exponent(), exponent);
        Assert.assertEquals(sameComponent.exponent(), exponent);

        // 1-arg constructor
        Assert.assertEquals(linearComponent.exponent(), 1.0f);
    }

    /**
     * Checks that the {@code hashCode()} of equal components is the same. This is the only real requirement for {@code
     * hashCode()}. A good hashing function will return different codes for unequal components as much as is possible,
     * but this is a subjective requirement that can not be formally tested with a small number of objects.
     *
     * @since 1.0.0
     */
    @Test
    public void hashCodeTest()
    {
        Assert.assertEquals(identicalComponent.hashCode(), component.hashCode());
        Assert.assertEquals(sameComponent.hashCode(), component.hashCode());
        // A test that is unlikely to fail, but nevertheless invalid:
        //Assert.assertNotEquals(new DimensionComponent(greaterDimension, greaterExponent), component.hashCode())
    }

    /**
     * Checks that the returned iterator will only iterate over one item, which is the component itself. The following
     * three steps are checked (in order):
     * <ol>
     *   <li>{@code hasNext()} returns {@code true}.</li>
     *   <li>{@code next()} returns the component.</li>
     *   <li>{@code hasNext()} returns {@code false}.</li>
     * </ol>
     *
     * @since 1.0.0
     */
    @Test
    public void iteratorTest()
    {
        Iterator<DimensionComponent> iterator = component.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(iterator.next(), component);
        Assert.assertFalse(iterator.hasNext());
    }
}
