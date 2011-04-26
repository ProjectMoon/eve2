Contributing
============
To contribute to Eve, you need only fork the repository on GitHub and send pull
requests. Please follow the coding style that is already present in the code
when submitting changes:

* The `{` character should go on the end of method declarations, if statements,
  for loops, etc. It should not go below it (as is common in .NET). 
* If editing the AST Parser or the Lexer/Parser files, follow the grouping and
  formatting conventions in those files.

Also, please follow this development model when making changes:

* All new code should be in or merged into the '''develop''' branch.
* New features should be created in a local branch and then merged with 
  `merge --no-ff` into develop so the branch history is preserved in the
  network graph.
* Bug fixes should be done on the develop branch, although if you happen to
  fix a bug while making a new feature, that's fine too.
