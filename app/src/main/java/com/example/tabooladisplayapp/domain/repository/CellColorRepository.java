package com.example.tabooladisplayapp.domain.repository;



import com.example.tabooladisplayapp.domain.model.CellColorUpdate;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface CellColorRepository {
    /**
     * Updates the cell color at the specified position
     * @param update The color update data
     */
    void updateCellColor(CellColorUpdate update) ;

    Observable<List<CellColorUpdate>> observeCells();
}
