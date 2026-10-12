# GlobalPop

GlobalPop is a Java-based population reporting system developed for the DevOps Project.

## Project Information

- Project: GlobalPop
- Language: Java
- JDK: Eclipse Temurin 25
- Build Tool: Maven
- Database: MySQL

Coding Guidelines
============
1. Use private (private သုံးလို ရနိုင်သမျှနေရာ private သုံးပါရန်)

2. Add comments for codes (Class level comments, Methods level comments, etc)

3. Use .hasNextLine() for issues that require input

4. Push only your feature file. Main class is just for local testing.

5. Follow "model" in model folder.

6. In Main class: call Feature class and run favored way.
   Example
   _
   Feature01 feature01 = new Feature01();

        feature01.generateAndDisplayReport();