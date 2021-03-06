/*
 * Copyright 1&1 Internet AG, https://github.com/1and1/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oneandone.troilus.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Random;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.service.CassandraDaemon;
import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;



public class EmbeddedCassandra {

    private static final String CASSANDRA_YAML_FILE = "cassandra.yaml";

    private static CassandraDaemon cassandraDaemon;

    private static int nativePort = 0;
    
    
    
    public static void start() throws IOException {
        if (nativePort == 0) {
            nativePort = prepare();
    
            cassandraDaemon = new CassandraDaemon();
            cassandraDaemon.activate();
    
            
            long maxWaiting = System.currentTimeMillis() + 10000;
            
            while (System.currentTimeMillis() < maxWaiting) {
                if (cassandraDaemon.nativeServer.isRunning()) {
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) { }
            }
            throw new RuntimeException("Embedded cassandra does not start at expected time!");
        }
    }

    
    public static void close() {
        try {
            cassandraDaemon.thriftServer.stop();
            cassandraDaemon.nativeServer.stop();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        nativePort = 0;
    }

    
    public static InetSocketAddress getNodeaddress() {
        return new InetSocketAddress("localhost", nativePort);
    }
    


    
    @SuppressWarnings("unchecked")
    protected static int prepare() throws IOException {
        
        String cassandraDirName = "target" + File.separator + "cassandra-junit-" + new Random().nextInt(1000000);
        
        
        File cassandraDir = new File(cassandraDirName);
        if (!cassandraDir.exists()) {
            boolean isCreated = cassandraDir.mkdirs();
            if (!isCreated) {
                throw new RuntimeException("could not create cassandra dir " + cassandraDir.getAbsolutePath());
            }
        }
        

        InputStream cassandraConfigurationInput = null;
        Writer cassandraConfigurationOutput = null;

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            cassandraConfigurationInput = loader.getResourceAsStream(CASSANDRA_YAML_FILE);

            Yaml yaml = new Yaml();
            Map<String, Object> cassandraConfiguration = (Map<String, Object>)yaml.load(cassandraConfigurationInput);

            int rpcPort = findUnusedLocalPort();
            if (rpcPort == -1) {
                throw new RuntimeException("Can not start embedded cassandra: no unused local port found!");
            }
            cassandraConfiguration.put("rpc_port", rpcPort);

            int storagePort = findUnusedLocalPort();
            if (storagePort == -1) {
                throw new RuntimeException("Can not start embedded cassandra: no unused local port found!");
            }
            cassandraConfiguration.put("storage_port", storagePort);

            int nativeTransportPort = findUnusedLocalPort();
            if (nativeTransportPort == -1) {
                throw new RuntimeException("Can not start embedded cassandra: no unused local port found!");
            }
            cassandraConfiguration.put("native_transport_port", nativeTransportPort);

            cassandraConfiguration.put("start_native_transport", "true");

            cassandraConfigurationOutput =
              new OutputStreamWriter(new FileOutputStream(cassandraDirName + File.separator + CASSANDRA_YAML_FILE), Charsets.UTF_8);

            yaml.dump(cassandraConfiguration, cassandraConfigurationOutput);
            
            
            
            System.setProperty("cassandra.config", new File(cassandraDirName, CASSANDRA_YAML_FILE).toURI().toString());
            System.setProperty("cassandra-foreground", "true");
        
            DatabaseDescriptor.createAllDirectories();
            
            
            return nativeTransportPort;
            
        } finally {
            Closeables.closeQuietly(cassandraConfigurationInput);
            Closeables.close(cassandraConfigurationOutput, true);
        }
    }

    private static int findUnusedLocalPort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
}
