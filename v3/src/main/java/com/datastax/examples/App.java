package com.datastax.examples;

import com.datastax.driver.core.*;
import com.datastax.driver.core.Cluster.Builder;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args)
    {
        //Get the client configuration based on the Environment variables passed in
        Session session = getClientConfiguration();

        Set<Host> hostSet = session.getCluster().getMetadata().getAllHosts();
        String hosts = hostSet.stream().map(h -> h.toString()).collect(Collectors.joining(","));
        System.out.printf("Connected to cluster with %d host(s) %s\n", hostSet.size(), hosts);

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
    private static Session getClientConfiguration() {
        Builder builder =  Cluster.builder();
        if (System.getenv("USEAPOLLO") != null && Boolean.parseBoolean(System.getenv("USEAPOLLO"))) {
            if (System.getenv("DBUSERNAME") != null &&
                    System.getenv("DBPASSWORD") != null &&
                    System.getenv("SECURECONNECTBUNDLEPATH") != null &&
                    System.getenv("KEYSPACE") != null) {
                // Change the path here to the secure connect bundle location
               builder.withCloudSecureConnectBundle(new File(System.getenv("SECURECONNECTBUNDLEPATH")));
            } else {
                throw new IllegalArgumentException("You must have the DBUSERNAME, DBPASSWORD, SECURECONNECTBUNDLEPATH, and KEYSPACE environment variables set to use Apollo as your database.");
            }
        } else {
            if (System.getenv("CONTACTPOINTS") != null) {
                builder.addContactPoint(System.getenv("CONTACTPOINTS"));
            } else {
                throw new IllegalArgumentException("You must have the CONTACTPOINTS environment variables set to use DSE/DDAC/Cassandra as your database.");
            }
        }
        //If authentication credentials were specified then use them  - This is required for Apollo
        if (System.getenv("DBUSERNAME") != null && System.getenv("DBPASSWORD") != null) {
            builder.withAuthProvider(new PlainTextAuthProvider(System.getenv("DBUSERNAME"), System.getenv("DBPASSWORD")));
        }

        //If a keyspace is specified then use it - This is required for Apollo
        if (System.getenv("KEYSPACE") != null) {
            return builder.build().connect(System.getenv("DBUSERNAME"));
        } else {
            return builder.build().connect();
        }

    }
}
