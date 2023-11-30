package mx.gob.imss.cit.pmc.rtt.service;

import java.util.concurrent.CompletableFuture;

public interface RttDetailService {

    CompletableFuture<Void> deleteAll() throws Exception;

}
