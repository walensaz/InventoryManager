package com.goodfoods.core.objects;

public enum JobType {

    WELD(1),
    CUT(2),
    PUT_TOGETHER(3),
    CLEAN(4);

    private int id;

    JobType(int id) {
        this.id = id;
    }


}
