package com.fiap.techchallenge4.product.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.techchallenge4.product.repository.enums.StatusCsv;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "csv_loader")
public class CsvLoaderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String path;

    @Enumerated(EnumType.STRING)
    private StatusCsv statusCsv;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdDate;

    //@PrePersist
    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }
}
