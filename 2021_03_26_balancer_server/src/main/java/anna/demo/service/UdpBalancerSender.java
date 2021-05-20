package anna.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UdpBalancerSender {
        private final String balancerHost;
        private final String serverHost;
        private final AtomicInteger connectionsCounter;
        private final int udpToBalancerPort;
        private final int serverPort;

    public UdpBalancerSender(@Value("${BALANCER_HOST}")String balancerHost,
                                 @Value("${SERVER_HOST}") String serverHost, AtomicInteger connectionsCounter,
                                 @Value("${udp.balancer.port}")int udpToBalancerPort, @Value("${selfTcpPort}") int serverPort) {
            this.balancerHost = balancerHost;
            this.serverHost = serverHost;
            this.serverPort = serverPort;
            this.connectionsCounter = connectionsCounter;
            this.udpToBalancerPort = udpToBalancerPort;
    }

        @Async
        public void run() throws IOException {

            InetAddress inetAddress = InetAddress.getByName(balancerHost);
            DatagramSocket udpSocket = new DatagramSocket();

            String line = serverHost + ":" + serverPort + ":" + connectionsCounter.toString();
            byte[] dataToSend = line.getBytes();
            DatagramPacket packetOut = new DatagramPacket(
                    dataToSend,
                    dataToSend.length,
                    inetAddress,
                    udpToBalancerPort
            );

            udpSocket.send(packetOut);
        }
    }
