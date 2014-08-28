/*
 * DimensionComponent.java (Class: com.madphysicist.monada.DimensionComponent)
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

import java.io.Serializable;
import java.util.Iterator;

import com.madphysicist.tools.util.HashUtilities;
import com.madphysicist.tools.util.ReferenceIterator;

/**
 * <p>
 * Represents a component of a derived dimension along a given base dimension. A component may contain positive,
 * negative, and even fractional exponents. It contains both the dimension information and the exponent with which the
 * base dimension appears in the derived one. For example, acceleration would consist of two components: {@code
 * Length^1}, and {@code Time^-2}, where {@code Length} and {@code Time} would be {@link BaseDimension}s.
 * </p>
 * <p>
 * {@code DimensionComponent}s are {@code Iterable} over themselves. This is done so that they can be specified in a
 * collection or array when constructing a {@code DerivedDimension}. Derived dimensions are constructed from iterators
 * of components.
 * </p>
 * <p>
 * Dimension components are comparable, first by exponent (descending), then by the name and description of the
 * underlying base dimension.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 14 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0.0
 */
public class DimensionComponent implements Serializable, Comparable<DimensionComponent>, DimensionComponentIterable
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
    private static final long serialVersionUID = 1000L;

    /**
     * The base dimension of this component. This reference will never be {@code null}, as enforced by the constructor.
     *
     * @serial
     * @since 1.0.0
     */
    private final BaseDimension dimension;

    /**
     * The exponent with which the base dimension this component represents appears in the derived dimension. The
     * exponent may be positive, negative, integer or fractional. Most common dimensions have integer exponents.
     *
     * @serial
     * @since 1.0.0
     */
    private final float exponent;

    /**
     * Constructs a component that is linear in the specified dimension. The exponent of this component will be
     * {@code 1.0f}.
     *
     * @param dimension the dimension that this component represents. May not be {@code null}.
     * @throws NullPointerException if {@code dimension} is {@code null}.
     * @since 1.0.0
     */
    public DimensionComponent(BaseDimension dimension) throws NullPointerException
    {
        this(dimension, 1.0f);
    }

    /**
     * Constructs a component in the specified dimension with the specified exponent.
     *
     * @param dimension the dimension that this component represents. May not be {@code null}.
     * @param exponent the exponent for this component.
     * @throws NullPointerException if {@code dimension} is {@code null}.
     * @since 1.0.0
     */
    public DimensionComponent(BaseDimension dimension, float exponent) throws NullPointerException
    {
        if(dimension == null)
            throw new NullPointerException("null dimension");
        this.dimension = dimension;
        this.exponent = exponent;
    }

    /**
     * Returns the base dimension that this component represents.
     *
     * @return the base dimension that this component represents.
     * @since 1.0.0
     */
    public BaseDimension dimension()
    {
        return dimension;
    }

    /**
     * Returns the exponent of the dimension for this component.
     *
     * @return the exponent of the dimension for this component.
     * @since 1.0.0
     */
    public float exponent()
    {
        return exponent;
    }

    /**
     * Checks if this object is equal to another. Two components are considered equal if they have the same class and
     * both their dimension and exponent are equal.
     *
     * @since 1.0.0
     */
    @Override public boolean equals(Object o)
    {
        if(o != null && this.getClass() == o.getClass()) {
            DimensionComponent d = (DimensionComponent)o;
            return this.exponent == d.exponent && this.dimension.equals(d.dimension);
        }
        return false;
    }

    /**
     * Computes the hash code based on the exponent and dimension of this object.
     *
     * @since 1.0.0
     */
    @Override public int hashCode()
    {
        int code = HashUtilities.hashCode(exponent);
        return HashUtilities.hashCode(code, dimension);
    }

    /**
     * Compares this component to another one, first by the exponent (descending), then by the underlying dimension.
     *
     * @since 1.0.0
     */
    @Override public int compareTo(DimensionComponent o)
    {
        if(this.exponent > o.exponent)
            return -1;
        if(this.exponent < o.exponent)
            return 1;
        return this.dimension.compareTo(o.dimension);
    }

    /**
     * Returns a string representation of this component.
     *
     * @since 1.0.0
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("[dimension=");
        sb.append(dimension);
        sb.append("; exponent=");
        sb.append(exponent);
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * The iterator has only one element, which is a reference to this object.
     *
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public Iterator<DimensionComponent> iterator()
    {
        return new ReferenceIterator<DimensionComponent>(this);
    }

    /**
     * {@inheritDoc}
     * This method always returns {@code 1} since the iterator is over this object.
     *
     * @since 1.0.0
     */
    @Override public int componentCount()
    {
        return 1;
    }
}
