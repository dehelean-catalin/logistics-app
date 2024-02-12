package app.logisctics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class AsyncConfig {

    @Bean
    public ExecutorService executor(){
        return new ThreadPoolExecutor(
                4,
                4,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean
    public ExecutorService smallExecutorService(){
        return new ThreadPoolExecutor(
                2,
                2,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
