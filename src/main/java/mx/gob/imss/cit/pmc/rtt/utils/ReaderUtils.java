package mx.gob.imss.cit.pmc.rtt.utils;

import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;
import mx.gob.imss.cit.pmc.rtt.enums.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.Objects;
import java.util.stream.Stream;

public class ReaderUtils {

    public static Query buildMovementsQueryInitialCharge() {
        Query query = new Query(new Criteria().andOperator(
                Criteria.where(CamposAseguradoEnum.FEC_BAJA.getDesc()).is(null),
                Criteria.where(CamposIncapacidadEnum.CONSECUENCIA.getDesc()).in(RttBatchConstants.CRITERIA_CONSECUENCIA),
                new Criteria().orOperator(
                        new Criteria().andOperator(
                                Criteria.where(CamposAseguradoEnum.CASO_REGISTRO.getDesc()).is(RttBatchConstants.CRITERIA_CASO_REGISTRO_OPORTUNO),
                                new Criteria().orOperator(
                                        Criteria.where(CamposAseguradoEnum.CYCLE.getDesc()).in(DateUtils.calculateCurrentCycle()),
                                        Criteria.where(CamposAseguradoEnum.CYCLE.getDesc()).in(DateUtils.calculateCurrentCycleInt())
                                )
                        ),
                        new Criteria().andOperator(
                                Criteria.where(CamposAseguradoEnum.CASO_REGISTRO.getDesc()).is(RttBatchConstants.CRITERIA_CASO_REGISTRO_EXTEMPORANEO),
                                Criteria.where(CamposAseguradoEnum.FEC_ALTA.getDesc())
                                        .gte(Objects.requireNonNull(DateUtils.calculateBeginDate(String.valueOf(CycleYearUtils.calculateAAAA()),
                                                String.valueOf(RttBatchConstants.JANUARY), String.valueOf(RttBatchConstants.FIRST))))
                                        .lte(Objects.requireNonNull(DateUtils.calculateEndDate(DateUtils.getCurrentYear(),
                                                "12", "31")))
                        )
                ),
                new Criteria().orOperator(
                        Criteria.where(MovementFieldsEnum.IS_PENDING.getDesc()).is(null),
                        Criteria.where(MovementFieldsEnum.IS_PENDING.getDesc()).is(Boolean.FALSE)
                ),
                Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).in(
                        Stream.concat(
                                Stream.of(RttBatchConstants.CRITERIA_ESTADO_REGISTRO_CORRECTO),
                                Stream.of(RttBatchConstants.CRITERIA_ESTADO_REGISTRO_SUSCEPTIBLE)
                        ).toArray()
                )
        )).limit(RttBatchConstants.CHUNK_SIZE);
        query.fields().include(MovementFieldsEnum.ID.getDesc()).include(CamposAseguradoEnum.NUM_NSS.getDesc())
                .include(CamposAseguradoEnum.NOMBRE.getDesc()).include(CamposAseguradoEnum.PRIMER_APELLIDO.getDesc())
                .include(CamposAseguradoEnum.SEGUNDO_APELLIDO.getDesc()).include(CamposAseguradoEnum.CURP.getDesc())
                .include(CamposAseguradoEnum.DELEGACION_NSS.getDesc()).include(CamposAseguradoEnum.SUB_DELEGACION_NSS.getDesc())
                .include(CamposPatronEnum.REGISTRO_PATRONAL.getDesc()).include(CamposIncapacidadEnum.CONSECUENCIA.getDesc())
                .include(CamposIncapacidadEnum.FEC_INICIO.getDesc()).include(CamposIncapacidadEnum.NUM_DIAS_SUBSIDIADOS.getDesc())
                .include(CamposIncapacidadEnum.FEC_FIN.getDesc()).include(CamposIncapacidadEnum.TIPO_RIESGO.getDesc())
                .include(CamposIncapacidadEnum.PORCENTAJE_INCAPACIDAD.getDesc())
                .include(CamposIncapacidadEnum.FEC_ACCIDENTE.getDesc())
                .include(CamposIncapacidadEnum.FEC_ALTA_INCAPACIDAD.getDesc())
                .include(CamposIncapacidadEnum.FEC_INI_PENSION.getDesc());
        return query;
    }

    public static Query buildChangeMN() {
        return new Query(new Criteria().andOperator(
                Criteria.where(ChangeFieldsEnum.FEC_BAJA.getDesc()).is(null),
                Criteria.where(ChangeFieldsEnum.CONSECUENCIA.getDesc()).in(RttBatchConstants.CRITERIA_CONSECUENCIA),
                new Criteria().orOperator(
                        new Criteria().andOperator(
                                Criteria.where(ChangeFieldsEnum.CASO_REGISTRO.getDesc()).is(RttBatchConstants.CRITERIA_CASO_REGISTRO_OPORTUNO),
                                new Criteria().orOperator(
                                        Criteria.where(ChangeFieldsEnum.CYCLE.getDesc()).in(DateUtils.calculateCurrentCycle()),
                                        Criteria.where(ChangeFieldsEnum.CYCLE.getDesc()).in(DateUtils.calculateCurrentCycleInt())
                                )
                        ),
                        new Criteria().andOperator(
                                Criteria.where(ChangeFieldsEnum.CASO_REGISTRO.getDesc()).is(RttBatchConstants.CRITERIA_CASO_REGISTRO_EXTEMPORANEO),
                                Criteria.where(ChangeFieldsEnum.FEC_ALTA.getDesc())
                                        .gte(Objects.requireNonNull(DateUtils.calculateBeginDate(String.valueOf(CycleYearUtils.calculateAAAA()),
                                                String.valueOf(RttBatchConstants.JANUARY), String.valueOf(RttBatchConstants.FIRST))))
                                        .lte(Objects.requireNonNull(DateUtils.calculateEndDate(DateUtils.getCurrentYear(),
                                                "12", "31")))
                        )
                ),
                Criteria.where(ChangeFieldsEnum.ORIGEN_ARCHIVO.getDesc()).is(RttBatchConstants.MANUAL),
                Criteria.where(ChangeFieldsEnum.ESTADO_REGISTRO.getDesc()).in(
                        Stream.concat(
                                Stream.of(RttBatchConstants.CRITERIA_ESTADO_REGISTRO_CORRECTO),
                                Stream.of(RttBatchConstants.CRITERIA_ESTADO_REGISTRO_SUSCEPTIBLE)
                                ).toArray()
                        ),
                Criteria.where(ChangeFieldsEnum.SITUACION_REGISTRO.getDesc()).is(RttBatchConstants.CRITERIA_SITUACION_REGISTRO),
                new Criteria().orOperator(
                        Criteria.where(ChangeFieldsEnum.IS_PENDING.getDesc()).is(null),
                        Criteria.where(ChangeFieldsEnum.IS_PENDING.getDesc()).is(Boolean.FALSE)
                )
        )).limit(RttBatchConstants.CHUNK_SIZE);
    }

}
