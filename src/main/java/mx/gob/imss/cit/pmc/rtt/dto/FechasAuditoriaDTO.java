package mx.gob.imss.cit.pmc.rtt.dto;

import com.mongodb.lang.Nullable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class FechasAuditoriaDTO {

	@Setter
	@Getter
	@Nullable
	private Date fecAlta;
	@Setter
	@Getter
	@Nullable
	private Date fecBaja;
	@Setter
	@Getter
	@Nullable
	private Date fecActualizacion;

}
