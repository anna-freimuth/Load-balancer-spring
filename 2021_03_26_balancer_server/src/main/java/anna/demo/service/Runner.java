package anna.demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
@Component
public class Runner implements ApplicationRunner {
    private final UdpBalancerSender udpBalancerSender;
    private final TcpGatewayListener tcpGatewayListener;

    public Runner(UdpBalancerSender udpBalancerSender, TcpGatewayListener tcpGatewayListener) {
        this.udpBalancerSender = udpBalancerSender;
        this.tcpGatewayListener = tcpGatewayListener;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tcpGatewayListener.run();
        udpBalancerSender.run();

    }
}
