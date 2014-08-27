/*
 * OffsetUnits.java (Class: com.madphysicist.monada.OffsetUnits)
 *
 * Mad Physicist Monada Project (Unit Conversion Suite)
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

/**
 * <p>
 * Represents a special type of units that contain an additive as well as a multiplicative component along their
 * dimension. The most common example of such units are temperature scales such as degrees Celsius. Units with an offset
 * are a special case that can not be multiplied by other units until they are converted into another unit along the
 * same dimension which does not have an offset. Although this class can be instantiated and used directly, it is highly
 * recommended that it be accessed through a {@code MeasurementSystem}, which implements special treatment for instances
 * of this class.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 28 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class OffsetUnits extends Units
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
     * The parent of this set of units. When multiplying by offset units, one should convert to the parent units until a
     * non-offset unit is achieved. This may have to be done recursively.
     *
     * @serial
     * @since 1.0.0
     */
    private final Units parent;

    /**
     * The multiplicative scaling of this set of units relative to the parent. This field exists to improve precision.
     * It can actually be computed from {@code this.factor() / parent.factor()}.
     *
     * @serial
     * @since 1.0.0
     */
    private final double scale;

    /**
     * The additive offset of the units from the parent. The offset is specified in terms of this set of units.
     *
     * @serial
     * @since 1.0.0
     */
    private final double offset;

    /**
     * Constructs a set of offset units with the specified name, abbreviation, parent and offset. The units are not
     * scaled relative to the parent.
     *
     * @param name the name of the units, for example "Degrees Celsius".
     * @param abbreviation the abbreviation of the units, for example "°C".
     * @param parent the parent units, relative to which the offset is computed.
     * @param offset the offset of this set of units, in terms of this set of units.
     * @see #parent()
     * @see #offset()
     * @since 1.0.0
     */
    public OffsetUnits(String name, String abbreviation, Units parent, double offset)
    {
        this(name, abbreviation, parent, 1.0, offset);
    }

    /**
     * Constructs a set of offset units with the specified name, abbreviation, scale, parent and offset. Note that the
     * scale is combined with the factor for these units, while the offset is completely ignored.
     *
     * @param name the name of the units, for example "degrees Celsius".
     * @param abbreviation the abbreviation of the units, for example "°C".
     * @param parent the parent units, relative to which the offset is computed.
     * @param scale the scaling of this set of units relative to the parent. This is a measure of how much bigger each
     * increment in this set of units is relative to the parent. For example, the scale of degrees Fahrenheit relative
     * to degrees Celsius is 5/9.
     * @param offset the offset of this set of units, in terms of this set of units.
     * @see #parent()
     * @see #offset()
     * @since 1.0.0
     */
    public OffsetUnits(String name, String abbreviation, Units parent, double scale, double offset)
    {
        super(name, abbreviation, scale, parent.dimension());
        this.parent = parent;
        this.scale = scale;
        this.offset = offset;
    }

    /**
     * The parent units, relative to which the scale and offset are specified. When multiplying by quantities with
     * offset units, one should convert to the parent units until a non-offset unit is achieved. This may have to be
     * done recursively.
     *
     * @return the units from which this set of units is offset.
     * @since 1.0.0
     */
    public Units parent()
    {
        return parent;
    }

    /**
     * The multiplicative scaling of this set of units relative to the parent. This value can be computed as {@code
     * this.factor() / parent.factor()}, although the value returned by this method may be a little more precise. For
     * example, the scale of degrees Celsius relative to Kelvin is 1.0. The scale of degrees Fahrenheit relative to
     * degrees Celsius is 5/9.
     *
     * @return the offset of this set of units from the parent, in terms of this set of units.
     * @since 1.0.0
     */
    public double scale()
    {
        return scale;
    }

    /**
     * The additive offset of the units from the parent. The offset is specified in terms of this set of units. For
     * example, the offset of degrees Celsius from Kelvin is +273.15. The offset of degrees Fahrenheit from degrees
     * Celsius is -32.
     *
     * @return the offset of this set of units from the parent, in terms of this set of units.
     * @since 1.0.0
     */
    public double offset()
    {
        return offset;
    }

    /**
     * Converts a numerical quantity from these units to the parent units. The conversion is equivalent to
     * <pre>
     * (quantity + offset()) * scale()
     * </pre>
     *
     * @param quantity the quantity to convert from this set of units to the parent.
     * @return the converted quantity in terms of the parent units.
     * @since 1.0.0
     */
    public double convertToParent(double quantity)
    {
        return (quantity + offset) * scale;
    }

    /**
     * Converts a numerical quantity from the parent units to these units. The conversion is equivalent to
     * <pre>
     * quantity / scale() - offset()
     * </pre>
     *
     * @param quantity the quantity to convert from the parent units to this set of units.
     * @return the converted quantity in terms of the this set of units.
     * @since 1.0.0
     */
    public double convertFromParent(double quantity)
    {
        return quantity / scale - offset;
    }
}
