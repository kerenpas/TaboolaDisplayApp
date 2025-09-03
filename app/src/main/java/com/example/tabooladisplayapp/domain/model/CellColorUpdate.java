package com.example.tabooladisplayapp.domain.model;

public class CellColorUpdate {
    private final int position;
    private final int color;
    private final boolean isVisable;

    private static final int TRANSPARENT = 0x00000000;

    public CellColorUpdate(int position, int color, boolean isVisable ) {
        this.position = position;
        this.color = color;
        this.isVisable = isVisable;
    }

    public CellColorUpdate(int position, int color) {
        this.position = position;
        this.color = color;
        this.isVisable = true;
    }

    public CellColorUpdate(int position, boolean isVisable) {
        this.position = position;
        this.color = TRANSPARENT;
        this.isVisable = isVisable;
    }


    public int getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public boolean isVisable() {
        return isVisable;
    }
}