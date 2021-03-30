package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final HttpServer server;
    private final String dataDir = "data";
    private Map<String, RouteHandler> routes = new HashMap<>();

    protected Server(String host, int port) throws IOException {
        server = createServer(host, port);
        registerCommonHandlers();
    }

    private static HttpServer createServer(String host, int port) throws IOException {
        var msg = "Starting server on http://%s:%s/%n";
        System.out.printf(msg,host,port);
        var address = new InetSocketAddress(host,port);
        return HttpServer.create(address, 50);
    }


}
