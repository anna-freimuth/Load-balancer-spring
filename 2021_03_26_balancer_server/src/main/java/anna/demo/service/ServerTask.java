package anna.demo.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ServerTask {

    @Async
    public void proxy(Socket socket, AtomicInteger connectionsCounter) {
        try {

            PrintStream outputToClient = new PrintStream(socket.getOutputStream());;
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String lineFromClient;
            try {
                while ((lineFromClient = inputFromClient.readLine()) != null) {
                    String response = "Handled by server " + lineFromClient;
                    outputToClient.println(response);
                }
            } catch (SocketException e) {
                /* expected disconnect */
            }finally {
                connectionsCounter.decrementAndGet();
                inputFromClient.close();
                outputToClient.close();
            }
        } catch (IOException ioException) {
            /* do nothing */
        }
    }
}
