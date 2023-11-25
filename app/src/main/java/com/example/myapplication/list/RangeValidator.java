package com.example.myapplication.list;

import androidx.core.util.Pair;

public interface RangeValidator {
    boolean isValid(Pair<Long, Long> date);
}
