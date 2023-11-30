package mx.gob.imss.cit.pmc.rtt.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MCT_PMC_RTT_ENCABEZADO", schema = "MGCARGA1")
public class RttHeaderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public RttHeaderDTO() {
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PMCRTTENCABEZADO")
    @SequenceGenerator(name = "SEQ_PMCRTTENCABEZADO", sequenceName = "SEQ_PMCRTTENCABEZADO", allocationSize = 1,
        schema = "MGCARGA1")
    @Column(name = "CVE_ID_PMC_ENCABEZADO_RTT")
    private Long cveIdPmcRttEncabezado;

    @Getter
    @Setter
    @Column(name = "REF_FILLER1", nullable = false)
    private String refFiller1;

    @Getter
    @Setter
    @Column(name = "CVE_DELEGACION", nullable = false)
    private Integer cveDelegacion;

    @Getter
    @Setter
    @Column(name = "REF_TSR", nullable = false)
    private String refTsr;

    @Getter
    @Setter
    @Column(name = "REF_CASUI99", nullable = false)
    private String refCasui99;

    @Getter
    @Setter
    @Column(name = "REF_FILLER2", nullable = false)
    private String refFiller2;

    @Getter
    @Setter
    @Column(name = "REF_CICLO", nullable = false)
    private String refCiclo;

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
    @Column(name = "FEC_ACTUALIZACION")
    private Date fecActualizacion;

}
