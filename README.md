# Scheduler

Authors: Jonathan Su, Alex Liu

# Setup

## Backend

#### Prerequisites

1. Maven must be installed on command line. You can use the following command to install it:
```
brew install maven
```
2. PostgreSQL must be installed and running
3. A database called "meeting-scheduler" must be present in PostgreSQL
4. The following line in application.properties (java/meeting-scheduler/src/main/resources) must be modified
```
spring.datasource.username=<your_postgres_account_name>
```

#### Installation

The following steps are needed in order to get the backend running on localhost:8080

1. Open Terminal and navigate to folder with pom.xml
```
cd Scheduler/java/meeting-scheduler
```
2. Run following command. You can skip tests by adding the argument -DskipTests=true
```
mvn package
```
4. Navigate to 'target' folder
```
cd target
```
5. Execute JAR
```
java -jar meeting-scheduler-0.0.1-SNAPSHOT.jar
```

## Running
```
brew services start postgresql
```

#### Testing APIs

All the APIs can be tested through the following link:
```
http://localhost:8080/swagger-ui.html#/
```

#### Miscellaneous

If importing the project into Eclipse, make sure to import it as a Maven project

# Package Dependencies

| Package | Installation |
|:-------:|:------------:|
| postgres | `brew install postgres` |

# Uses:
Boostrap 3.0
