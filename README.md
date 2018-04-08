# Topera's Hello World #012
## Using CLI (Command Line Interface) with SpringBoot
Example of application using Spring Boot in CLI, with an embedded MariaDB server.

This example contains:
* Example of SpringBoot used in CLI
*


How to test
* $ cd gradle-rest-server-spring
* $ gradle bootRun
* Access: http://localhost:8080/test

Tech Stack
* Spring Boot 1.5.6
* IntelliJ IDEA 2016.1.4
* Gradle 3.5.1

To see a hello world that generates a WAR file, please check https://github.com/topera/gradle-rest-server-spring-war

To take a look in other tutorials, please see https://github.com/topera/hello-world-index



1. Enter in base folder and generate jar

    $ cd exam/
    $ gradle assemble

2. Enter in lib folder and execute the jar

    $ cd build/libs/

    If you prefer to use an embedded MariaDB server:
    $ java -jar parser.jar --accesslog=../../src/test/resources/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

    If you want to use an already running MySQL server:
    $ java -jar parser.jar --accesslog=../../src/test/resources/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --jdbc=jdbc:mysql://172.17.0.2:3306/test?user=root\&password=123456



Developed with:

* IntelliJ 2017.2.6
* Java 1.8.0_161
* Linux Mate
* MySQL 5.7.2