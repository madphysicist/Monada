/*
 * package-info.java (Package: com.madphysicist.monada)
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

/**
 * The purpose of this package is to create a framework for unit conversions. The main classes of this package are
 * {@link com.madphysicist.monada.MeasurementSystem} and {@link com.madphysicist.monada.Arithmetic}.
 * {@code MeasurementSystem} maintains a self-consistent database of dimensions and units. It allows for lookup of units
 * according to their dimension, name and other attributes. {@code Arithmetic} contains methods for performing unit
 * conversions as well as common operations on both scalar and vector quantities.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0, 14 Jul 2014 - J. Fox-Rabinovitz: Initial coding.
 * @since 1.0
 */
package com.madphysicist.monada;