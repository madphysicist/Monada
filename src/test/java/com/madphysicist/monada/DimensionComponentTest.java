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
 * Tests each of the methods of
 * {@link com.madphysicist.monada.DimensionComponent}.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 27 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 */
public class DimensionComponentTest
{
    private BaseDimension dimension;
    private BaseDimension sameDimension;
    private BaseDimension otherDimension;
    private float exponent;
    private float greaterExponent;
    private float smallerExponent;
    private DimensionComponent component;
    private DimensionComponent identicalComponent;
    private DimensionComponent sameComponent;

    @BeforeClass
    public void beforeClass()
    {
        dimension = new BaseDimension("Length", "Distance", false);
        sameDimension = new BaseDimension("Length", "Distance", false);
        otherDimension = new BaseDimension("Angle", "Dimensionless in SI", true);
        exponent = 3.0f;
        greaterExponent = 4.0f;
        smallerExponent = 2.0f;
        component = new DimensionComponent(dimension, exponent);
        identicalComponent = new DimensionComponent(dimension, exponent);
        sameComponent = new DimensionComponent(sameDimension, exponent);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void LinearConstructorNullTest()
    {
        new DimensionComponent(null);
        Assert.fail();
    }

    @Test
    public void LinearConstructorTest()
    {
        DimensionComponent testComponent = new DimensionComponent(dimension);
        Assert.assertEquals(testComponent.exponent(), 1.0f);
        Assert.assertSame(testComponent.dimension(), dimension);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void FullConstructorNullTest()
    {
        new DimensionComponent(null, exponent);
        Assert.fail();
    }

    @Test
    public void FullConstructorTest()
    {
        DimensionComponent testComponent = new DimensionComponent(dimension, exponent);
        Assert.assertEquals(testComponent.exponent(), exponent);
        Assert.assertSame(testComponent.dimension(), dimension);
    }

    @Test
    public void compareToTest()
    {
        BaseDimension greaterDimension, smallerDimension;
        if(sameDimension.compareTo(otherDimension) > 0) {
            greaterDimension = sameDimension;
            smallerDimension = otherDimension;
        } else {
            greaterDimension = otherDimension;
            smallerDimension = sameDimension;
        }

        DimensionComponent greaterComponentByExp = new DimensionComponent(dimension, smallerExponent);
        DimensionComponent smallerComponentByExp = new DimensionComponent(dimension, greaterExponent);

        DimensionComponent greaterComponentByDim = new DimensionComponent(greaterDimension, exponent);
        DimensionComponent smallerComponentByDim = new DimensionComponent(smallerDimension, exponent);

        Assert.assertEquals(component.compareTo(component), 0);

        Assert.assertEquals(identicalComponent, component);
        Assert.assertNotSame(identicalComponent, component);
        Assert.assertEquals(component.compareTo(identicalComponent), 0);
        Assert.assertEquals(identicalComponent.compareTo(component), 0);

        Assert.assertEquals(sameComponent, component);
        Assert.assertNotSame(sameComponent, component);
        Assert.assertEquals(component.compareTo(sameComponent), 0);
        Assert.assertEquals(sameComponent.compareTo(component), 0);

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

    @Test
    public void componentCountTest()
    {
        Assert.assertEquals(component.componentCount(), 1);
    }

    @Test
    public void dimensionTest()
    {
        Assert.assertSame(component.dimension(), dimension);
    }

    @Test
    public void equalsTest()
    {
        Assert.assertNotSame(sameDimension, dimension);
        Assert.assertEquals(sameDimension, dimension);

        Assert.assertNotEquals(dimension, otherDimension);
        Assert.assertNotEquals(sameDimension, otherDimension);

        // Y: Self
        Assert.assertEquals(component, component);

        // Y: Same dimension, same exponent
        Assert.assertNotSame(component, identicalComponent);
        Assert.assertEquals(component, identicalComponent);

        // Y: Equal but not same dimension, same exponent
        Assert.assertNotSame(sameComponent, component);
        Assert.assertEquals(sameComponent, component);

        // N: Different dimension, same exponent
        DimensionComponent diffDimComponent = new DimensionComponent(otherDimension, exponent);
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
        DimensionComponent differentComponent = new DimensionComponent(otherDimension, greaterExponent);
        Assert.assertNotSame(differentComponent, component);
        Assert.assertNotEquals(differentComponent, component);
    }

    @Test
    public void exponentTest()
    {
        Assert.assertEquals(component.exponent(), exponent);
    }

    @Test
    public void hashCodeTest()
    {
        Assert.assertEquals(identicalComponent.hashCode(), component.hashCode());
        Assert.assertEquals(sameComponent.hashCode(), component.hashCode());
    }

    @Test
    public void iteratorTest()
    {
        Iterator<DimensionComponent> iterator = component.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(iterator.next(), component);
        Assert.assertFalse(iterator.hasNext());
    }
}
