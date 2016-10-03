package com.abc.dto;

/**
 * Created by xiaopengyu on 2016/10/3.
 */
public class MySlotRange {

    public MySlotRange() {
    }

    public MySlotRange(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    private Integer start;

    private Integer end;

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
