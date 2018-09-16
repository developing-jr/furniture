# Room Furniture
Optimal placement of furniture in room.

## Build
The application is created in Java using Maven project management software. The pre-requirements of the application build are installed Java and Maven software.

To build the maven project *room-furniture* you can use
* command `mvn clean build` in root directory
* or if you familiar with IDE IntelliJ Idea, then by import of the maven project is imported build the launcher with name *Build*.

After successfully build is created in *Target* directory of maven project the shaded JAR file *room-furniture-1.0-SNAPSHOT-shaded.jar*, which contains all needed dependencies to run the application.

## Run
### Properties for run
To run application you need to define 2 system properties:
* `roomDefinition` - for room definition,
* `furnitureDefinitions` - for furniture definitions of room.

### Start run
The application can be started:
* from terminal using following command line, which contains sample system properties
````bash
java -DroomDefinition="5,6 ..###. .####. ###### ###### ...###" -DfurnitureDefinitions="A2#### B3.#.###.#." -jar furniture-1.0-SNAPSHOT-shaded.jar
````
* or if you familiar with IDE IntelliJ Idea, then by import of the maven project is imported the launcher with name \"*Build and Run Furniture Placer with Sample data*\", which by start rebuilds the application and afterwards starts the application also with following sample system properties 
`-DroomDefinition="5,6 ..###. .####. ###### ###### ...###" -DfurnitureDefinitions="A2#### B3.#.###.#."`.
