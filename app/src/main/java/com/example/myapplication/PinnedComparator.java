package com.example.myapplication;

import com.example.myapplication.date.Content;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class PinnedComparator implements Comparator<Content> {
    @Override
    public int compare(Content item1, Content item2) {
        // 首先按照isPinned状态排序
        if (item1.isPinned() && !item2.isPinned()) {
            return -1; // 如果item1是置顶的而item2不是，item1应该排在前面
        } else if (!item1.isPinned() && item2.isPinned()) {
            return 1; // 如果item2是置顶的而item1不是，item2应该排在前面
        } else {
            // 如果isPinned状态相同，可以按照date排序
            LocalDateTime date1 = LocalDateTime.parse(item1.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime date2 = LocalDateTime.parse(item2.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return date1.compareTo(date2);
        }
    }
}