package mx.gob.imss.cit.pmc.rtt.utils;

import mx.gob.imss.cit.pmc.rtt.constants.RttBatchConstants;

public class StringUtils {

    public static String safeValidate(String str) {
        return isNotEmpty(str) ? str : RttBatchConstants.EMPTY;
    }

    public static String safeValidateCurp(String curp) {
        return isNotEmpty(curp) ? curp : RttBatchConstants.MISSING_CURP;
    }

    public static Boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static Boolean isNotEmpty(String str) { return !isEmpty(str); }

    public static String safeSubString(String str, Integer length) {
        if (isNotEmpty(str)) {
            return str.length() <= length ? str : str.substring(RttBatchConstants.ZERO, length);
        }
        return RttBatchConstants.MISSING_NSS_RP;
    }

}
