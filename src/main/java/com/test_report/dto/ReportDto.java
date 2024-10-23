package com.test_report.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private Integer id;
    private String name;
    private int project;
    private int status;
    private String details;
    private Date executionDate;
}
