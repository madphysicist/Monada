/*
 * BaseDimension.java (Class: com.madphysicist.monada.BaseDimension)
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

/**
 * <p>
 * Defines a dimension orthogonal to all other dimensions in a given {@code MeasurementSystem} or other collection. A
 * base dimension can be used to construct derived dimensions via {@code DimensionComponent} objects. Base dimensions
 * can be considered "null" dimensions in certain cases. Null dimensions are conceptual placeholders that are
 * effectively dimensionless/unitless. An example is angle in the SI measurement system. Quantities along a null base
 * dimension may be treated as a scalar, but only after they have been converted to the base unit along that dimension.
 * This way, a function such as {@code sin(x)}, which expects a unitless scalar argument can be invoked with a quantity
 * in degrees, but only after it has been converted to radians (assuming SI units).
 * </p>
 * <p>
 * This class is intended to be treated as an enum within a given {@code MeasurementSystem}. To this effect, it does not
 * override {@code hashCode()} or {@code equals()} from the default implementation. This way, two dimensions are equal
 * if and only if they point to the same object, not just if they have the same name.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 16 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class BaseDimension extends Dimension
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
     * A dimension component with only one element. The single component contains a reference to this
     * dimension with an exponent of {@code 1.0f}. This field is computed during initialization to avoid
     * re-instantiating it for every call of {@link #iterator()}.
     *
     * @serial
     * @since 1.0.0
     */
    private final DimensionComponent component;

    /**
     * Determines if the dimension is a "null", or unitless dimension. A null dimension does not affect the quantities
     * that it labels. For example, in SI units, angle is a null dimension. Quantities in radians are effectively
     * unitless. Note that quantities that contain a null base dimension must be converted to the base units for that
     * dimension before they can be used as a scalar.
     *
     * @serial
     * @since 1.0.0
     */
    public boolean isNull;

    /**
     * Constructs a base dimension with the specified name and no description. The new dimension is not a null
     * dimension.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @see #isNull()
     * @since 1.0.0
     */
    public BaseDimension(String name) throws NullPointerException
    {
        this(name, null, false);
    }

    /**
     * Constructs a dimension with the specified name and optional description. The new dimension is not a null
     * dimension.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param description a (brief) description of what this dimension represents. May be {@code null} to indicate no
     * description.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @see #isNull()
     * @since 1.0.0
     */
    public BaseDimension(String name, String description) throws NullPointerException
    {
        this(name, description, false);
    }

    /**
     * Constructs a dimension with the specified name, null status and no description.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param isNull {@code true} if this is a null dimension, {@code false} otherwise. Null dimensions are unitless
     * dimensions such as angle in SI.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @see #isNull()
     * @since 1.0.0
     */
    public BaseDimension(String name, boolean isNull) throws NullPointerException
    {
        this(name, null, isNull);
    }

    /**
     * Constructs a dimension with the specified name, null status and optional description.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param description a (brief) description of what this dimension represents. May be {@code null} to indicate no
     * description.
     * @param isNull {@code true} if this is a null dimension, {@code false} otherwise. Null dimensions are unitless
     * dimensions such as angle in SI.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @see #isNull()
     * @since 1.0.0
     */
    public BaseDimension(String name, String description, boolean isNull) throws NullPointerException
    {
        super(name, description);
        this.isNull = isNull;
        this.component = new DimensionComponent(this);
    }

    /**
     * Returns the null-status of this dimension. A null dimension represents a "dimensionless" dimension, but one that
     * still has conceptual meaning. Whether or not a dimension is null is measurement system dependent. Examples of
     * null dimensions are angle and solid angle in SI. Quantities along null dimensions can be treated as scalars,
     * although they may first have to be converted to the base unit along that dimension. For example, a function that
     * expects a unitless scalar argument such as {@code sin(x)} can be invoked with a quantity in radians, but not one
     * in degrees without prior conversion. Derived dimensions that differ only by components in null dimensions may or
     * may not be considered equivalent, depending on the application.
     *
     * @return {@code true} if this is a null dimension, {@code false} otherwise.
     * @since 1.0.0
     */
    public boolean isNull()
    {
        return isNull;
    }

    /**
     * {@inheritDoc}
     * Base dimensions have only one component, by definition. 
     *
     * @return always {@code 1}.
     * @since 1.0.0
     */
    @Override public int componentCount()
    {
        return 1;
    }

    /**
     * Returns a single-element iterator that iterates over a {@code DimensionComponent} that is linear in this
     * instance. The returned iterator does not allow modifications.
     *
     * @since 1.0.0
     */
    @Override public Iterator<DimensionComponent> iterator()
    {
        return component.iterator();
    }
}
