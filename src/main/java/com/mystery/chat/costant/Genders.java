package com.mystery.chat.costant;

import java.util.HashMap;
import java.util.Map;

/**
 * Genders
 *
 * @author shouchen
 * @date 2022/11/29
 */
public final class Genders {
    public static final String M = "M";
    public static final String M_VALUE = "男";
    public static final String F = "F";
    public static final String F_VALUE = "女";
    public static final String MtF = "MtF";
    public static final String MtF_VALUE = "跨女";
    public static final String FtM = "FtM";
    public static final String FtM_VALUE = "跨男";
    public static final String OTHER = "OTHER";
    public static final String OTHER_VALUE = "其他";
    private static final Map<String, String> GENDER_MAP = new HashMap<>() {
        {
            put(M, M_VALUE);
            put(F, F_VALUE);
            put(MtF, MtF_VALUE);
            put(FtM, FtM_VALUE);
            put(OTHER, OTHER_VALUE);
        }
    };

    public static String parseGender(String gender) {
        return GENDER_MAP.getOrDefault(gender, OTHER_VALUE);
    }

    public static String checkGender(String gender) {
        return GENDER_MAP.containsKey(gender) ? gender : OTHER;
    }
}
