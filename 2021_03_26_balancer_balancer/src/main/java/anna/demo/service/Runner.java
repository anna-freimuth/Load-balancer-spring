package anna.demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
    private final UdpGatewaySender udpGatewaySender;
    private final UdpServerListener udpServerListener;
    private final Cleaner cleaner;

    public Runner(UdpGatewaySender udpGatewaySender, UdpServerListener udpServerListener,Cleaner cleaner) {
        this.udpGatewaySender = udpGatewaySender;
        this.udpServerListener = udpServerListener;
        this.cleaner = cleaner;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        udpServerListener.run();
        udpGatewaySender.run();
        cleaner.run();
    }
}
