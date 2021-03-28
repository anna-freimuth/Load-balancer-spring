package anna.demo;

import anna.demo.server.ServerData;
import anna.demo.server.ServerSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Service
public class UdpBalancerListener {
    @Value("${PACKET_SIZE}") private int PACKET_SIZE;
    ServerSource serverSource;
    @Value("${udp.balancer.port}") int udpFromBalancerPort;

    public UdpBalancerListener(ServerSource serverSource) {
        this.serverSource = serverSource;
    }

    @Async
    public void listen(){
        DatagramSocket serverUdpSocket;
        try {
            serverUdpSocket = new DatagramSocket(udpFromBalancerPort);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        byte[] dataIn = new byte[PACKET_SIZE];
        DatagramPacket packetIn = new DatagramPacket(dataIn, PACKET_SIZE);

        try {
            while (true) {
                serverUdpSocket.receive(packetIn);
                String line = new String(dataIn, 0, packetIn.getLength());
                handleDataFromBalancer(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void handleDataFromBalancer(String line) {
        String[] parts = line.split(":");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);

        ServerData data = new ServerData(host, port);
        serverSource.update(data);
    }
}
