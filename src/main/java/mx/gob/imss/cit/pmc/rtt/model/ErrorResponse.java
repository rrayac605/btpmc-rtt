package mx.gob.imss.cit.pmc.rtt.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import mx.gob.imss.cit.pmc.rtt.enums.EnumHttpStatus;

@Data
public class ErrorResponse {

	// HTTP Status code
	private String code;
	// contact email first aids
	private String contactEmail;
	// HTTP Status description
	private String description;
	// Business message for frontend
	private String businessMessage;
	// Real message exception
	private String reasonPhrase;
	// URI for help (future use)
	private String uri;
	// time stamp Exception
	private String timeStamp;

	public ErrorResponse(EnumHttpStatus status, String businessMessage, String reasonPhrase) {

		this.code = status.getCode().toString();
		this.businessMessage = businessMessage;
		this.reasonPhrase = reasonPhrase;
		this.description = status.getDescription();
		this.uri = "http://pmc.imss.gob.mx/help";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		this.timeStamp = dateFormat.format(new Date());

	}
}
