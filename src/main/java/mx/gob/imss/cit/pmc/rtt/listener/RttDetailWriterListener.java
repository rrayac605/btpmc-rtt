package mx.gob.imss.cit.pmc.rtt.listener;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import mx.gob.imss.cit.pmc.rtt.dto.RttDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

public class RttDetailWriterListener implements ItemWriteListener<RttDetailDTO> {

    Logger logger = LoggerFactory.getLogger(RttDetailWriterListener.class);

    @Override
    public void beforeWrite(List<? extends RttDetailDTO> items) {
        logger.info("Se inicia el borrado de registros");
    }

    @Override
    public void afterWrite(List<? extends RttDetailDTO> items) {
        logger.info(MessageFormat.format("Se borraron {0} registros", items.size()));
    }

    @Override
    public void onWriteError(Exception exception, List<? extends RttDetailDTO> items) {
        logger.info("Sucedio un error al borrar uno de los registros");
    }

}
