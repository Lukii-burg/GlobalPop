# GlobalPop

GlobalPop is a Java-based population reporting system developed for the DevOps Project.

## Project Information

- Project: GlobalPop
- Language: Java
- JDK: Eclipse Temurin 25
- Build Tool: Maven
- Database: MySQL

Group, Issues and Required reports
A — World and continent cities
#7 , #8 , #12 , #13
All cities in world/continent; top N cities in world/continent

B — Region, country and district cities
#9 , #10 , #11 , #14 , #15 , #16
All cities and top N cities by region, country and district

C — Capital cities
#17 , #18 , #19 , #20 , #21 , #22
All capital cities and top N capital cities by world, continent and region

src/main/java/com/globalpop/
├── Main.java
├── database/
│   ├── DatabaseConnection.java
│   └── CityQueryRepository.java
├── model/
│   ├── Country.java
│   └── City.java                     
├── sprint1_features/
│   └── existing Sprint 1 classes
├── sprint2_worldandcontinentcities/
│   ├── Feature7.java
│   ├── Feature8.java
│   ├── Feature12.java
│   └── Feature13.java
├── sprint2_regioncountrydistrictcities/
│   ├── Feature9.java
│   ├── Feature10.java
│   ├── Feature11.java
│   ├── Feature14.java
│   ├── Feature15.java
│   └── Feature16.java
└── sprint2_capitalcities/
├── Feature17.java
├── Feature18.java
├── Feature19.java
├── Feature20.java
├── Feature21.java
└── Feature22.java

Coding Guidelines
============
1. Use private (private သုံးလို ရနိုင်သမျှနေရာ private သုံးပါရန်)

2. Add comments for codes (Class level comments, Methods level comments, etc)

3. Use .hasNextLine() for issues that require input

4. Push only your feature file. Main class is just for local testing.

5. Follow "model" in model folder.

6. In Main class: call Feature class and run favored way.
   Eample
   _
   Feature01 feature01 = new Feature01();

        feature01.generateAndDisplayReport();