package mx.gob.imss.cit.pmc.rtt.service.impl;

import mx.gob.imss.cit.pmc.rtt.service.RestartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
public class RestartServiceImpl implements RestartService {

    @Autowired
    private RestartEndpoint restartEndpoint;

    @Override
    public void restart() {
        restartEndpoint.restart();
    }

}
