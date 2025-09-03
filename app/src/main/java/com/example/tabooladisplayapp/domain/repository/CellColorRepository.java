package com.example.tabooladisplayapp.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.tabooladisplayapp.domain.model.CellColorUpdate;

import java.util.List;

public interface CellColorRepository {
    /**
     * Updates the cell color at the specified position
     * @param update The color update data
     */
    void updateCellColor(CellColorUpdate update) ;
    LiveData<List<CellColorUpdate>> observeCells();
}
