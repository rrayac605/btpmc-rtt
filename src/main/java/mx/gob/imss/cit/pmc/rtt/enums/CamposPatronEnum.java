package mx.gob.imss.cit.pmc.rtt.enums;

import lombok.Getter;

@Getter
public enum CamposPatronEnum {

    REGISTRO_PATRONAL("patronDTO.refRegistroPatronal");

    private final String desc;

    CamposPatronEnum(String desc) {
        this.desc = desc;
    }

}
