package anna.demo.service;

import anna.demo.server.IServerMap;
import anna.demo.server.ServerData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


@Service
public class UdpServerListener {
    @Value("${PACKET_SIZE}")
    private int PACKET_SIZE;
    IServerMap serverMap;
    int udpFromServerPort;

    public UdpServerListener(IServerMap serverMap, @Value("${udp.server.port}") int udpFromServerPort) {
        this.serverMap = serverMap;
        this.udpFromServerPort = udpFromServerPort;
    }

    @Async
    public void run() throws IOException {
        DatagramSocket serverUdpSocket;
        serverUdpSocket = new DatagramSocket(udpFromServerPort);


        byte[] dataIn = new byte[PACKET_SIZE];
        DatagramPacket packetIn = new DatagramPacket(dataIn, PACKET_SIZE);


        while (true) {
            serverUdpSocket.receive(packetIn);
            String line = new String(dataIn, 0, packetIn.getLength());
            handleDataFromServer(line);
        }
    }

    void handleDataFromServer(String line) {

        // data is assume to be 127.0.0.1:80:5 (host:port:load)
        // Removes line feed character \n for linux, \r for macs, and \r\n for windows senders
        // required for testing with netcat
        line = line.replaceAll("\n", "").replaceAll("\r", "");

        System.out.println(line); //Debug

        String[] parts = line.split(":");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);
        int load = Integer.parseInt(parts[2]);

        ServerData data = new ServerData(host, port, load);
        serverMap.update(data);
    }
}
