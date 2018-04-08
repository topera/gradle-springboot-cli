# Topera's Hello World #012
## Using CLI (Command Line Interface) with SpringBoot
Example of application using Spring Boot in CLI, with an embedded MariaDB server.

This example contains:
* Example of SpringBoot used in CLI
* Fat-jar creation (jar with all dependencies)
* Unit tests with Groovy/Spock
* Usage of Apache Commons CLI (https://commons.apache.org/proper/commons-cli)
* Connection to an external MySQL server
* Connection to an embedded MariaDB server

How to test
* $ gradle assemble
* $ cd build/libs/

* If you prefer to use an embedded MariaDB server:
** $ java -jar parser.jar --accesslog=../../src/test/resources/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

* If you want to use an already running MySQL server:
** $ java -jar parser.jar --accesslog=../../src/test/resources/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --jdbc=jdbc:mysql://172.17.0.2:3306/test?user=root\&password=123456


Tech Stack
* Gradle 4.3
* IntelliJ 2017.2.6
* Java 1.8.0_161
* Linux Mate
* MySQL 5.7.2

To take a look in other tutorials, please see https://github.com/topera/hello-world-index

