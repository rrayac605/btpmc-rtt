package mx.gob.imss.cit.pmc.rtt.service;

import java.util.concurrent.CompletableFuture;
import mx.gob.imss.cit.pmc.rtt.dto.RttHeaderDTO;

public interface RttHeaderService {

    CompletableFuture<RttHeaderDTO> create() throws Exception;

    CompletableFuture<Void> deleteAll() throws Exception;
    
    CompletableFuture<Long> count() throws Exception;

}
