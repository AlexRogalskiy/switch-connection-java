package com.datastax.examples;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.session.Session;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class App {
    public static void main(String[] args)
    {
        //Get the client configuration based on the Environment variables passed in
        Session session = getClientConfiguration();

        Map<UUID, Node> hosts =session.getMetadata().getNodes();
        StringBuilder hostsString = new StringBuilder();

        for(Map.Entry<UUID, Node> entry : hosts.entrySet()) {
            hostsString.append(entry.getValue().getEndPoint().toString());
        }
        System.out.printf("Connected to cluster with %d host(s) %s\n", hosts.size(), hostsString);

        // This step is important because it frees underlying resources (Threads, Connections, etc) and needs to be done at shutdown
        session.close();
        System.exit(0);
    }

    /**
     * This method shows how you can switch between a Cassandra or an Apollo connection at runtime.
     * In this example we used environment variables but in reality this could be done using configuration files, command line arguments, etc.
     * Additionally for a production use case we would want to add additional error handling/checking around that connection
     * process which was omitted here for simplicities sake.
     * @return A connected session object
     */
    private static CqlSession getClientConfiguration() {
        DriverConfigLoader loader;
        if (System.getenv("USEAPOLLO") != null && Boolean.parseBoolean(System.getenv("USEAPOLLO"))) {
            loader = DriverConfigLoader.fromClasspath("application.apollo.conf");
        } else {
            loader = DriverConfigLoader.fromClasspath("application.cassandra.conf");
        }

        return CqlSession.builder().withConfigLoader(loader).build();
    }
}
