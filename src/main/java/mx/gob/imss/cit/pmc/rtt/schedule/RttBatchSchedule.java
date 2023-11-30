package mx.gob.imss.cit.pmc.rtt.schedule;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.dto.RttHeaderDTO;
import mx.gob.imss.cit.pmc.rtt.service.RestartService;
import mx.gob.imss.cit.pmc.rtt.service.RttDetailService;
import mx.gob.imss.cit.pmc.rtt.service.RttHeaderService;
import mx.gob.imss.cit.pmc.rtt.utils.FutureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RttBatchSchedule {

    private static final Logger logger = LoggerFactory.getLogger(RttBatchSchedule.class);

    @Autowired
    @Qualifier("jobLauncherSchedule")
    private SimpleJobLauncher jobLauncherSchedule;

    @Autowired
    @Qualifier("chargeInitialRttMovements")
    private Job rttInitialCharge;

    @Autowired
    private RttHeaderService rttHeaderService;

    @Autowired
    private RttDetailService rttDetailService;

    @Autowired
    private RestartService restartService;

    @Bean
    public SimpleJobLauncher jobLauncherSchedule(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Scheduled(cron = "${cron.expression.rttBatchCleaner}")
    public void cleanOldRegisters() {
        logger.info("Se inicia la limpieza de las tablas");
        try {
            FutureUtils.wait(rttDetailService.deleteAll(),logger,
                    "Borrando registros de la tabla MCT_PMC_DETALLE_RTT");
            FutureUtils.wait(rttHeaderService.deleteAll(), logger,
                    "Borrando registros de la tabla MCT_PMC_RTT_ENCABEZADO");
            logger.info("Se concluyo la limpieza de las tablas");
        } catch (Exception e) {
            logger.error("Ocurrio un error al limpiar las tablas ");
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.expression.rttBatchRestart}")
    public void restartService() {
    	try {
            logger.info("Se reiniciara el microservicio");
            restartService.restart();
            logger.info("Servicio reiniciado");
		} catch (Exception e) {
			logger.error("Inconveniente al reiniciar aplicativo " + e.getMessage());
		}
    }

    @Scheduled(cron = "${cron.expression.rttBatch}")
    public void startJob() {
        logger.info("Se inicia la ejecucion del proceso");
        try {
            CompletableFuture<RttHeaderDTO> futureRttHeaderDTO = rttHeaderService.create();
            FutureUtils.wait(futureRttHeaderDTO, logger, "Creando el header");
            logger.info(MessageFormat.format("Se creo el header con el id {0}",
                    futureRttHeaderDTO.get().getCveIdPmcRttEncabezado()));
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong(RttBatchConstants.HEADER_ID, futureRttHeaderDTO.get().getCveIdPmcRttEncabezado())
                    .toJobParameters();
            JobExecution execution = jobLauncherSchedule.run(rttInitialCharge, jobParameters);
            logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
                    execution.getStartTime(), execution.getEndTime()));
        } catch (Exception e) {
        	logger.error("Inconveniente en la ejecucion para registrar los datos en BDTU " + e.getMessage());
        }

    }
    
    @Scheduled(cron = "${cron.expression.rttBatchRetry}")
    public void retryJob() {
        logger.info("Se inicia la ejecucion del reintento del proceso");
        try {
            CompletableFuture<Long> futureRttHeaderDTOCount = rttHeaderService.count();
            FutureUtils.wait(futureRttHeaderDTOCount, logger, "Consulta header");      	
            if(futureRttHeaderDTOCount.get() > RttBatchConstants.ZERO) {
            	logger.info("l proceso anterior se ejecuto de forma correcta");
            	System.exit(0);
            }else {
                logger.info("El reintento inicia porque no existe informacion en la bd");
                CompletableFuture<RttHeaderDTO> futureRttHeaderDTO = rttHeaderService.create();
                FutureUtils.wait(futureRttHeaderDTO, logger, "Creando el header");
                logger.info(MessageFormat.format("Se creo el header con el id {0}",
                        futureRttHeaderDTO.get().getCveIdPmcRttEncabezado()));
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong(RttBatchConstants.HEADER_ID, futureRttHeaderDTO.get().getCveIdPmcRttEncabezado())
                        .toJobParameters();
                JobExecution execution = jobLauncherSchedule.run(rttInitialCharge, jobParameters);
                logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
                        execution.getStartTime(), execution.getEndTime()));
            }
        } catch (Exception e) {
        	logger.error("Inconveniente en la ejecucion para registrar los datos en BDTU " + e.getMessage());
        }

    }

}
