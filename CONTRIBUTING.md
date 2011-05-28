Contributing
============
To contribute to Eve, you need only fork the repository on GitHub and send pull
requests. 

You will need to set up a tracking branch for the develop branch before coding:

    git branch --track develop origin/develop

The `develop` branch is where all new code goes (see development model, below).

Coding Style
------------
Please follow the coding style that is already present in the code
when submitting changes:

* The `{` character should go on the end of method declarations, if statements,
  for loops, etc. It should not go below it (as is common in .NET). 
* If editing the AST Parser or the Lexer/Parser files, follow the grouping and
  formatting conventions in those files.
* Use tabs, not spaces.

Development Model
-----------------
Please follow this development model when making changes:

* All new code should be in or merged into the **develop** branch.
* New features should be created in a local branch and then merged with 
  `merge --no-ff` into develop so the branch history is preserved in the
  network graph.
* Bug fixes should be done on the develop branch, although if you happen to
  fix a bug while making a new feature, that's fine too.

Development Environment
-----------------------
At a minimum, you will need Apache Ant and Apache Ivy to build Eve. You can
find out how to get them by reading the README. If you use an IDE, Eclipse is
the IDE recommended. To set up Eclipse for Eve development, use the following
plugins:

* IvyDE: <http://ant.apache.org/ivy/ivyde/>
* ANTLR IDE: <http://antlrv3ide.sourceforge.net/>

Note: ANTLR IDE is really only recommended for its syntax highlighting.

### Setting up Eclipse ###
You will need to set up your environment to use an Ant Builder. Disable the
Java and Script builders for the project, and add build.xml as a new Ant build.
If you import the project into the workspace from the repository, this should
all be handled automatically. If you don't have ANTLR IDE installed, you will
get an error about antlr-3.3-complete.jar missing. Ignore this error.

If you are setting up run configurations, don't forget to put the built jar on
the classpath. It should be added under the Classpath tab of the run
configuration, and must be ABOVE the project folder under user entries. Do not
DELETE the project, as that is needed for the debugger to follow the source
code.

The built jar will always be located in the dist/ directory. When making a run
configuration, you will generally want to run the eve.core.EveCore class.
