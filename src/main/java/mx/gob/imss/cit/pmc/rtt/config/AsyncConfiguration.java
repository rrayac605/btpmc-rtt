package mx.gob.imss.cit.pmc.rtt.config;

import java.util.concurrent.Executor;
import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean(name = "taskExecutorController")
    public Executor taskExecutorController() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(RttBatchConstants.POOL_SIZE);
        taskExecutor.setMaxPoolSize(RttBatchConstants.POOL_SIZE);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
