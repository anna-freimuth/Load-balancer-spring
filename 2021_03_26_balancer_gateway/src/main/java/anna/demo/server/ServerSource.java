package anna.demo.server;

import org.springframework.stereotype.Component;

@Component
public class ServerSource {
    private ServerData serverData;

    public synchronized ServerData getLast() {
        return serverData;
    }

    public synchronized void update(ServerData serverData) {
        this.serverData = serverData;
    }
}
