package anna.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager implements ApplicationRunner {

    private final int connectionNumber;
    private final ConnectionTask connectionTask;

    public ConnectionManager(
            @Value("${CONNECTION_NUMBER}") int connectionNumber,
            ConnectionTask connectionTask
    ) {
        this.connectionNumber = connectionNumber;
        this.connectionTask = connectionTask;
    }

    @Override
    public void run(ApplicationArguments args) {

        for (int i = 0; i < this.connectionNumber; i++) {
            connectionTask.connect();
        }
    }
}
