package mx.gob.imss.cit.pmc.rtt.listener;

import mx.gob.imss.cit.pmc.rtt.dto.MovementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class MongoItemReaderListener implements ItemReadListener<MovementDTO> {

    Logger logger = LoggerFactory.getLogger(MongoItemReaderListener.class);

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(MovementDTO item) {
        logger.info(item.toString());
    }

    @Override
    public void onReadError(Exception ex) {

    }

}
