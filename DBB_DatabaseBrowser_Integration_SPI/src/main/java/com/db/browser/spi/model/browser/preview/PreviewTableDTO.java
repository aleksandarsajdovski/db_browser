package com.db.browser.spi.model.browser.preview;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PreviewTableDTO {

    private Set<String> columnName;
    private List<RowDTO> rows;

}
