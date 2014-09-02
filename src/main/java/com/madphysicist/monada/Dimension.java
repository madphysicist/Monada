/*
 * Dimension.java (Class: com.madphysicist.monada.Dimension)
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.madphysicist.monada.temp.MeasurementSystem;
import com.madphysicist.tools.util.HashUtilities;

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
 * within a given {@code MeasurementSystem}.
 * </p>
 * <p>
 * Dimensions are comparable by name their components, name and description. The comparison is consistent with
 * equals. Extending classes should preserve this property.
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
     * Determines if this dimension has the same components as another dimension. The name and description are
     * irrelevant to this comparison. Note that null base dimensions are not considered to be equal. This method relies
     * on the respective iterators returning elements in their natural order, as required by contract.
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
     * Compares this dimension to another one by name and by description. by description and finally by class name. The
     * class name comparison ensures that a dimension of a different type will not be equal to this one. This comparison
     * is therefore consistent with {@code equals()}. This method should be overridden by extending classes to preserve
     * its consistency with {@code equals()} by including comparisons of any relevant properties.
     *
     * @param o {@inheritDoc}
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public int compareTo(Dimension o)
    {
        int comp;

        // Compare by name
        comp = this.name().compareTo(o.name());
        if(comp != 0)
            return comp;

        // If names are equal, compare by description
        if(this.description == null) {
            comp = (o.description == null) ? 0 : 1;
        } else {
            comp = this.description().compareTo(o.description());
        }
        if(comp != 0)
            return comp;

        // If name and description are equal, compare by class name
        return this.getClass().getName().compareTo(o.getClass().getName());
    }

    /**
     * Tests if two dimensions are equal. This method only compares the abstract base portion of this class. The name
     * and description are compared. Extending classes should override this method to include any additional properties
     * they define. Note that two dimensions can not be equal if they are not of the same class. This property should be
     * preserved by the {@code #compareTo(Dimension)} method of any extending class.
     *
     * @param o {@inheritDoc}
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public boolean equals(Object o)
    {
        if(o != null && this.getClass() == o.getClass()) {
            Dimension dim = (Dimension)o;
            return this.name.equals(dim.name) && HashUtilities.refEquals(this.description, dim.description);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * This method only computes the hash code for this abstract base class. Extending classes should override this
     * method to be consistent with equals. The hash code depends the name and description of this dimension.
     *
     * @since 1.0.0
     */
    @Override public int hashCode()
    {
        int hashCode = HashUtilities.hashCode(name);
        return HashUtilities.hashCode(hashCode, description);
    }

    /**
     * Returns a string representation of this dimension. This string only displays the name and description, if it
     * is not missing. Extending classes should override this method.
     *
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(name);
        if(description != null)
            sb.append(" (").append(description).append(")");
        return sb.toString();
    }

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
                else {
                    float newExponent = eFactor * component.exponent();
                    if(newExponent != 0.0f)
                        map.put(dim, new DimensionComponent(dim, newExponent));
                }
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
