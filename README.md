Eve 0.3.6
=========

An experiment to learn how to make an interpreter.<br>
Eve is a simple interpreted language that targets the JVM.

Features
--------
Eve is a dynamically strongly typed, pure prototypal, object-oriented language:

* Everything is an object, and there are no classes.
* Objects are generated by cloning prototypes and existing objects.
* Objects have no delegation relationship with one another (as in 
  JavaScript); this reduces confusion and mistakes when dealing with
  references.
* Eve runs on the Java Virtual Machine, and integration with Java code is
  possible.
  
For more information about prototypal OOP programming, see the Wikipedia
article:

<http://en.wikipedia.org/wiki/Prototype-based_programming>

Usage
-----
First, write an eve file. See the wiki for language reference, tutorials, and
examples. Then, invoke the Eve interpreter:

    java -jar dist/eve.jar file.eve
    
You can invoke the executable jar with the `-h` option to get more a summary
of options used to tweak the interpreter.

Building
========
First, check out the code from the Git repository. The master branch always
reflects the latest stable release, while develop reflects the latest code
under development.

The primary requirement for building and running Eve is Java. Eve is developed
and tested on the "official" 1.6 HotSpot VM. It should work under Java 1.5, but
that environment is unsupported. It will not work under Java 1.4 or lower.

Eve uses Apache Ant for building and Apache Ivy for resolving dependencies.
Make sure to install those first. On Linux, your distro may have them.
For example, to install Ant and Ivy on Ubuntu 10.10:

    sudo apt-get install ant ivy
    
If necessary, install Ant and Ivy manually. They can be found at:

<http://ant.apache.org/><br/>
<http://ant.apache.org/ivy/>

Once having installed Ant and Ivy and putting them on your classpath, run the
build script by typing "ant" in the root of the repository. Dependencies will
be fetched and added to the classpath automatically.

The build process will produce an executable jar `eve.jar` in the `dist`
directory. Execute `java -jar dist/eve.jar` to start the interpreter.

Installation
------------
Starting with 0.3.4, there is now a task in the build.xml file that will install
eve to `/usr/local/` on Unix machines. Run `sudo ant install` (or equivalent)
and you will have `/usr/local/bin/eve`. This allows eve to be executed more
naturally:

    eve file.eve

Tests
-----
If you want to run the test suite, run `ant test` instead of `ant jar`.

Troubleshooting
---------------
### Building Issues ###
Before running Ant, make sure Ivy is on your classpath. If Ivy is not on the
classpath, the dependecy resolution task will fail. For example, to get Ivy on
the classpath in Ubuntu 10.10:

`export CLASSPATH=/usr/share/java/ivy.jar:$CLASSPATH`

### Interpreter Can't Find Dependencies ###
The executable jar requires a number of dependencies. Make sure a `lib/`
directory exists in the location where eve.jar is, and make sure
all required dependencies are in that directory. If those are present, Eve
should able to be run from anywhere. The build process should dump all required
dependencies into the `dist/` directory for you.

Release Notes
=============
0.3.6:

* Switch to "slots" methodology of storing properties. This brings a significant
  change to the way Eve handles properties, and puts it more in line with other
  prototypal languages:
    * An object has any number of "slots," identified by a string or number.
    * Any value can go into a slot, and a slot can change type at any time.
    * Numbers and numbers in string form are the same for slot identifiers
      (e.g. obj[0] and obj["0"] point to the same thing).
    * Because of this change, the `dict` type was removed, as every object is
      basically a dictionary now.
    * Properties can be referenced via dot notation (if they are not numbers),
      or through "array" notation: `obj.myProperty` and `obj["myProperty"]`.
    * Significant internal restructuring was done to accomodate these changes.
* EJI enhancements, most of which were done to support the slots change:
    * Added reverse type changing between Java and Eve types in certain cases
      (e.g. java.lang.Integer will become EveInteger).
    * Numerous fixes properly allow for EJI types that extend EveObject.
    * Because of the above, the builtin types now get standard functions and
      properties from their Java class definitions.
    * More efficient code inside `JavaMethodInvocation`; storing a reference to
      an object is no longer necessary.
    * `EJIHelper.self()` will now return the proper "this" context for Java
      methods, so `this` can be used to reference the current object in EJI
      code.
    * Define specific indexed accessors and mutator methods for EJI types.
    * More Java errors are captured and turned into Eve errors.
    * Static methods will be picked up and added to EJI types.
    * Significant changes in how EJI objects are created and handled. If an
      EJIType class extends EveObject, it is handled slightly differently
      than a POJO class. This is mostly internal.
* Property collections are now a custom type since `dict` is gone, and are
  sealed (cannot have values added, removed, or modified).
* `scope` blocks now isolate scope. They will not search for values defined
  outside the scope. This only affects `scope(private)` currently.
* `onClone` event removed in anticipation of a better event-handling mechanism.
* Variable argument list are now a "list-like" object, rather than an object of
  type `list`.
* Ability to define a type name with object literals was removed because of the
  new `typedef` statement.
* Object literals will now report their type as "object", rather than
  "object_literal".
* `import` function: removed ability to import Java classes, because it didn't
  really line up with what importing is supposed to do.
* Switched from reflections to annovention annotation scanning library. This
  scans the classpath at startup (correctly, even!) and loads any EJI types
  automatically. Now, only `typedef extern` is required!
* Identifiers discovered to be part of a closure scope are now properly assigned
  to the main fields map instead of the temporary fields map.

0.3.5:

* `typedef` statement.
* Significant rework of how built-in types and standard library is loaded.
* Much smoother support for creating "native code" through the use of annotations.
* Rewrote native namespaces (now known as modules) using new annotations.
* Renamed `java` namespace to `eji` since it conflicted with the `java` built-in type.
* Significant enhancements to the import function:
    * EJI import standard namespace by namespace name (e.g. `import("eji")`)
    * EJI import type by package:type (e.g. `import("com.mycompany:mynamespace")`)
    * EJI import type namespace by class name (e.g. `import("com.mycompany.MyNamespace")`)
* Completely removed namespaces in favor of `typedef`:
    * All "namespaces" are now actually types (that cannot be created).
    * `::` operator repurposed to do property resolution on types.
    * Invocation of namespace functions basically remain unchanged (e.g. "eji::import" still works).
    * Namespaces are now more properly known as module types.
    * Every script is executed in the global scope.
* Scope blocks:
    * Added `scope(private)` and `scope(global)` to force scope changes.
    * `scope(private)` isolates code inside it from the outside world.
    * `scope(global)` allows block to resolve variables in the global scope.

0.3.4:

* Basic REPL.
* Reference equality operators === and !==.
* "Equals Expressions": +=, -=, etc.
* Unix install task: will install to /usr/local/lib/eve/. No task for Windows yet.

0.3.3:

* Fixed null pointer exception related to auto deep cloning with closures.

0.3.2:

* Put release notes in the readme file!
* Added automatic deep cloning for lists and dicts.
* Changed `def` and `delegate` syntax to be less obtuse.
* Added `null` value and variable initialization without assignment.

0.3.1:

* Bug fixes of some sort.

0.3.0:

* Large update that completely changes how to create objects.

Big things left to do:

* Object family support (for pushing changes to many objects)
* Ahead of Time "compilation"
* A standard library

Things to consider:

* Multithreading
