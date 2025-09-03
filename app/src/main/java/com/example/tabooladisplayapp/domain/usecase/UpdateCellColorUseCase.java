package com.example.tabooladisplayapp.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.tabooladisplayapp.domain.model.CellColorUpdate;
import com.example.tabooladisplayapp.domain.repository.CellColorRepository;

import java.util.List;

import javax.inject.Inject;

public class UpdateCellColorUseCase {
    private final CellColorRepository repository;

    @Inject
    public UpdateCellColorUseCase(CellColorRepository repository) {
        this.repository = repository;
    }

    public void invoke(int position, int color) {
         CellColorUpdate update = new CellColorUpdate(position, color, true);
         repository.updateCellColor(update);
    }
    public void invoke(boolean isVisible, int position, int color) {
        CellColorUpdate update = new CellColorUpdate(position, color, isVisible);
        repository.updateCellColor(update);
    }

}
