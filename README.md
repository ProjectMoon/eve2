Eve
====

An experiment to learn how to make an interpreter.<br>
Eve is a simple interpreted language that targets the JVM.

Features
--------
Eve is a loosely typed, pure prototypal, object-oriented language:

* Everything is an object, and there are no classes.
* Objects are generated by cloning prototypes and existing objects.
* Objects have no delegation relationship with one another (as in 
  JavaScript); this reduces confusion and mistakes when dealing with
  prototypes.
* Eve runs on the Java Virtual Machine, so integration with Java code is
  possible.
  
For more information about Prototypal OOP programming, see the Wikipedia
article:<br>
<http://en.wikipedia.org/wiki/Prototype-based_programming>

Building
========
Eve uses Apache Ant for building and Apache Ivy for resolving libraries
required for the project. Make sure to install those first. Your distro
may have them in its repositories. For example, to install Ant and Ivy
on Ubuntu 10.10:

    sudo apt-get install ant ivy
    
If necessary, install Ant and Ivy manually. They can be found at:
http://ant.apache.org/
http://ant.apache.org/ivy/

Once having installed Ant and Ivy and putting them on your classpath,
run the build script by typing "ant" in the root of the repository.
Dependencies will be fetched and added to the classpath automatically.

The build process will produce an executable jar `eve.jar` in the `dist`
directory. Execute `java -jar dist/eve.jar` to start the interpreter.

Troubleshooting
---------------
### Building Issues ###
Before running Ant, make sure Ivy is on your classpath. If Ivy is not on
the classpath, the dependecy resolution task will fail. For example, to
get Ivy on the classpath in Ubuntu 10.10:

`export CLASSPATH=/usr/share/java/ivy.jar:$CLASSPATH`

### Interpreter Can't Find ANTLR Runtime ###
The executable jar has the ANTLR runtime in its classpath. Make sure a
`lib/` directory exists in the location where eve.jar is, and make sure
`antlr-runtime-3.3.jar` is in that directory. If those are present, eve
should be able to be run from anywhere.

Status
======

Very much a work in progress. Completed things:

* Read and execute script files
* Debug mode
* int, double, string, and function data types.
* Variable assignment works
* Variable update works
* Print statement works
* Prototype definition works
* Prototype cloning works
* Adding properties to objects works
* Basic scoping implemented
* Cloning objects
* Very basic Java interop implemented (only internally)
* function invocation works

Big things left to do:

* REPL
* import statment
* Boolean data type
* if/else statements
* loop statements (for, while)
* Scope operator
* delete statement
* Object family support (for pushing changes to many objects)
* Ahead of Time "compilation"
* A standard library

Things to consider:

* Closures
* Multithreading
