/*
 * Quantity.java (Class: com.madphysicist.monada.Quantity)
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
package com.madphysicist.monada.temp;

import java.io.Serializable;

/**
 * <p>
 * A number with units. This class encapsulates a scalar quantity with units.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @version 1.0.0, 16 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class Quantity implements Serializable
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
     * The value of the scalar.
     *
     * @serial
     * @since 1.0.0
     */
    private final double value;

    /**
     * The units of this quantity. The same units apply to every numerical value in the quantity.
     *
     * @serial
     * @since 1.0.0
     */
    private final Units units;

    public Quantity(double value, Units units)
    {
        this.value = value;
        this.units = units;
    }

    public double value()
    {
        return value;
    }

    public Units units()
    {
        return units;
    }
}
