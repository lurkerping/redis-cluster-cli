package com.abc.dto;

import org.springframework.util.Assert;

/**
 * redis cluster slot range
 */
public class MySlotRange {

    public MySlotRange() {
    }

    public MySlotRange(Integer start, Integer end) {
        Assert.isTrue(start <= end, "start must less or equal than end!");
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
