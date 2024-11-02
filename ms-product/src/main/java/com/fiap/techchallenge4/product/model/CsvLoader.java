package com.fiap.techchallenge4.product.model;

import com.fiap.techchallenge4.product.enums.StatusCsv;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
public class CsvLoader {

    private Long id;

    private String fileName;

    private String path;

    private StatusCsv statusCsv;

    private LocalDateTime createdDate;

    public String directoryPathWithFileName() {
        return  getPath()+ File.separator + getFileName();
    }

}
