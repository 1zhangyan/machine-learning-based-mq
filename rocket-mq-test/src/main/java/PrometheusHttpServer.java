import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;

public class PrometheusHttpServer {
    static HTTPServer server;
    public static void startServer() throws IOException {
        server  = new HTTPServer.Builder()
                .withPort(1234)
                .build();
    }

    public static void closeServer() {
        server.close();
    }

}
