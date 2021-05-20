package anna.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TcpGatewayListener {
    int selfTcpPort;
    AtomicInteger connectionsCounter;
    ServerTask serverTask;

    public TcpGatewayListener(@Value("${selfTcpPort}") int selfTcpPort, AtomicInteger connectionsCounter, ServerTask serverTask) {
        this.selfTcpPort = selfTcpPort;
        this.connectionsCounter = connectionsCounter;
        this.serverTask = serverTask;
    }

    @Async
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(selfTcpPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        Socket socket = null;

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            connectionsCounter.incrementAndGet();

            serverTask.proxy(socket, connectionsCounter);
        }
    }
}
