package anna.demo.service;

import anna.demo.server.IServerMap;
import anna.demo.server.ServerData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Service
public class UdpGatewaySender {
    private final String host;
    private final IServerMap serverMap;
    private final int udpToGatewayPort;
    private final int periodMillis;


    public UdpGatewaySender(@Value("${UDP_GATEWAY_HOST}") String host, IServerMap serverMap, @Value("${udp.gateway.port}") int udpToGatewayPort, @Value("${periodMillis}") int periodMillis) {
        this.host = host;
        this.serverMap = serverMap;
        this.udpToGatewayPort = udpToGatewayPort;
        this.periodMillis = periodMillis;
    }

    @Async
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            DatagramSocket udpSocket = new DatagramSocket();
            while (true) {
                try {
                    Thread.sleep(periodMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ServerData serverData = serverMap.getBest();
                if (serverData == null) continue;
                String best = serverData.toString();
                byte[] sendBest = best.getBytes();
                DatagramPacket packetOut = new DatagramPacket(
                        sendBest,
                        sendBest.length,
                        inetAddress,
                        udpToGatewayPort
                );

                udpSocket.send(packetOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
