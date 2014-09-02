/*
 * TestDimension.java (Class: com.madphysicist.monada.TestDimension)
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
import java.util.Iterator;

/**
 * A trivial implementation of {@code Dimension}. The abstract methods all return {@code 0} and empty iterators. Direct
 * access is provided to the superconstructors. This class is intended to give access to tests of the non-abstract
 * methods of the base class.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 27 Aug 2014 - J. Fox-Rabinovitz - Initial coding.
 * @since 1.0
 */
public class TestDimension extends Dimension
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
    private static final long serialVersionUID = 10000L;

    /**
     * Redirect's to the single-argument superconstuctor.
     * 
     * @param name {@inheritDoc}
     * @since 1.0.0
     */
    public TestDimension(String name)
    {
        super(name);
    }

    /**
     * Redirect's to the two-argument superconstuctor.
     * 
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @since 1.0.0
     */
    public TestDimension(String name, String description)
    {
        super(name, description);
    }

    /**
     * The number of components in this dimension. Always returns {@code 0}.
     *
     * @return {@code 0}
     * @since 1.0.0
     */
    @Override public int componentCount()
    {
        return 0;
    }

    /**
     * An empty iterator over the non-existent components of this dimension.
     *
     * @return an iterator over an empty collection.
     * @since 1.0.0
     */
    @Override public Iterator<DimensionComponent> iterator()
    {
        return new ArrayList<DimensionComponent>(0).iterator();
    }
}
