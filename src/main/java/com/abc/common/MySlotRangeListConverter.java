package com.abc.common;

import com.abc.dto.MySlotRange;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * convert 8192-12287,xxx-xxx to a List<SlotRange>
 */
public class MySlotRangeListConverter implements Converter<String, List<MySlotRange>> {
    @Override
    public List<MySlotRange> convert(String source) {
        Assert.notNull(source);
        String[] slotRanges = source.split(",");
        List<MySlotRange> mySlotRangeList = new ArrayList<>();
        for (String slotRange : slotRanges) {
            String[] slot = slotRange.split("-");
            mySlotRangeList.add(new MySlotRange(new Integer(slot[0]), new Integer(slot[1])));
        }
        return mySlotRangeList;
    }
}
