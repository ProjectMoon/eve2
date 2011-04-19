Eve
====

An experiment to learn how to make an interpreter.<br>
Depends on ANTLR 3 (http://www.antlr.org).

Features
--------
Eve is a loosely typed, pure prototypal, pure object-oriented language. Everything is
an object, and there are no classes. Objects are generated by cloning prototypes and
existing objects. Objects have no relationship to one another; this frees the
programmer from having to deal with pointer logic.

Status
------

Very much a work in progress:

* Variable assignment works
* Variable update works
* Print statement works
* Prototype definition works
* Prototype cloning works
* Adding properties to objects works
* Basic scoping implemented
* Very basic Java interop implemented

Everything else is either broken or unimplemented.

Big things left to do:
* REPL
* Read and execute files
* import functionality
* Decimal data type
* Boolean data type
* if/else statements
* loop statements (for, while)
* Cloning objects
* Scope operator
* delete statement
* Object family support (for pushing changes to many objects)
* Ahead of Time "compilation"
* A standard library

Things to consider:
* Closures
* Multithreading
