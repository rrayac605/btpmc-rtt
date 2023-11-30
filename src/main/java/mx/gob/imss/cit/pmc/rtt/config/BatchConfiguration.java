package mx.gob.imss.cit.pmc.rtt.config;

import lombok.SneakyThrows;
import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.dto.ChangeDTO;
import mx.gob.imss.cit.pmc.rtt.dto.MovementDTO;
import mx.gob.imss.cit.pmc.rtt.dto.RttDetailDTO;
import mx.gob.imss.cit.pmc.rtt.processor.RttDetailChangeProcessor;
import mx.gob.imss.cit.pmc.rtt.processor.RttDetailProcessor;
import mx.gob.imss.cit.pmc.rtt.utils.ReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "mx.gob.imss.cit.pmc.rtt" })
public class BatchConfiguration extends DefaultBatchConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private DataSource dataSource;

    @Bean
    @NonNull
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @SneakyThrows
    @NonNull
    @Bean
    public JobRepository getJobRepository() {
        return Objects.requireNonNull(new MapJobRepositoryFactoryBean(getTransactionManager()).getObject());
    }

    @Bean(name = "chargeInitialRttMovements")
    public Job chargeInitialRttMovements() throws Exception {
        return jobBuilderFactory.get("chargeInitialRttMovements")
                .incrementer(new RunIdIncrementer())
                .start(stepWriteRttDetail())
                .next(stepWriteRttChangeMN())
                .build();
    }

    @Bean
    public Step stepWriteRttDetail() throws Exception {
        return stepBuilderFactory.get("stepWriteRttDetail")
                .<MovementDTO, RttDetailDTO>chunk(RttBatchConstants.CHUNK_SIZE)
                .reader(movementReader())
                .processor(detailProcessor())
                .writer(detailWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepWriteRttChangeMN() throws Exception {
        return stepBuilderFactory.get("stepWriteRttChangeMN")
                .<ChangeDTO, RttDetailDTO>chunk(RttBatchConstants.CHUNK_SIZE)
                .reader(changeReader())
                .processor(detailChangeProcessor())
                .writer(detailWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<MovementDTO> movementReader() throws Exception {
        logger.info("--------------------------Query de movimientos correctos-------------------------");
        logger.info(ReaderUtils.buildMovementsQueryInitialCharge().toString());
        logger.info("---------------------------------------------------------------------------------");
        try {
            return new MongoItemReaderBuilder<MovementDTO>()
                    .name("movementReader")
                    .template(mongoOperations)
                    .targetType(MovementDTO.class)
                    .sorts(RttBatchConstants.READER_SORTER)
                    .query(ReaderUtils.buildMovementsQueryInitialCharge())
                    .pageSize(RttBatchConstants.CHUNK_SIZE)
                    .build();
		} catch (Exception e) {
        	logger.error("Inconveniente al consultar la informacion en la bd de mongo - movimientos");
			throw new Exception(e);
		}
    }

    @Bean
    public MongoItemReader<ChangeDTO> changeReader() throws Exception {
        logger.info("--------------------------Query de altas manuales-------------------------");
        logger.info(ReaderUtils.buildChangeMN().toString());
        logger.info("--------------------------------------------------------------------------");
        try {
            return new MongoItemReaderBuilder<ChangeDTO>()
                    .name("changeReader")
                    .template(mongoOperations)
                    .targetType(ChangeDTO.class)
                    .sorts(RttBatchConstants.READER_SORTER)
                    .query(ReaderUtils.buildChangeMN())
                    .pageSize(RttBatchConstants.CHUNK_SIZE)
                    .build();
		} catch (Exception e) {
        	logger.error("Inconveniente al consultar la informacion en la bd de mongo - cambios");
			throw new Exception(e);
		}
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(RttBatchConstants.POOL_SIZE);
        taskExecutor.setMaxPoolSize(RttBatchConstants.POOL_SIZE);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public JdbcBatchItemWriter<RttDetailDTO> detailWriter() throws Exception {
    	try {
            return new JdbcBatchItemWriterBuilder<RttDetailDTO>()
                    .dataSource(dataSource)
                    .sql(RttBatchConstants.DETAIL_WRITER_SQL_STATEMENT)
                    .beanMapped()
                    .build();
		} catch (Exception e) {
        	logger.error("Inconveniente al guardar informacion en bdtu");
			throw new Exception(e);
		}
    }

    @Bean
    public RttDetailProcessor detailProcessor() {
        return new RttDetailProcessor();
    }

    @Bean
    public RttDetailChangeProcessor detailChangeProcessor() {
        return new RttDetailChangeProcessor();
    }

}
