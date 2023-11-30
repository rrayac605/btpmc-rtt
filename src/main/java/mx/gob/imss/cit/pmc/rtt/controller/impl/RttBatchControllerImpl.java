package mx.gob.imss.cit.pmc.rtt.controller.impl;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.controller.RttBatchController;
import mx.gob.imss.cit.pmc.rtt.dto.RttHeaderDTO;
import mx.gob.imss.cit.pmc.rtt.model.ErrorResponse;
import mx.gob.imss.cit.pmc.rtt.model.ModelVersion;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/msBatchRtt")
public class RttBatchControllerImpl implements RttBatchController {

    Logger logger = LoggerFactory.getLogger("RttBatchControllerImpl");

    @Autowired
    RttHeaderService rttHeaderService;

    @Autowired
    RttDetailService rttDetailService;

    @Autowired
    RestartService restartService;

    @Autowired
    @Qualifier("jobLauncherController")
    private SimpleJobLauncher jobLauncherController;

    @Autowired
    @Qualifier("chargeInitialRttMovements")
    private Job rtt;

    @Bean
    public SimpleJobLauncher jobLauncherController(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Override
    @PostMapping(value = "/execute", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> execute() {
        logger.info("Se inicia la ejecucion del proceso");
        ResponseEntity<?> response;
        try {
            CompletableFuture<RttHeaderDTO> futureRttHeaderDTO = rttHeaderService.create();
            FutureUtils.wait(futureRttHeaderDTO, logger, "Creando el header");
            logger.info(MessageFormat.format("Se creo el header con el id {0}",
                    futureRttHeaderDTO.get().getCveIdPmcRttEncabezado()));
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong(RttBatchConstants.HEADER_ID, futureRttHeaderDTO.get().getCveIdPmcRttEncabezado())
                    .toJobParameters();
            JobExecution execution = jobLauncherController.run(rtt, jobParameters);
            response = new ResponseEntity<>(MessageFormat.format("El resultado de la ejecucion es: {0}",
                    execution.getStatus()), HttpStatus.OK);
            logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
                    execution.getStartTime(), execution.getEndTime()));
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("Error al ejecutar la tarea " + e.getMessage());
            response = new ResponseEntity<>("Error al ejecutar la tarea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @PostMapping("/restart")
    public void restart() {
    	try {
            logger.info("Se reiniciara el microservicio");
            restartService.restart();
            logger.info("Servicio reiniciado");
		} catch (Exception e) {
			logger.error("Inconveniente al reiniciar aplicativo " + e.getMessage());
		}
    }

    @Override
    @PostMapping("/cleanRegisters")
    public ResponseEntity<?> deleteOldRegisters() {
        ResponseEntity<?> response;
        try {
            FutureUtils.wait(rttDetailService.deleteAll(),logger,
                    "Borrando registros de la tabla MCT_PMC_DETALLE_RTT");
            FutureUtils.wait(rttHeaderService.deleteAll(), logger,
                    "Borrando registros de la tabla MCT_PMC_RTT_ENCABEZADO");
            logger.info("Se concluyo la limpieza de las tablas");
            response = new ResponseEntity<>("Se eliminaron correctamente los registros", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Inconveniente al eliminar los registros de bdtu " + e.getMessage());
            response = new ResponseEntity<>("Error al eliminar los registros", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    
    @Override
    @PostMapping(value = "/retryExecute", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retryExecute() {
        logger.info("Se inicia la ejecucion del reintento del proceso");
        ResponseEntity<?> response;
        try {

            CompletableFuture<Long> futureRttHeaderDTOCount = rttHeaderService.count();
            FutureUtils.wait(futureRttHeaderDTOCount, logger, "Consulta header");
            if(futureRttHeaderDTOCount.get() > RttBatchConstants.ZERO) {
            	logger.info("El proceso anterior se ejecuto de forma correcta");
                response = new ResponseEntity<>("El proceso anterior se ejecuto de forma correcta", HttpStatus.OK);
                return response;
            }else {
                logger.info("El reintento inicia porque no existe informacion en la bd");
                CompletableFuture<RttHeaderDTO> futureRttHeaderDTO = rttHeaderService.create();
                FutureUtils.wait(futureRttHeaderDTO, logger, "Creando el header");
                logger.info(MessageFormat.format("Se creo el header con el id {0}",
                        futureRttHeaderDTO.get().getCveIdPmcRttEncabezado()));
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong(RttBatchConstants.HEADER_ID, futureRttHeaderDTO.get().getCveIdPmcRttEncabezado())
                        .toJobParameters();
                JobExecution execution = jobLauncherController.run(rtt, jobParameters);
                response = new ResponseEntity<>(MessageFormat.format("El resultado de la ejecucion es: {0}",
                        execution.getStatus()), HttpStatus.OK);
                logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
                        execution.getStartTime(), execution.getEndTime()));
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("Error al ejecutar la tarea " + e.getMessage());
            response = new ResponseEntity<>("Error al ejecutar la tarea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

	private final static String version_service = "btpmc-rtt-1.0.1";
	
	private final static String folio_service = "Inconveniente-RTT";
	
	private final static String nota_service = "Tablas intermedias vacias de RTT (inconveniente conexion BDTU)";
	
	@ApiOperation(value = "version", nickname = "version", notes = "version", response = Object.class, responseContainer = "binary", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = ResponseEntity.class, responseContainer = "byte"),
			@ApiResponse(code = 204, message = "Sin resultados", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(value = "/version")
	@Override
	public ModelVersion version() throws Exception {
		return new ModelVersion(version_service, folio_service, nota_service);
	}
	
}
