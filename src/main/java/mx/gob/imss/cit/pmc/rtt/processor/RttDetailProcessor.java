package mx.gob.imss.cit.pmc.rtt.processor;

import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.dto.IncapacidadDTO;
import mx.gob.imss.cit.pmc.rtt.dto.MovementDTO;
import mx.gob.imss.cit.pmc.rtt.dto.RttDetailDTO;
import mx.gob.imss.cit.pmc.rtt.utils.DateUtils;
import mx.gob.imss.cit.pmc.rtt.utils.NumberUtils;
import mx.gob.imss.cit.pmc.rtt.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class RttDetailProcessor implements ItemProcessor<MovementDTO, RttDetailDTO> {

    private JobParameters parameters;

    private static final Logger logger = LoggerFactory.getLogger(RttDetailProcessor.class);

    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) {
        parameters = stepExecution.getJobExecution().getJobParameters();
    }


    @Override
    public RttDetailDTO process(MovementDTO movementDTO) throws Exception {
    	try {
            Long headerId = parameters.getLong(RttBatchConstants.HEADER_ID);
            IncapacidadDTO incapacity = movementDTO.getIncapacidadDTO();
            RttDetailDTO rttDetailDTO = new RttDetailDTO();
            rttDetailDTO.setCveIdPmcRttEncabezado(headerId);
            rttDetailDTO.setMongoObjectId(movementDTO.getObjectId().toString());
            rttDetailDTO.setNumNss(movementDTO.getAseguradoDTO().getNumNss());
            rttDetailDTO.setNombre(StringUtils.safeValidate(movementDTO.getAseguradoDTO().getNomAsegurado()));
            rttDetailDTO.setApellidoPat(StringUtils.safeValidate(movementDTO.getAseguradoDTO().getRefPrimerApellido()));
            rttDetailDTO.setApellidoMat(StringUtils.safeValidate(movementDTO.getAseguradoDTO().getRefSegundoApellido()));
            rttDetailDTO.setCurp(StringUtils.safeValidateCurp(movementDTO.getAseguradoDTO().getRefCurp()));
            rttDetailDTO.setCveDelegacionAsegurado(movementDTO.getAseguradoDTO().getCveDelegacionNss());
            rttDetailDTO.setCveSubdelegacionAsegurado(movementDTO.getAseguradoDTO().getCveSubdelNss());
            rttDetailDTO.setRefRegistroPatronal(StringUtils.safeSubString(movementDTO.getPatronDTO().getRefRegistroPatronal(),
                    RttBatchConstants.TEN));
            rttDetailDTO.setCveIdConsecuencia(NumberUtils.processConsequence(
                    incapacity.getCveConsecuencia(), incapacity.getNumDiasSubsidiados()));
            rttDetailDTO.setFecInicio(DateUtils.orElse(incapacity.getFecInicio(),
                    incapacity.getFecAccidente()));
            rttDetailDTO.setNumDiasSubsidiados(NumberUtils.safeValidateInteger(
                    incapacity.getNumDiasSubsidiados()));
            rttDetailDTO.setFecTermino(DateUtils.orElse(incapacity.getFecFin(),
                    incapacity.getFecIniPension(), incapacity.getFecAltaIncapacidad()));
            rttDetailDTO.setCveTipoRiesgo(NumberUtils.safeValidateInteger(incapacity.getCveTipoRiesgo()));
            rttDetailDTO.setNumPorcentajeIncapacidad(NumberUtils.safetyParseBigDecimal(
                    incapacity.getPorPorcentajeIncapacidad()));
            rttDetailDTO.setRefFiller1(RttBatchConstants.FILLER7);
            rttDetailDTO.setRefFiller2(RttBatchConstants.REF_0TRR);
            rttDetailDTO.setRefFiller3(RttBatchConstants.FILLER14);
            rttDetailDTO.setIndProcesado(Boolean.FALSE);
            rttDetailDTO.setFecAlta(DateUtils.getCurrentMexicoDate());
            rttDetailDTO.setIndProcesado(Boolean.FALSE);
            return rttDetailDTO;
		} catch (Exception e) {
        	logger.error("Inconveniente al mapear datos de movimientos a objeto de negocio");
			throw new Exception(e);
		}
    }
}
