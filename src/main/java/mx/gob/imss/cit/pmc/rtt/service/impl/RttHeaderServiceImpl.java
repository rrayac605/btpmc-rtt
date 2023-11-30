package mx.gob.imss.cit.pmc.rtt.service.impl;

import java.util.concurrent.CompletableFuture;
import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.dto.RttHeaderDTO;
import mx.gob.imss.cit.pmc.rtt.repository.RttHeaderRepository;
import mx.gob.imss.cit.pmc.rtt.service.RttHeaderService;
import mx.gob.imss.cit.pmc.rtt.utils.CycleYearUtils;
import mx.gob.imss.cit.pmc.rtt.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RttHeaderServiceImpl implements RttHeaderService {

	Logger logger = LoggerFactory.getLogger("RttHeaderServiceImpl");
	
    @Autowired
    private RttHeaderRepository rttHeaderRepository;

    @Override
    @Transactional("transactionManagerSchedule")
    @Async
    public CompletableFuture<RttHeaderDTO> create() throws Exception {
    	try {
            RttHeaderDTO rttHeaderDTO = new RttHeaderDTO();
            rttHeaderDTO.setRefFiller1(RttBatchConstants.HEADER);
            rttHeaderDTO.setCveDelegacion(RttBatchConstants.DELEGACION);
            rttHeaderDTO.setRefTsr(RttBatchConstants.REF_TSR);
            rttHeaderDTO.setRefCasui99(RttBatchConstants.REF_CASUI99);
            rttHeaderDTO.setRefFiller2(RttBatchConstants.FILLER3);
            rttHeaderDTO.setRefCiclo(CycleYearUtils.calculateAA().concat(CycleYearUtils.calculateCCC()));
            rttHeaderDTO.setRefFiller3(RttBatchConstants.FILLER73);
            rttHeaderDTO.setIndProcesado(Boolean.FALSE);
            rttHeaderDTO.setFecAlta(DateUtils.getCurrentMexicoDate());
            return CompletableFuture.completedFuture(rttHeaderRepository.saveAndFlush(rttHeaderDTO));
		} catch (Exception e) {
        	logger.error("Inconveniente al registrar encabezado");
			throw new Exception(e);
		}
    }

    @Override
    @Transactional("transactionManager")
    @Async
    public CompletableFuture<Void> deleteAll() throws Exception {
    	try {
            rttHeaderRepository.deleteAll();
            return CompletableFuture.completedFuture(null);
		} catch (Exception e) {
        	logger.error("Inconveniente al borrar datos de encabezado");
			throw new Exception(e);
		}
    }
    
    @Override
    @Transactional("transactionManager")
    @Async
    public CompletableFuture<Long> count() throws Exception {
    	try {
    		return CompletableFuture.completedFuture(rttHeaderRepository.count());
		} catch (Exception e) {
        	logger.error("Inconveniente al consultar encabezado");
			throw new Exception(e);
		}
    }

}
