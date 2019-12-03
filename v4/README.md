# Switching Connections between Cassandra or Apollo with the Java DataStax Driver for Apache Cassandra 4.x
This application shows how to use configure a Java application to connect to Cassandra or an Apollo database at runtime using environment variables.

Contributors: [Dave Bechberger](https://github.com/bechbd) 

## Objectives
* To demonstrate how to specify at runtime between a Cassandra client configuration and an Apollo configuration for the same application.

## Project Layout
* [App.java](/v4/src/main/java/com/datastax/examples/App.java) - The main application file which contains all the logic to switch between the configurations
* [application.apollo.conf](/v4/src/main/resources/application.apollo.conf) - The configuration file for Apollo
* [application.cassandra.conf](/v4/src/main/resources/application.cassandra.conf)  - The configuration file for Cassandra

## How this Sample Works
This sample uses environment variables to specify the configuration parameters and whether to use a Cassandra configuration or an Apollo configuration.  
All the logic to switch between the configurations occurs in the `getClientConfiguration` method.  
* If you specify the `USEAPOLLO` environment variable and it is `true` then the [application.apollo.conf](/v4/src/main/resources/application.apollo.conf) 
is loaded from the `/resources` directory via the classpath.
* If you so not specify the `USEAPOLLO` environment variable or it is `false` then the [application.cassandra.conf](/v4/src/main/resources/application.cassandra.conf) 
is loaded from the `/resources` directory via the classpath.

While these criteria added to the configuration are commonly used configuration parameters you are able to specify any additional ones in the code. 

Additionally while this example uses environment variables to control which configuration is selected this could also be done via the use of configuration files or command line parameters.

For clarity this sample does not contain any of the normal error handling process you would want to wrap around connecting to a cluster to handle likely errors that would occur.

## Setup and Running

### Prerequisites
* Java 8
* A Cassandra cluster or an Apollo database to connect to with the appropriate connection information

### Running

To connect to an Apollo database you first need to download the secure connect bundle following the instructions found [here](https://docs.datastax.com/en/landing_page/doc/landing_page/cloud.html).
This first step in the process is to build and package the application.  This can be done using the following command:

`mvn package`

This will compile the code and package it as a fat JAR file (located in `target/switching-connection-configurations-java-driver-oss-v3-1.0-SNAPSHOT-jar-with-dependencies.jar`) 
which contains all the dependencies needed to run the application.

Once you have compiled the connection information and built the JAR you can run this to connect to Apollo by using the command below, with the appropriate configuration added:

`USEAPOLLO=true java -jar ./target/switching-configurations-v4-1.0-SNAPSHOT-jar-with-dependencies.jar `

If you would like to connect to a Cassandra cluster use the command below, with the appropriate configuration added:

`java -jar ./target/switching-configurations-v4-1.0-SNAPSHOT-jar-with-dependencies.jar 
`

Once you run this against either option you will get output that specifies the number of hosts and their IP addresses printed to the console:

```Connected to cluster with 3 host(s) /XX.XX.XX.XX:9042,/XX.XX.XX.XX:9042,/XX.XX.XX.XX:9042```
