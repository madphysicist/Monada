/*
 * DimensionComponentIterable.java (Interface: com.madphysicist.monada.DimensionComponentIterable)
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
 * An interface that extends {@code Iterable} over a fixed number of elements. The number of elements can be obtained
 * as well.
 * </p>
 * </p>
 * <b>An aside to the developer: </b>Part of the reason for this interface is to avoid unchecked casts to arrays of
 * generic iterables when constructing @{code DerivedDimension}s and {@code Units}.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 20 Aug 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public interface DimensionComponentIterable extends Iterable<DimensionComponent>
{
    /**
     * Returns an iterator over the components in the iterable. This method has an additional restriction: the returned
     * elements must be sorted according to the natural order of {@code DimensionComponent}.
     *
     * @return an iterator over the components of this object.
     * @since 1.0.0
     */
    @Override public Iterator<DimensionComponent> iterator(); 

    /**
     * Returns the number of elements in the iterator. This number must be a constant by the contract of this interface.
     *
     * @return the number of elements in this {@code Iterable}'s iterator.
     * @since 1.0.0
     */
    public int componentCount();
}
