package anna.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ListenerStarter implements ApplicationRunner {

    private final UdpBalancerListener udpBalanceListener;
    private final TcpOuterListener tcpOuterListener;

    public ListenerStarter(TcpOuterListener tcpOuterListener, UdpBalancerListener udpBalancerListener) {
        this.tcpOuterListener = tcpOuterListener;
        this.udpBalanceListener = udpBalancerListener;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        udpBalanceListener.listen();
        tcpOuterListener.listen();
    }
}
