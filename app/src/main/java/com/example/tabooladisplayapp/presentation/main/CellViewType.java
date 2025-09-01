package com.example.tabooladisplayapp.presentation.main;

public enum CellViewType {
    DATA(0),
    EMPTY(1),
    TABOOLA_WIDGET(2),
    TABOOLA_WIDGET_FEED(3);


    private final int value;

    CellViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CellViewType fromValue(int value) {
        for (CellViewType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown view type: " + value);
    }
}
