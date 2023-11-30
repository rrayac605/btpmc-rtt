package mx.gob.imss.cit.pmc.rtt.constants;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.springframework.data.domain.Sort;

public class RttBatchConstants {

    public static final String HEADER = "00000000000000000000000000000HEADER";

    public static final String REF_TSR = "TSR";

    public static final String REF_CASUI99 = "CASUI99";

    public static final String FILLER3 = "000";

    public static final String FILLER73 = "0000000000000000000000000000000000000000000000000000000000000000000000000";

    public static final String FILLER7 = "0000000";

    public static final String REF_0TRR = "0RTT";

    public static final String FILLER14 = "00000000000000";

    public static final String PATTERN_DDMMYYYY_TIME = "ddMMyyyy HH:mm:ss";

    public static final Integer DELEGACION = 99;

    public static final String HEADER_ID = "HEADER_ID";

    public static final String NOMBRE_ARCHIVO = "nombreArchivo";

    public static final Integer CHUNK_SIZE = 500;

    public static final String BATCH_SIZE = "batchSize";

    public static final String MANUAL = "MN";

    public static final Integer POOL_SIZE = 64;

    public static final String MISSING_CURP = "000000000000000000";

    public static final String MISSING_NSS_RP = "0000000000";

    public static final String RTT = "RTT";

    public static final Object[] CRITERIA_ESTADO_REGISTRO_CORRECTO = { 1, 5 };

    public static final Object[] CRITERIA_ESTADO_REGISTRO_SUSCEPTIBLE = { 4, 8 };

    public static final Object[] CRITERIA_CONSECUENCIA = { null, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

    public static final Object CRITERIA_SITUACION_REGISTRO = 1;

    public static final Object CRITERIA_CASO_REGISTRO_OPORTUNO = 1;

    public static final Object CRITERIA_CASO_REGISTRO_EXTEMPORANEO = 2;

    public static final Object JANUARY = 1;

    public static final Integer MARCH = 3;

    public static final Integer FIRST = 1;

    public static final Integer FIFTEEN = 15;

    public static final Integer ZERO = 0;

    public static final String ZERO_STRING = "0";

    public static final String FIRST_DAY = "01";

    public static final String BEGIN_HOURS = " 00:00:00";

    public static final String END_HOURS = " 18:59:59";

    public static final String ISO_TIMEZONE = "UTC";

    public static final Integer HOURS_TO_ADD = 6;

    public static final Integer TEN = 10;

    public static final String EMPTY = "";

    public static final String ORACLE_URL = "oracleUrl";

    public static final String ORACLE_USERNAME = "oracleUsername";

    public static final String ORACLE_PASSWORD = "oraclePassword";

    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public static final String MOVEMENT_DTO_PACKAGE_CLASS = "mx.gob.imss.cit.pmc.rtt.dto.MovementDTO";

    public static final Map<String, Sort.Direction> READER_SORTER = ImmutableMap.of("_id", Sort.Direction.ASC);

    public static final Map<Integer, Integer> CONSEQUENCE_EQUIVALENCE = ImmutableMap.<Integer, Integer>builder()
            .put(0, 1)
            .put(10, 2)
            .put(11, 3)
            .put(12, 3)
            .build();


    public static final Map<String, String> cycleFrom = ImmutableMap
            .<String, String>builder()
            .put("024", "0103 00:00:00")
            .put("001", "1603 00:00:00")
            .put("002", "0104 00:00:00")
            .put("003", "1604 00:00:00")
            .put("004", "0105 00:00:00")
            .put("005", "1605 00:00:00")
            .put("006", "0106 00:00:00")
            .put("007", "1606 00:00:00")
            .put("008", "0107 00:00:00")
            .put("009", "1607 00:00:00")
            .put("010", "0108 00:00:00")
            .put("011", "1608 00:00:00")
            .put("012", "0109 00:00:00")
            .put("013", "1609 00:00:00")
            .put("014", "0110 00:00:00")
            .put("015", "1610 00:00:00")
            .put("016", "0111 00:00:00")
            .put("017", "1611 00:00:00")
            .put("018", "0112 00:00:00")
            .put("019", "1612 00:00:00")
            .put("020", "0101 00:00:00")
            .put("021", "1601 00:00:00")
            .put("022", "0102 00:00:00")
            .put("023", "1602 00:00:00")
            .build();

    public static final Map<String, String> cycleTo = ImmutableMap
            .<String, String>builder()
            .put("024", "1503 23:59:59")
            .put("001", "3103 23:59:59")
            .put("002", "1504 23:59:59")
            .put("003", "3004 23:59:59")
            .put("004", "1505 23:59:59")
            .put("005", "3105 23:59:59")
            .put("006", "1506 23:59:59")
            .put("007", "3006 23:59:59")
            .put("008", "1507 23:59:59")
            .put("009", "3107 23:59:59")
            .put("010", "1508 23:59:59")
            .put("011", "3108 23:59:59")
            .put("012", "1509 23:59:59")
            .put("013", "3009 23:59:59")
            .put("014", "1510 23:59:59")
            .put("015", "3110 23:59:59")
            .put("016", "1511 23:59:59")
            .put("017", "3011 23:59:59")
            .put("018", "1512 23:59:59")
            .put("019", "3112 23:59:59")
            .put("020", "1501 23:59:59")
            .put("021", "3101 23:59:59")
            .put("022", "1502 23:59:59")
            .put("023", "2802 23:59:59")
            .build();

    public static final String DETAIL_WRITER_SQL_STATEMENT = "INSERT INTO MGCARGA1.MCT_PMC_DETALLE_RTT " +
            "(OBJECT_ID, REF_CVE_ID_PMC_ENCABEZADO_RTT, NUM_NSS, REF_REGISTRO_PATRONAL, CVE_ID_CONSECUENCIA," +
            "FEC_INICIO, NUM_DIAS_SUBSIDIADOS, FEC_TERMINO, CVE_ID_TIPO_RIESGO, NUM_PORCENTAJE_INCAPACIDAD," +
            "NOM_NOMBRE_ASEGURADO, NOM_APELLIDO_PAT, NOM_APELLIDO_MAT, REF_FILLER1, REF_CURP, REF_FILLER2," +
            "CVE_DELEGACION_ASEGURADO, CVE_SUBDELEGACION_ASEGURADO, REF_FILLER3, IND_PROCESADO, FEC_ALTA," +
            "CVE_ID_PMC_DETALLE_RTT)" +
            "VALUES" +
            "(:mongoObjectId, :cveIdPmcRttEncabezado, :numNss, :refRegistroPatronal, :cveIdConsecuencia," +
            ":fecInicio, :numDiasSubsidiados, :fecTermino, :cveTipoRiesgo, :numPorcentajeIncapacidad," +
            ":nombre, :apellidoPat, :apellidoMat, :refFiller1, :curp, :refFiller2," +
            ":cveDelegacionAsegurado, :cveSubdelegacionAsegurado, :refFiller3, :indProcesado, :fecAlta," +
            "MGCARGA1.SEQ_PMCDETALLERTT.NEXTVAL)";

}
