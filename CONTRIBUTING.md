Contributing
============
To contribute to Eve, you need only fork the repository on GitHub and send pull
requests. Please follow the coding style that is already present in the code
when submitting changes:

* The `{` character should go on the end of method declarations, if statements,
  for loops, etc. It should not go below it (as is common in .NET). 
* If editing the AST Parser or the Lexer/Parser files, follow the grouping and
  formatting conventions in those files.

Example Contribution: New Data Type
-----------------------------------
As an example of making a contribution, here is how to make a new "builtin"
data type that uses a simple var x = <myType>; declaration.

1. We will call the new data type MyType.
2. Add the new type as a field in EveObject: `private MyType myTypeValue;`
3. Add getter and setter for MyType, following the type-checking conventions.
4. Add MyType to the toString and getTypeName methods.
5. Update the clonePrimitives method to copy your type.
6. Make a convenience constructor for MyType.
7. Update the equals() method to allow boolean comparison of your type.
5. Update WrappedPrimitiveExpression to take MyType.
6. Update any of the other expressions (plus, minus, etc) to expose your type
   to operators.
7. Update Eve.g to recognize a declaration of MyType.
8. Update ASTParser.g to put MyType into a WrappedPrimitiveExpression.