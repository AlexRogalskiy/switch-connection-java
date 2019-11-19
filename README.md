# Switch Connections between Apache Cassandra™ and Apollo databases
This application shows how to use both the latest version 4.x and the legacy version 3.x of the [Java DataStax Driver](https://docs.datastax.com/en/developer/java-driver/latest) to connect to an on-prem Cassandra database or a Apollo database in the cloud at runtime using environment variables.

Contributor(s): [Dave Bechberger](https://github.com/bechbd) 

## Objectives
* Shows the differences between a Cassandra connection configuration and an Apollo connection configuration in a single app.
* Provide a demonstration of how to configure the database connection at runtime.
* See the documentation for more details about the Apollo connection configuration for the Java Driver ( [4.x docs](https://docs.datastax.com/en/developer/java-driver/latest/manual/cloud/) | [3.x docs](https://docs.datastax.com/en/developer/java-driver/3.8/manual/cloud/) ).

## Project Layout
* [v3](/v3) - The application that shows how to accomplish this task with the legacy version 3.x of the Java DataStax Driver
* [v4](/v4) - The application that shows how to accomplish this task with the latest version 4.x of the Java DataStax Driver

## How this Works
Refer to the READMEs in the appropriate sub folder above to see the details of how to accomplish this with your desired java driver version.
