package com.example.tabooladisplayapp.presentation.model;

import android.graphics.Color;

import com.example.tabooladisplayapp.presentation.main.CellViewType;

public abstract class Cell {
    private final CellViewType type;
    private int backgroundColor = Color.TRANSPARENT;

    private boolean visible = true;

    protected Cell(CellViewType type) {
        this.type = type;
    }

    public int getType() {
        return type.getValue();
    }

    public int getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(int color) { this.backgroundColor = color; }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static class DataCell extends Cell {
        private final String name;
        private final String description;
        private final String thumbnailUrl;

        public DataCell(String name, String description, String thumbnailUrl) {
            super(CellViewType.DATA);
            this.name = name;
            this.description = description;
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getThumbnailUrl() { return thumbnailUrl; }
    }

    public static class EmptyCell extends Cell {
        public EmptyCell() {
            super(CellViewType.EMPTY);
        }
    }

    public static class TaboolaWidgetCell extends Cell {
        public TaboolaWidgetCell() {
            super(CellViewType.TABOOLA_WIDGET);
        }
    }

    public static class TaboolaFeedCell extends Cell {
        public TaboolaFeedCell() {
            super(CellViewType.TABOOLA_WIDGET_FEED);
        }
    }
}
