/*
 * MeasurementSystem.java (Class: com.madphysicist.monada.MeasurementSystem)
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.madphysicist.tools.io.FileParseException;

/**
 * <p>
 * Maintains a database of different dimensions and units, both raw and derived. Provides a framework for search and
 * lookup of registered dimensions and units. The purpose of this class is to perform lookups so that the results of
 * operations get the most appropriate units automatically assigned to them, as well as to facilitate conversions.
 * </p>
 * <p>
 * A measurement system can have a hierarchy of parents. When a lookup fails in the current system, it continues
 * recursively in the parent system(s). Parent systems are never modified. All modifications are done to the current
 * system only. Note that elements in the current system (prefixes, dimensions, units) override those of the parent
 * system. Each system in the hierarchy may have a different type of inheritance. Inheritance types are described in the
 * {@linkplain #MeasurementSystem(MeasurementSystem, InheritanceRule) constructor}'s documentation.
 * </p> 
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0, 14 Jul 2014 - J. Fox-Rabinovitz - Initial coding
 * @since 1.0
 */
public class MeasurementSystem implements Serializable
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
     * Describes the type of relationship a {@code MeasurementSystem} has to its parent when lookups that return
     * multiple results are performed. Only one type of inheritance rule exists for lookups that return a single result. 
     *
     * @author Joseph Fox-Rabinovitz
     * @version 1.0.0, 14 Aug 2014 - J. Fox-Rabinovitz - Initial coding
     * @since 1.0
     */
    public static enum InheritanceRule
    {
        /**
         * Look up any available elements in the parent which are not overridden by name in the child system.
         *
         * @since 1.0.0
         */
        MERGE,

        /**
         * Do not perform lookup in the parent if any results are found in the child system.
         *
         * @since
         */
        OVERRIDE
    }

    /**
     * The default inheritance rule.
     *
     * @since 1.0.0
     */
    private static final InheritanceRule DEFAULT_INHERITANCE_RULE = InheritanceRule.MERGE;

    private static final MeasurementSystem SI_INSTANCE;
    static {
        try {
            SI_INSTANCE = loadMeasurementSystem(MeasurementSystem.class.getPackage().getName() + ".SI", "SI");
        } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new ExceptionInInitializerError(ioe);
        }
    }

    private final MeasurementSystem parent;
    private final InheritanceRule inheritanceRule;

    private final String name;

    private final Map<String, Dimension> dimensionNameLookup;
    private final List<Dimension> dimensionComponentLookup;

    private final Map<String, Prefix> prefixes;

    public MeasurementSystem()
    {
        this(null, null, DEFAULT_INHERITANCE_RULE);
    }

    public MeasurementSystem(String name)
    {
        this(null, name, DEFAULT_INHERITANCE_RULE);
    }

    public MeasurementSystem(MeasurementSystem parent)
    {
        this(parent, null, DEFAULT_INHERITANCE_RULE);
    }

    public MeasurementSystem(MeasurementSystem parent, String name)
    {
        this(parent, name, DEFAULT_INHERITANCE_RULE);
    }

    public MeasurementSystem(MeasurementSystem parent, InheritanceRule inheritanceRule)
    {
        this(parent, null, inheritanceRule);
    }

    public MeasurementSystem(MeasurementSystem parent, String name, InheritanceRule inheritanceRule)
    {
        this.parent = parent;
        this.inheritanceRule = inheritanceRule;
        this.name = name;
        this.dimensionNameLookup = new HashMap<>();
        this.dimensionComponentLookup = new ArrayList<>();
        this.prefixes = new HashMap<>();
    }

    public MeasurementSystem parent()
    {
        return parent;
    }

    public String name()
    {
        return name;
    }

    public InheritanceRule inheritanceRule()
    {
        return inheritanceRule;
    }

    /**
     * Adds a new dimensions, if the name is not already present in the database.
     *
     * @param dimension the dimension to add.
     * @return {@code true} if the dimension was added, {@code false} if a dimension with the same name already exists
     * in the database.
     * @since 1.0.0
     */
    public boolean addDimension(Dimension dimension)
    {
        Dimension dim = dimensionNameLookup.get(dimension.name());
        if(dim == null) {
            dimensionNameLookup.put(dimension.name(), dimension);
            // This is guaranteed to return a negative number since at least the name is different
            int index = Collections.binarySearch(dimensionComponentLookup, dimension);
            if(index >= 0)
                throw new RuntimeException("Impossible condition encountered while adding new dimension");
            else
                index = -(index + 1);
            dimensionComponentLookup.add(index, dimension);
            return true;
        }
        return false;
    }

    public boolean replaceDimension(Dimension dimension)
    {
        boolean exists = removeDimension(dimension);
        addDimension(dimension);
        return exists;
    }

    public boolean removeDimension(Dimension dimension)
    {
        Dimension dim = dimensionNameLookup.remove(dimension.name());
        if(dim != null) {
            dimensionComponentLookup.remove(dim);
            return true;
        }
        return false;
    }

    /**
     * Returns a measurement system initialized with dimensions and units compatible with the SI system. The returned
     * system has a number of additional elements not defined in SI, such as binary prefixes and memory units (bits and
     * bytes).
     *
     * @return a measurement system compatible with SI.
     * @since 1.0.0
     */
    public static MeasurementSystem getSIInstance()
    {
        return SI_INSTANCE;
    }

    public static MeasurementSystem loadMeasurementSystem(String resourceName, String name) throws FileParseException, IOException
    {
        return loadMeasurementSystem(resourceName, null, name);
    }

    /**
     * <p>
     * A routine for loading entire measurement systems from a formatted text file. The text file is a cross between
     * an INI and a CSV file. It contains sections with names in brackets. Each section contains tab-delimited entries
     * describing various types of information.
     * </p>
     * <p>
     * The file format allows comments and most types of white space. The part of a line following a hash symbol (#) is
     * considered comment and ignored. White space is trimmed from each entry. Empty lines (or lines that are all
     * comment and/or whitespace are skipped).
     * </p>
     * <p>
     * Recognized sections are {@code [Prefixes]}, {@code [Dimensions/Base]}, {@code [Dimensions/Derived]}, {@code
     * [Units]}, {@code [Units/Alias]}, {@code [Units/Offset]}. Although the entries in each section are formatted
     * similarly as tab-delimited lines, the fields are different for each section. Sections may appear multiple times,
     * which can help with references and dependencies within them. Unrecognized sections cause an error. None of the
     * sections are strictly required. There are however a number of interdependencies with the sections:
     * <ul>
     * <li>{@code [Dimensions/Derived]} depends on {@code [Dimensions/Base]}</li>
     * <li>{@code [Units]} depends on at least one of {@code [Dimensions/Base]}, {@code [Dimensions/Derived]}.</li>
     * <li>{@code [Units/Alias]} depends on {@code [Units]} and possibly on {@code [Units/Offset]}</li>
     * <li>{@code [Units/Offset]} depends on {@code [Units]} and possibly on {@code [Units/Alias]}</li>
     * </ul>
     * {@code [Prefixes]} and {@code [Dimension/Base]} are the only truly stand-alone sections.
     * </p>
     * <p>
     * Entries in the file which reference other entries must appear after the entry being referenced. To avoid
     * dependency problems, sections may appear more than once, with only the relevant elements in each section.
     * </p>
     * <p>
     * Section formats are as follows:
     * 
     * </p>
     * <p>
     * If a prefix does not have a known abbreviation, the corresponding field should be left blank, for example:
     * <pre>
     *     demi,,2^-1 # Not strictly SI
     * </pre>
     * </p>
     *
     * @param resourceName the name of the resource to load the measurement system from.
     * @param parent the optional parent measurement system. May be {@code null} to indicate no parent.
     * @param name the name of the measurement system to load.
     * @throws FileParseException if the configuration file has an error in it. The exception will contain detailed
     * information about the line and possibly the column at which the exception occurred.
     * @throws IOException if an error occurs opening or reading the resource.
     * @since 1.0.0
     */
    public static MeasurementSystem loadMeasurementSystem(String resourceName, MeasurementSystem parent, String name) throws FileParseException, IOException
    {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        MeasurementSystem.class.getClassLoader().getResourceAsStream(resourceName)))) {
            String line;
            while((line = reader.readLine()) != null) {
                
            }
        }
        return null; // TODO
    }

    /**
     * Prints the conversions of 0 and 100 degrees to and from parent units for each of the eight temperature units.
     * All of the temperature scales except rankine and kelvin are offset from absolute zero.
     *
     * @since 1.0.0
     *\/
    private static void printTemperatureConversions()
    {
        String[] temperatureScales = new String[] {"celsius", "fahrenheit", "kelvin", "rankine", "delisle", "newton degree", "réaumur", "rømer"};

        System.out.println("Conversion tables for 8 temperature scales relative to their parents:");
        for(String name : temperatureScales) {
            Units units = SI_INSTANCE.getUnits(name);
            if(units instanceof OffsetUnits) {
                OffsetUnits tempUnits = (OffsetUnits)units;
                System.out.println(tempUnits.name() + ": parent = " + tempUnits.parent());
                System.out.println("0" + tempUnits.abbreviation() + " = " + tempUnits.convertToParent(0) + tempUnits.parent().abbreviation());
                System.out.println("0" + tempUnits.parent().abbreviation() + " = " + tempUnits.convertFromParent(0) + tempUnits.abbreviation());
                System.out.println("100" + tempUnits.abbreviation() + " = " + tempUnits.convertToParent(100) + tempUnits.parent().abbreviation());
                System.out.println("100" + tempUnits.parent().abbreviation() + " = " + tempUnits.convertFromParent(100) + tempUnits.abbreviation());
            } else {
                System.out.println(units.name() + ": factor = " + units.factor());
            }
        }
    }*/

    /**
     * Runs a demo of the SI measurement system. The output results can be used to do a visual inspection of the unit
     * conversions to check for accuracy.
     *
     * @param args command-line arguments, which are ignored.
     * @see #getSIInstance()
     * @since 1.0.0
     */
    public static void main(String[] args)
    {
        System.out.println("Demo of built-in SI Measurement System:");
        //printTemperatureConversions();
    }
}
