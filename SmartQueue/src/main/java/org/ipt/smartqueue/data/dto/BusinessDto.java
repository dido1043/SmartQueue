package org.ipt.smartqueue.data.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BusinessDto {
    private String name;
    private String activity;
    private int rating;
    private List<LocalDateTime> availableDates;
}
