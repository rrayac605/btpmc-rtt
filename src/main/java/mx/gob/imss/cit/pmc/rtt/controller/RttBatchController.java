package mx.gob.imss.cit.pmc.rtt.controller;

import org.springframework.http.ResponseEntity;

import mx.gob.imss.cit.pmc.rtt.model.ModelVersion;

public interface RttBatchController {

    ResponseEntity<?> deleteOldRegisters();

    ResponseEntity<?> execute();

    void restart();

    ResponseEntity<?> retryExecute();
    
	ModelVersion version() throws Exception;

}
