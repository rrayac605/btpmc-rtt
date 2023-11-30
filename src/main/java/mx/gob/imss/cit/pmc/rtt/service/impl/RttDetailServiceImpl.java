package mx.gob.imss.cit.pmc.rtt.service.impl;

import java.util.concurrent.CompletableFuture;
import mx.gob.imss.cit.pmc.rtt.repository.RttDetailRepository;
import mx.gob.imss.cit.pmc.rtt.service.RttDetailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RttDetailServiceImpl implements RttDetailService {

	Logger logger = LoggerFactory.getLogger("RttDetailServiceImpl");
	
    @Autowired
    private RttDetailRepository rttDetailRepository;

    @Override
    @Transactional("transactionManager")
    @Async
    public CompletableFuture<Void> deleteAll() throws Exception {
    	try {
            rttDetailRepository.deleteAll();
            return CompletableFuture.completedFuture(null);
		} catch (Exception e) {
        	logger.error("Inconveniente al borrar datos de detalle");
			throw new Exception(e);
		}
    }

}
