package com.example.tabooladisplayapp.presentation.model;

public abstract class Cell {
    private final int type;

    protected Cell(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static class DataCell extends Cell {
        private final String name;
        private final String description;
        private final String thumbnailUrl;

        public DataCell(String name, String description, String thumbnailUrl) {
            super(0);
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
            super(1);
        }
    }
}
