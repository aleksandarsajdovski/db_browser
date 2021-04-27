package com.db.browser.spi.model.browser.preview;

import lombok.Getter;

import java.util.Collection;

@Getter
public class RowDTO {
    public Collection<Object> rowValues;

    public RowDTO(Collection<Object> rowValues) {
        this.rowValues = rowValues;
    }
}
