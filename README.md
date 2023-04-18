# Jatai

* A prototype that incorporates simple types (), bidirectional typing, and subtyping.
* No parser needed: No need to pre-process the program into a structured representation such as ASTs because the
  structure of a term is embedded in the class definition thanks to the OOP design of Java. Declared terms are
  guaranteed to be syntactically correct but may not be semantically correct.
* Currently, Jatai does not allow duplicate names or support auto-conversion of duplicate names.