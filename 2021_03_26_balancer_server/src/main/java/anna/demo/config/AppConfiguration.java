package anna.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@EnableAsync
@Configuration
public class AppConfiguration {
    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }

    @Bean
    public Socket socket(){
        return new Socket();
    }
    @Bean
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(17);
        executor.setMaxPoolSize(17);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(5);
        executor.setThreadNamePrefix("Server-");
        executor.initialize();
        return executor;
    }
}
