/*
 * DerivedDimension.java (Class: com.madphysicist.monada.DerivedDimension)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.madphysicist.monada.temp.MeasurementSystem;
import com.madphysicist.tools.util.HashUtilities;

/**
 * <p>
 * Defines a dimension that is a combination of base dimensions. A derived dimension is the product of base dimensions
 * raised to some power. The product is specified as a collection of {@code DimensionComponent} objects, each of which
 * encapsulates a base dimension and its exponent. Although this class can be instantiated directly, it is best to use
 * it through a {@link MeasurementSystem}.
 * </p>
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 16 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class DerivedDimension extends Dimension
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
     * The list of components that make up this dimension. This list is neither {@code null} nor empty, as enforced by
     * the constructor. The elements in this list are always sorted in descending order according to the natural
     * ordering imposed by {@link DimensionComponent#compareTo(DimensionComponent)}. This list is unmodifiable to
     * preserve the immutability of this class.
     *
     * @serial
     * @since 1.0.0
     */
    private final List<DimensionComponent> components;

    /**
     * Constructs a derived dimension with the specified name, base components and no description. When supplying the
     * components, not that both {@link Dimension} and {@code DimensionComponent} implement {@code
     * Iterable<DimensionComponent>}.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param components a collection of the components from which this dimension is derived. All of the elements of
     * the iterators obtained from the collection are multiplied. May not be {@code null} or empty. Must contain at
     * least one actual element. This reference is not stored directly in the object and may be modified freely.
     * @throws NullPointerException if either {@code name} or {@code components} are {@code null}.
     * @throws IllegalArgumentException if {@code components} does not contain a single iterator with an element.
     * @since 1.0.0
     */
    public DerivedDimension(String name, Collection<DimensionComponentIterable> components)
            throws NullPointerException, IllegalArgumentException
    {
        this(name, null, components);
    }

    /**
     * Constructs a dimension with the specified name, optional description and base components. When supplying the
     * components, not that both {@link Dimension} and {@code DimensionComponent} implement {@code
     * Iterable<DimensionComponent>}.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param description a (brief) description of what this dimension represents. May be {@code null} to indicate no
     * description.
     * @param components a collection of the components from which this dimension is derived. All of the elements of
     * the iterators obtained from the collection are multiplied. May not be {@code null} or empty. Must contain at
     * least one actual element. This reference is not stored directly in the object and may be modified freely.
     * @throws NullPointerException if either {@code name} or {@code components} are {@code null}.
     * @throws IllegalArgumentException if {@code components} does not contain a single iterator with an element.
     * @since 1.0.0
     */
    public DerivedDimension(String name, String description, Collection<DimensionComponentIterable> components)
            throws NullPointerException, IllegalArgumentException
    {
        super(name, description);
        if(components == null)
            throw new NullPointerException("Components required");
        this.components = Collections.unmodifiableList(normalize(components));
    }

    /**
     * Constructs a derived dimension with the specified name, base components and no description. When supplying the
     * components, not that both {@link Dimension} and {@code DimensionComponent} implement {@code
     * Iterable<DimensionComponent>}.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param components an array of the components from which this dimension is derived. All of the elements of
     * the iterators obtained from the collection are multiplied. May not be {@code null} or empty. Must contain at
     * least one actual element. This reference is not stored directly in the object and may be modified freely.
     * @throws NullPointerException if either {@code name} or {@code components} are {@code null}.
     * @throws IllegalArgumentException if {@code components} does not contain a single iterator with an element.
     */
    public DerivedDimension(String name, DimensionComponentIterable... components)
            throws NullPointerException, IllegalArgumentException
    {
        this(name, null, components);
    }

    /**
     * Constructs a dimension with the specified name, optional description and base components. When supplying the
     * components, not that both {@link Dimension} and {@code DimensionComponent} implement {@code
     * Iterable<DimensionComponent>}.
     *
     * @param name the name of this dimension. May not be {@code null}.
     * @param description a (brief) description of what this dimension represents. May be {@code null} to indicate no
     * description.
     * @param components an array of the components from which this dimension is derived. All of the elements of
     * the iterators obtained from the collection are multiplied. May not be {@code null} or empty. Must contain at
     * least one actual element. This reference is not stored directly in the object and may be modified freely.
     * @throws NullPointerException if either {@code name} or {@code components} are {@code null}.
     * @throws IllegalArgumentException if {@code components} does not contain a single iterator with an element.
     * @since 1.0.0
     */
    public DerivedDimension(String name, String description, DimensionComponentIterable... components)
            throws NullPointerException, IllegalArgumentException
    {
        this(name, description, Arrays.asList(components));
    }

    /**
     * Compares this dimension to another one by its components, then by its base properties. This comparison is
     * consistent with {@code equals()}.
     *
     * @param o {@inheritDoc}
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public int compareTo(Dimension o)
    {
        // Compare components
        int comp = compareComponents(o);
        if(comp != 0)
            return comp;

        // If components truly identical, compare base properties
        return super.compareTo(o);
    }

    /**
     * @since 1.0.0
     */
    @Override public int componentCount()
    {
        return components.size();
    }

    /**
     * Returns an ordered iterator over the components of this dimension. This iterator is guaranteed to contain at
     * least one element. The elements are sorted according to their natural order. The returned iterator does not allow
     * modification of the underlying collection.
     *
     * @since 1.0.0
     */
    @Override public Iterator<DimensionComponent> iterator()
    {
        return components.iterator();
    }

    /**
     * Checks if another object is equal to this one. Two objects are equal if they are of the same type and have the
     * same base properties (see {@link Dimension#equals(Object)}, as well as equal dimension components. This method is
     * consistent with both {@link #hashCode()} and {@link #compareTo(Dimension)}. 
     * 
     * @param o {@inheritDoc}
     * @return {@inheritDoc}
     * @see Dimension#compareComponents(Dimension)
     * @since 1.0.0
     */
    @Override public boolean equals(Object o)
    {
        if(super.equals(o)) // true result guarantees that o is a DerivedDimension
            return this.compareComponents((Dimension)o) == 0;
        return false;
    }

    /**
     * Computes the hash code for this object. The hash code depends on the base properties (see {@link
     * Dimension#hashCode()} as well as the null status. This method is consistent with {@link #equals(Object)}.
     *
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public int hashCode()
    {
        int hashCode = super.hashCode();
        for(DimensionComponent component : components) {
            hashCode = HashUtilities.hashCode(hashCode, component);
        }
        return hashCode;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return {@inheritDoc}
     * @since 1.0.0
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n    Derived Dimension:");
        for(DimensionComponent component : this) {
            sb.append(' ').append(component.dimension().name());
            float exponent = component.exponent();
            if(exponent != 1.0f)
                sb.append('^').append(exponent);
        }
        return sb.toString();
    }

    /**
     * Normalizes a {@code Collection} of {@code DimensionComponent} objects. The resulting list contains only one
     * component for each base dimension. It is sorted according to the {@linkplain
     * DimensionComponent#compareTo(DimensionComponent) natural order} of the components. Since all components with the
     * same dimension in the input are combined by adding their exponents, collections with different elements may yield
     * identical results after normalization. The returned list is modifiable. It should be wrapped in an unmodifiable
     * list for internal storage by the constructor.
     *
     * @param components a collection of components to normalize.
     * @return a normalized, modifiable list based on the input collection.
     * @throws IllegalArgumentException if none of the {@code Iterator}s obtained from {@code components} contains a 
     * valid {@code DimensionComponent}, or if the net result is truly dimensionless (as opposed ot containing only null
     * dimensions).
     * @since 1.0.0
     */
    private List<DimensionComponent> normalize(Collection<DimensionComponentIterable> components)
            throws IllegalArgumentException
    {
        Map<BaseDimension, DimensionComponent> mapSet = new HashMap<>(components.size());
        for(DimensionComponentIterable iterable : components)
            combineDimensionComponents(mapSet, iterable, 1.0f);

        if(mapSet.size() == 0)
            throw new IllegalArgumentException("Missing valid DimensionComponents");

        List<DimensionComponent> componentList = new ArrayList<>(mapSet.values());
        Collections.sort(componentList);
        return componentList;
    }
}
