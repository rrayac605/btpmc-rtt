package mx.gob.imss.cit.pmc.rtt.listener;

import java.text.MessageFormat;
import mx.gob.imss.cit.pmc.rtt.dto.RttDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class RttDetailReaderListener implements ItemReadListener<RttDetailDTO> {

    Logger logger = LoggerFactory.getLogger(RttDetailReaderListener.class);

    @Override
    public void beforeRead() { }

    @Override
    public void afterRead(RttDetailDTO item) {
        logger.info(MessageFormat.format("Se eliminara el registro con el id {0} y el object id {1}",
                item.getId(), item.getMongoObjectId()));
    }

    @Override
    public void onReadError(Exception ex) { }

}
