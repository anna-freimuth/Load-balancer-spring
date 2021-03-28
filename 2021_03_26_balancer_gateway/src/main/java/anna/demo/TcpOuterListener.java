package anna.demo;

import anna.demo.server.ServerSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


@Service
public class TcpOuterListener {
    ServerSource serverSource;
    int tcpOuterPort;
    ServerProxyTask serverProxyTask;

    public TcpOuterListener(
            ServerSource serverSource,
            @Value("${tcp.outer.port}") int tcpOuterPort,
            ServerProxyTask serverProxyTask
    ) {
        this.serverSource = serverSource;
        this.tcpOuterPort = tcpOuterPort;
        this.serverProxyTask = serverProxyTask;
    }

    @Async
    public void listen() throws IOException {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(tcpOuterPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Connected");

            serverProxyTask.proxy(socket);

        }
    }
}
