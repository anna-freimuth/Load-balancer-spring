package anna.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

@Service
public class ConnectionTask {
    private final String gatewayHost;
    private final int gatewayPort;
    private final String message;
    private final int messagesPerConnection;

    public ConnectionTask(
            @Value("${GATEWAY_HOST}")String gatewayHost,
            @Value("${GATEWAY_TCP_PORT}")int gatewayPort,
            @Value("${MESSAGE}") String message,
            @Value("${MESSAGES_PER_CONNECTION}") int messagesPerConnection
    ) {
        this.gatewayHost = gatewayHost;
        this.gatewayPort = gatewayPort;
        this.message = message;
        this.messagesPerConnection = messagesPerConnection;

    }

    @Async
    public void connect() {
        Socket socket;
        try {
            socket = new Socket(gatewayHost, gatewayPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedReader fromGateway = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream toGateway = new PrintStream(socket.getOutputStream())
        ) {
            for (int i = 0; i < messagesPerConnection; i++) {
                toGateway.println(message);
                String response = fromGateway.readLine();
                System.out.println(response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

