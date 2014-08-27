/*
 * Dimension.java (Class: com.madphysicist.monada.Dimension)
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * Defines a dimension. Dimensions may be base or derived. Base dimensions are orthogonal to all other base dimensions
 * in a given {@code MeasurementSystem} or other collection. Derived dimensions are combinations of base dimensions.
 * Examples of base dimensions in the SI measurement system are {@code Length} and {@code Time}. Examples of dimensions
 * derived from those two base dimensions would be {@code Area} ({@code Length^2}), {@code Velocity} ({@code
 * Length/Time}) and {@code Acceleration} ({@code Length/Time^2}). As these examples show, derived combinations are not
 * necessarily linear.
 * </p>
 * <p>
 * All Units lie along a dimension.
 * </p>
 * <p>
 * It is best to use this class through a {@link MeasurementSystem}, since it is intended to be treated as an enum
 * within a given {@code MeasurementSystem}. To this effect, it does not override {@code hashCode()} or {@code equals()}
 * from the default implementation. This way, two dimensions are equal if and only if they point to the same object, not
 * just if they have the same name and components.
 * </p>
 * <p>
 * Dimensions are comparable by name their components, name and description. The comparison is not consistent with
 * equals since two different dimension references with the same components, name and description will not be equal.
 * </p>
 * <p>
 * This class is immutable, and extending classes should preserve its immutability.
 * </p>
 *
 * @see BaseDimension
 * @see DerivedDimension
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 14 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public abstract class Dimension implements Serializable, Comparable<Dimension>, DimensionComponentIterable
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
     * The name of the dimension. This string may not be {@code null}, as enforced by the constructor.
     *
     * @see #name()
     * @serial
     * @since 1.0.0
     */
    private final String name;

    /**
     * An optional (brief) description the dimension. This string may be {@code null} to indicate a missing description.
     *
     * @see #description()
     * @serial
     * @since 1.0.0
     */
    private final String description;

    /**
     * Constructs a dimension with the specified name and no description.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @since 1.0.0
     */
    public Dimension(String name) throws NullPointerException
    {
        this(name, null);
    }

    /**
     * Constructs a dimension with the specified name and optional description.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param description a (brief) description of what this dimension represents. May be {@code null} to indicate no
     * description.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @since 1.0.0
     */
    public Dimension(String name, String description) throws NullPointerException
    {
        if(name == null)
            throw new NullPointerException("Dimension name required");
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of this dimension. This field will never be {@code null}.
     *
     * @return the name of this dimension.
     * @since 1.0.0
     */
    public String name()
    {
        return name;
    }

    /**
     * Returns the description of this dimension. This field may be {@code null} if no description was provided.
     *
     * @return a brief description of what this dimension represents.
     * @since 1.0.0
     */
    public String description()
    {
        return description;
    }

    /**
     * Compares this dimension to another one by its components, then by name and by description. This comparison is not
     * consistent with {@code equals()}.
     *
     * @since 1.0.0
     */
    @Override public int compareTo(Dimension o)
    {
        // Compare components
        int comp = compareComponents(o);
        if(comp != 0)
            return comp;

        // If components truly identical, compare by name
        comp = this.name().compareTo(o.name());
        if(comp != 0)
            return comp;

        // If components and name are equal, compare by description
        return this.description().compareTo(o.description());
    }

    /**
     * Returns a string representation of this dimension. This method recognizes instances of the
     * subclass {@code BaseDimension} to be a special type. All other subclasses are treated as
     * sequences of {@code DimensionComponent}s.
     *
     * @since 1.0.0
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(name);
        if(this instanceof BaseDimension) {
            sb.append(": BaseDimension");
        } else {
            sb.append(" = ");
            for(DimensionComponent component : this) {
                sb.append(component.dimension().name());
                float exponent = component.exponent();
                if(exponent != 1.0f)
                    sb.append('^').append(exponent);
                sb.append(' ');
            }
        }
        if(description != null)
            sb.append(" (").append(description).append(")");
        return sb.toString();
    }

    /**
     * Determines if this dimension has the same components as another dimension. Two dimensions are identical if they
     * contain the same {@code DimensionComponent}s. The name and description are irrelevant to this comparison. Note
     * that null base dimensions are not considered to be equal. This method relies on the respective iterators
     * returning elements in their natural order.
     *
     * @param o the dimension to test for sameness with.
     * @return {@code 0} if this dimension is identical to the specified one, {@code false} otherwise.
     * @since 1.0.0
     */
    public int compareComponents(Dimension o)
    {
        Iterator<DimensionComponent> thisIterator = iterator();
        Iterator<DimensionComponent> otherIterator = o.iterator();

        while(thisIterator.hasNext() && otherIterator.hasNext()) {
            int comp = thisIterator.next().compareTo(otherIterator.next());
            if(comp != 0)
                return comp;
        }

        return this.componentCount() - o.componentCount();
    }

    /**
     * Returns the number of components in this dimension. This method should always return the number of elements in
     * the iterator returned by {@link #iterator()}.
     *
     * @return the number of {@code DimensionComponent}s that this dimension consists of. This is also the number of
     * base dimensions that figure in this dimension.
     * @since 1.0.0
     */
    @Override public abstract int componentCount();

    /**
     * Returns an ordered iterator over the components of this dimension. The components must be sorted in descending
     * order. If this dimension is a base dimension, the iterator will only contain a single element. The returned
     * iterator should preserve the immutability of this class.
     *
     * @return an iterator over the components of this dimension, in descending sorted order.
     * @since 1.0.0
     */
    @Override public abstract Iterator<DimensionComponent> iterator();

    /**
     * A utility method for combining the dimension components of various iterable entities such as Dimensions and
     * Units. This method allows for both multiplication and division by including a preprocessing factor to be applied
     * to the exponents being added.
     *
     * @param map a map containing the {@code DimensionComponent}s that have already been accumulated, keyed by their
     * corresponding {@code BaseDimension}s. This map may be {@code null}, in which case it will be allocated.
     * @param components the components to add to the map. This reference may not be {@code null}.
     * @param eFactor a factor by which the exponent of the components being addded is pre-multiplied before being added
     * to the map. This allows for both multiplication ({@code eFactor = 1.0f}) and and division ({@code
     * eFactor = -1.0f}) by the new components. The value of this argument should generally be either positive or
     * negative one.
     * @since 1.0.0
     */
    protected static Map<BaseDimension, DimensionComponent> combineDimensionComponents(Map<BaseDimension, DimensionComponent> map,
                                                                                       DimensionComponentIterable components, float eFactor)
    {
        if(map == null)
            map = new HashMap<>();

        for(DimensionComponent component : components) {
            BaseDimension dim = component.dimension();
            DimensionComponent prevComponent = map.get(dim);
            if(prevComponent == null) {
                if(eFactor == 1.0f)
                    map.put(dim, component); // Do not create new object if exponent factor is 1, which it will be a lot of the time 
                else
                    map.put(dim, new DimensionComponent(dim, eFactor * component.exponent()));
            } else {
                float newExponent = prevComponent.exponent() + eFactor * component.exponent();
                if(newExponent == 0.0f)
                    map.remove(dim);
                else
                    map.put(dim, new DimensionComponent(dim, newExponent));
            }
        }

        return map;
    }
}
