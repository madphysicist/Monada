/*
 * Units.java (Class: com.madphysicist.monada.Units)
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * Represents base or derived units along a dimension or combination of dimensions. Examples of base units are meters
 * (in the SI system) and seconds (in the SI and CGS systems). Examples of derived units would be meters per second
 * (velocity), and meters per second per second (acceleration). Additionally, kilometers would be considered derived in
 * both the SI and CGS system. Although this class can be instantiated and used directly, it is recommended that it be
 * accessed through a {@code MeasurementSystem}.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 14 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class Units implements Serializable, DimensionComponentIterable
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

    private final String name;
    private final String abbreviation;
    private final double factor;
    private final double nullFactor;
    private final Dimension dimension;

    /**
     * Constructs base units for the given dimension.
     *
     * @param name the name of the units, for example "kilometers".
     * @param abbreviation the abbreviation of the units, for example "km".
     * @param dimension the dimension of the units, for example "Length".
     * @since 1.0.0
     */
    public Units(String name, String abbreviation, Dimension dimension)
    {
        this(name, abbreviation, 1.0, dimension);
    }

    public Units(String name, String abbreviation, double factor, Dimension dimension)
    {
        if(name == null)
            throw new NullPointerException("null Units name");
        this.name = name;
        this.abbreviation = abbreviation;
        this.factor = factor;
        this.nullFactor = (dimension instanceof BaseDimension && ((BaseDimension)dimension).isNull()) ? factor : 1.0;
        this.dimension = dimension;
    }

    /**
     * Constructs derived units that are a scale of another set of units.
     *
     * @param name
     * @param abbreviation
     * @param factor
     * @param units
     */
    public Units(String name, String abbreviation, double factor, Units units)
    {
        this(name, abbreviation, factor * units.factor, units.dimension);
    }

    public Units(String name, String abbreviation, double factor, Collection<Units> numerator, Collection<Units> denominator)
    {
        this(name, abbreviation, factor * computeFactor(numerator, denominator),
             computeDimension(name + " Dimension", "Temporary dimension for " + name + " units", numerator, denominator));
    }

    public String name()
    {
        return name;
    }

    public String abbreviation()
    {
        return abbreviation;
    }

    public double factor()
    {
        return factor;
    }

    public Dimension dimension()
    {
        return dimension;
    }

    @Override public Iterator<DimensionComponent> iterator()
    {
        return dimension.iterator();
    }

    @Override public int componentCount()
    {
        return dimension.componentCount();
    }

    protected static double computeFactor(Collection<Units> numerator, Collection<Units> denominator)
    {
        double factor = 1.0;

        for(Units units : numerator)
            factor *= units.factor;

        for(Units units : denominator)
            factor /= units.factor;

        return factor;
    }

    protected static Dimension computeDimension(String name, String description,
                                                Collection<? extends DimensionComponentIterable> numerator,
                                                Collection<? extends DimensionComponentIterable> denominator)
    {
        Collection<DimensionComponentIterable> components = new ArrayList<>(numerator.size() + denominator.size());

        if(numerator != null)
            components.addAll(numerator);

        if(denominator != null) {
            for(DimensionComponentIterable iterable : denominator) {
                for(DimensionComponent component : iterable)
                    components.add(new DimensionComponent(component.dimension(), -1.0f * component.exponent()));
            }
        }

        return new DerivedDimension(name, description, components);
    }
}
