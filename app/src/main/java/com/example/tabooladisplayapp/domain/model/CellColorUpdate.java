package com.example.tabooladisplayapp.domain.model;

public class CellColorUpdate {
    private final int position;
    private final int color;
    private final boolean isVisible;

    private static final int TRANSPARENT = 0x00000000;

    public CellColorUpdate(int position, int color, boolean isVisible ) {
        this.position = position;
        this.color = color;
        this.isVisible = isVisible;
    }

    public int getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public boolean isVisible() {
        return isVisible;
    }
}