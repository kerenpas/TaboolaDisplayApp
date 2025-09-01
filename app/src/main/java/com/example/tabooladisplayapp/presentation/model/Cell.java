package com.example.tabooladisplayapp.presentation.model;

import com.example.tabooladisplayapp.presentation.main.CellViewType;

public abstract class Cell {
    private final CellViewType type;

    protected Cell(CellViewType type) {
        this.type = type;
    }

    public int getType() {
        return type.getValue();
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
