package mx.gob.imss.cit.pmc.rtt.processor;

import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.dto.ChangeDTO;
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
public class RttDetailChangeProcessor implements ItemProcessor<ChangeDTO, RttDetailDTO> {

    private JobParameters parameters;

    private static final Logger logger = LoggerFactory.getLogger(RttDetailProcessor.class);

    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) {
        parameters = stepExecution.getJobExecution().getJobParameters();
    }


    @Override
    public RttDetailDTO process(ChangeDTO changeDTO) throws Exception {
    	try {
            Long headerId = parameters.getLong(RttBatchConstants.HEADER_ID);
            RttDetailDTO rttDetailDTO = new RttDetailDTO();
            rttDetailDTO.setCveIdPmcRttEncabezado(headerId);
            rttDetailDTO.setMongoObjectId(changeDTO.getObjectIdCambio());
            rttDetailDTO.setNumNss(changeDTO.getNumNss());
            rttDetailDTO.setRefRegistroPatronal(StringUtils.safeSubString(changeDTO.getRefRegistroPatronal(),
                    RttBatchConstants.TEN));
            rttDetailDTO.setCveIdConsecuencia(NumberUtils.processConsequence(
                    changeDTO.getCveConsecuencia(), changeDTO.getNumDiasSubsidiados()));
            rttDetailDTO.setFecInicio(DateUtils.orElse(changeDTO.getFecInicio(), changeDTO.getFecAccidente()));
            rttDetailDTO.setNumDiasSubsidiados(NumberUtils.safeValidateInteger(
                    changeDTO.getNumDiasSubsidiados()));
            rttDetailDTO.setFecTermino(DateUtils.orElse(changeDTO.getFecFin(), changeDTO.getFecIniPension(),
                    changeDTO.getFecAltaIncapacidad()));
            rttDetailDTO.setCveTipoRiesgo(NumberUtils.safeValidateInteger(changeDTO.getCveTipoRiesgo()));
            rttDetailDTO.setNumPorcentajeIncapacidad(NumberUtils.safeValidateInteger(
                    changeDTO.getPorcentajeIncapacidad()));
            rttDetailDTO.setNombre(StringUtils.safeValidate(changeDTO.getNomAsegurado()));
            rttDetailDTO.setApellidoPat(StringUtils.safeValidate(changeDTO.getRefPrimerApellido()));
            rttDetailDTO.setApellidoMat(StringUtils.safeValidate(changeDTO.getRefSegundoApellido()));
            rttDetailDTO.setRefFiller1(RttBatchConstants.FILLER7);
            rttDetailDTO.setCurp(changeDTO.getRefCurp() != null ? changeDTO.getRefCurp() :
                    RttBatchConstants.MISSING_CURP);
            rttDetailDTO.setRefFiller2(RttBatchConstants.REF_0TRR);
            rttDetailDTO.setCveDelegacionAsegurado(changeDTO.getCveDelegacionNss());
            rttDetailDTO.setCveSubdelegacionAsegurado(changeDTO.getCveSubdelNss());
            rttDetailDTO.setRefFiller3(RttBatchConstants.FILLER14);
            rttDetailDTO.setIndProcesado(Boolean.FALSE);
            rttDetailDTO.setFecAlta(DateUtils.getCurrentMexicoDate());
            rttDetailDTO.setIndProcesado(Boolean.FALSE);
            return rttDetailDTO;
		} catch (Exception e) {
        	logger.error("Inconveniente al mapear datos de cambios a objeto de negocio");
			throw new Exception(e);
		}
    }

}
