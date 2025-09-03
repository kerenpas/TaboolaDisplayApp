package com.example.tabooladisplayapp.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cellColor")
public class CellColorEntity {
    @PrimaryKey
    public int position;
    public int color;
    public boolean isVisible;

    public CellColorEntity(int position, int color, boolean isVisible) {
        this.position = position;
        this.color = color;
        this.isVisible = isVisible;
    }
}

