Monada
======

A suite of Java classes for working with measurement units and dimensions. The suite contains a library that allows users to create their own measurement systems, individual units and dimensions. The MeasurementSystem class also comes preconfigured with a fairly extensive set of units with conversions to the SI measurement system.

This suite was written before I had any knowledge of GNU units. Many of the concepts are necessarily the same, but some are treated quite differently. My hope is not to supercede or replace anything, but to give a new take on a subject that may or may not have been beaten to death at this point. Also, to finaly put some of my (very early) physics education to some kind of use. I am always open to the discussion of new features.

Name
----

Monada is the Greek word for unit. It also happens to mean "cute" or "pretty" in Spanish, which is just a fortuitous coincidence (I am not a linguist).

How it Works
------------

`Units` are allowed to have a scale and an offset along a given `Dimension` relative to some base units (which have a scale of 1.0 and an offset of 0.0). Most units have no offset. The notable exception is temperature.

`Units` always lie along a `Dimension`. There are two conceptual types of `Dimension`: `BaseDimension`, which is orthogonal to every other `BaseDimension`, and `DerivedDimension`, which is a product of other `Dimension`s raised to some arbitrary exponents (e.g. Length, Time, Acceleration = Length^1 * Time^-2). Every dimension has a base unit, although it may be implicitly rather than explicitly defined.

Students of physics may have noted that radians are technically "dimensionless" even though they represent a conceptually distinct type of quantity. To encapsulate this idea, `BaseDimensions`, such as angle can be marked as being `null`. This means that units along this dimension are "unitless", possibly after conversion to the base unit. This way, scalar functions such as `Arithmetic.sin(Quantity)` can be expected to yield the correct result even when a `Quantity` in revolutions or picodegrees is passed in.

A `Quantity` is just a scalar associated with a set of `Units`. Computations with quantities are done by the static `Arithmetic` class. This class has rules for how `Units` may be combined for various operations. For example, addition may be allowed only for units with the exact same dimension, or with similar dimensions up to null dimensions. The later case may or may not enforce a conversion. The class documentation for `Arithmetic` contains more information.

The `MeasurementSystem` class allows the user to load or define sets of `Units`, `Dimension`s and `Prefix`es which can be used for lookup. For example, the preexisting SI MeasurementSystem will generally contain a dimension and at least one set of units relevant to any reasonable, physically meaningful multiplication.

`Prefix`es allow a `MeasurementSystem` to interpret names such as "picodegree" correctly without the unit being explicitly defined. When "picodegree" is not found in the database, a `MeasurementSystem` will check if it starts with a known prefix ("pico"), and search for the remainder ("degree"). Since the default measurement system recognizes both metric and binary prefixes, units such as "kibimetere" are just as valid as "kilometre".

Ports
-----

Monada will be ported to C and Python at some point in the future.

Notes on Dependencies
---------------------

This project depends on the Mad Physicist JTools project. The JTools jar file must be on the classpath when building the library and running the parser.

Test code uses TestNG version 6.8 or above.
