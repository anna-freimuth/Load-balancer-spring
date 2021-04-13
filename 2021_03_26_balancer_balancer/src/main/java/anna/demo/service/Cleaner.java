package anna.demo.service;

import anna.demo.server.IServerMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Cleaner {
    private IServerMap serverMap;
    private int periodMillis;


    public Cleaner(IServerMap serverMap, @Value("${periodMillisCleaner}")int periodMillis) {
        this.serverMap = serverMap;
        this.periodMillis = periodMillis;
    }

    @Async
    public void run() {
        while (true) {
            try {
                Thread.sleep(periodMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            serverMap.removeUnused(periodMillis);
        }
    }
}
