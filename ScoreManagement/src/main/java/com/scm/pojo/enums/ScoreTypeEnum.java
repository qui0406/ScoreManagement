package com.scm.pojo.enums;

public enum ScoreTypeEnum {
    GIUA_KY(1),
    CUOI_KY(2),
    CHUYEN_CAN(3),
    BAI_TAP_NHOM(4),
    BAI_TAP_LON(5),
    KIEM_TRA_MIENG(6),
    DIEM_HOAT_DONG(7),
    KHAC(8);

    private final int value;

    ScoreTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}