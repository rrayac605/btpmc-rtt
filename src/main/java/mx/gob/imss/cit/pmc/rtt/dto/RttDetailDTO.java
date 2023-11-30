package mx.gob.imss.cit.pmc.rtt.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MCT_PMC_DETALLE_RTT", schema = "MGCARGA1")
public class RttDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public RttDetailDTO() {
    }

    public RttDetailDTO(Long id, String objectId) {
        setId(id);
        setMongoObjectId(objectId);
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PMCDETALLERTT")
    @SequenceGenerator(name = "SEQ_PMCDETALLERTT", sequenceName = "SEQ_PMCDETALLERTT", allocationSize = 1,
        schema = "MGCARGA1")
    @Column(name = "CVE_ID_PMC_DETALLE_RTT", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "REF_CVE_ID_PMC_ENCABEZADO_RTT", nullable = false)
    private Long cveIdPmcRttEncabezado;

    @Getter
    @Setter
    @Column(name = "OBJECT_ID", nullable = false)
    private String mongoObjectId;

    @Getter
    @Setter
    @Column(name = "NUM_NSS", nullable = false)
    private String numNss;

    @Getter
    @Setter
    @Column(name = "REF_REGISTRO_PATRONAL", nullable = false)
    private String refRegistroPatronal;

    @Getter
    @Setter
    @Column(name = "CVE_ID_CONSECUENCIA", nullable = false)
    private Integer cveIdConsecuencia;

    @Getter
    @Setter
    @Column(name = "FEC_INICIO", nullable = false)
    private Date fecInicio;

    @Getter
    @Setter
    @Column(name = "NUM_DIAS_SUBSIDIADOS", nullable = false)
    private Integer numDiasSubsidiados;

    @Getter
    @Setter
    @Column(name = "FEC_TERMINO", nullable = false)
    private Date fecTermino;

    @Getter
    @Setter
    @Column(name = "CVE_ID_TIPO_RIESGO",nullable = false)
    private Integer cveTipoRiesgo;

    @Getter
    @Setter
    @Column(name = "NUM_PORCENTAJE_INCAPACIDAD", nullable = false)
    private Integer numPorcentajeIncapacidad;

    @Getter
    @Setter
    @Column(name = "NOM_NOMBRE_ASEGURADO", nullable = false)
    private String nombre;

    @Getter
    @Setter
    @Column(name = "NOM_APELLIDO_PAT", nullable = false)
    private String apellidoPat;

    @Getter
    @Setter
    @Column(name = "NOM_APELLIDO_MAT", nullable = false)
    private String apellidoMat;

    @Getter
    @Setter
    @Column(name = "REF_FILLER1", nullable = false)
    private String refFiller1;

    @Getter
    @Setter
    @Column(name = "REF_CURP", nullable = false)
    private String curp;

    @Getter
    @Setter
    @Column(name = "REF_FILLER2")
    private String refFiller2;

    @Getter
    @Setter
    @Column(name = "CVE_DELEGACION_ASEGURADO", nullable = false)
    private Integer cveDelegacionAsegurado;

    @Getter
    @Setter
    @Column(name = "CVE_SUBDELEGACION_ASEGURADO", nullable = false)
    private Integer cveSubdelegacionAsegurado;

    @Getter
    @Setter
    @Column(name = "REF_FILLER3", nullable = false)
    private String refFiller3;

    @Getter
    @Setter
    @Column(name = "IND_PROCESADO", nullable = false)
    private Boolean indProcesado;

    @Getter
    @Setter
    @Column(name = "FEC_ALTA", nullable = false)
    private Date fecAlta;

    @Getter
    @Setter
    @Column(name = "FEC_ACTUALIZACION", nullable = false)
    private Date fecActualizacion;

    @Override
    public String toString() {
        return "RttDetailDTO{" +
                "id=" + id +
                ", cveIdPmcRttEncabezado=" + cveIdPmcRttEncabezado +
                ", mongoObjectId='" + mongoObjectId + '\'' +
                ", numNss='" + numNss + '\'' +
                ", refRegistroPatronal='" + refRegistroPatronal + '\'' +
                ", cveIdConsecuencia=" + cveIdConsecuencia +
                ", fecInicio=" + fecInicio +
                ", numDiasSubsidiados=" + numDiasSubsidiados +
                ", fecTermino=" + fecTermino +
                ", cveTipoRiesgo=" + cveTipoRiesgo +
                ", numPorcentajeIncapacidad=" + numPorcentajeIncapacidad +
                ", nombre='" + nombre + '\'' +
                ", apellidoPat='" + apellidoPat + '\'' +
                ", apellidoMat='" + apellidoMat + '\'' +
                ", refFiller1='" + refFiller1 + '\'' +
                ", curp='" + curp + '\'' +
                ", refFiller2='" + refFiller2 + '\'' +
                ", cveDelegacionAsegurado=" + cveDelegacionAsegurado +
                ", cveSubdelegacionAsegurado=" + cveSubdelegacionAsegurado +
                ", refFiller3='" + refFiller3 + '\'' +
                ", indProcesado=" + indProcesado +
                ", fecAlta=" + fecAlta +
                ", fecActualizacion=" + fecActualizacion +
                '}';
    }
}
