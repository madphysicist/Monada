/*
 * Prefix.java (Class: com.madphysicist.monada.Prefix)
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

import com.madphysicist.tools.util.HashUtilities;

/**
 * <p>
 * Describes a prefix that can be used to identify scaled units. A prefix has both a long and an abbreviated form. Using
 * prefixes allows a {@code MeasurementSystem} to contain fewer actual units. For example, the default SI {@code
 * MeasurementSystem} stores units of meters (m) and the prefix kilo (k). Requesting a unit kilometer (km) will parse
 * out the prefix and return a meaningful result even though the unit may not have originally been in the database.
 * </p>
 * <p>
 * As a convenience, prefixes are comparable first by factor, then by textual representation. The comparison is
 * consistent with equals.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 20 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class Prefix implements Serializable, Comparable<Prefix>
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
     * The long form of the prefix, example "kilo". May not me {@code null}, as enforced by the constructor.
     *
     * @serial
     * @since 1.0.0
     */
    private final String longForm;

    /**
     * The short form of the prefix, for example "k", for "kilo". May be {@code null} to indicate that the short form
     * is unknown or does not exist.
     *
     * @serial
     * @since 1.0.0
     */
    private final String abbreviation;

    /**
     * The multiplicative factor associated with the prefix. For example, the prefix "kilo" has a factor of 1000 or 1e3.
     *
     * @serial
     * @since 1.0.0
     */
    private final double factor;

    /**
     * Constructs a prefix with the specified long form, and factor. The long form must be provided. This is eqivalent
     * to calling the other constructor with the arguments {@link #Prefix(String, String, double) Prefix(longForm, null,
     * factor)}.
     *
     * @param longForm the long form of the prefix that is used when units are spelled out completely. May not be {@code
     * null}.
     * @param factor the factor that this prefix multiplies units by.
     * @throws NullPointerException if {@code longForm} is {@code null}.
     * @since 1.0.0
     */
    public Prefix(String longForm, double factor)
    {
        this(longForm, null, factor);
    }

    /**
     * Constructs a prefix with the specified long form, abbreviation and factor. The long form must be provided but the
     * abbreviation may be omitted.
     *
     * @param longForm the long form of the prefix that is used when units are spelled out completely. May not be {@code
     * null}.
     * @param abbreviation the form of the prefix used with abbreviated units. May be {@code null} to indicate that the
     * prefix has no abbreviation.
     * @param factor the factor that this prefix multiplies units by.
     * @throws NullPointerException if {@code longForm} is {@code null}.
     * @since 1.0.0
     */
    public Prefix(String longForm, String abbreviation, double factor) throws NullPointerException
    {
        if(longForm == null)
            throw new NullPointerException("long form of prefix may not be null");
        this.longForm = longForm;
        this.abbreviation = abbreviation;
        this.factor = factor;
    }

    /**
     * Returns the long form of the prefix. The long form is used when the units to which the prefix applies are written
     * out in full. For example, "kilo" in "kilometers".
     *
     * @return the long form of the prefix. This reference will never be {@code null}.
     * @since 1.0.0
     */
    public String longForm()
    {
        return longForm;
    }

    /**
     * Returns the abbreviation of the prefix. The long abbreviation is used when the units to which the prefix applies
     * are written in abbreviated form. For example, "k" in "km".
     *
     * @return the abbreviation of the prefix. This reference may be {@code null} to indicate that the prefix has no
     * abbreviation.
     * @since 1.0.0
     */
    public String abbreviation()
    {
        return abbreviation;
    }

    /**
     * Returns the factor associated with this prefix. The factor is the amount by which this prefix multiplies the
     * units to which it is applied. For example, 1000 is the factor for kilo.
     *
     * @return the factor associated with this prefix.
     * @since 1.0.0
     */
    public double factor()
    {
        return factor;
    }

    /**
     * {@inheritDoc}.
     * The comparison is done by factor, then by alphabetical order of the long form and then by alphabetical order of
     * the short form, where {@code null} is considered less than any non-{@code null} string. This method is consistent
     * with {@link #equals(Object)}.
     *
     * @since 1.0.0
     */
    @Override public int compareTo(Prefix p)
    {
        if(this.factor > p.factor)
            return 1;
        if(this.factor < p.factor)
            return -1;
        int comp = this.longForm.compareTo(p.longForm);
        if(comp != 0)
            return comp;
        if(this.abbreviation == null)
            return (p.abbreviation == null) ? 0 : -1;
        return (p.abbreviation == null) ? 1 : this.abbreviation.compareTo(p.abbreviation);
    }

    /**
     * {@inheritDoc}.
     * Two prefixes are equal if they have equal long forms, abbreviations, and the same factor.
     *
     * @since 1.0.0
     */
    @Override public boolean equals(Object o)
    {
        if(o.getClass() == getClass()) {
            Prefix p = (Prefix)o;
            return this.longForm.equals(p.longForm) &&
                   HashUtilities.refEquals(this.abbreviation, p.abbreviation) &&
                   this.factor == p.factor;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * The hash code of a prefix depends on its long form, abbreviation and factor.
     *
     * @since 1.0.0
     */
    @Override public int hashCode()
    {
        int code = HashUtilities.hashCode(longForm);
        code = HashUtilities.hashCode(code, abbreviation);
        code = HashUtilities.hashCode(code, factor);
        return code;
    }
}
