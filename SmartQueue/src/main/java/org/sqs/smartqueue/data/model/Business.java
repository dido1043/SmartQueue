package org.sqs.smartqueue.data.model;

import jakarta.persistence.*;
import lombok.Data;
import org.sqs.smartqueue.data.annotation.Rating;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "app_business")
@Data
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id",  nullable = false)
    private User owner;
    @Column(name = "business_name", nullable = false)
    private String name;
    @Column(name = "business_activity", nullable = false)
    private String activity;
    @Rating
    private int rating;
    @Column(name = "available_times")
    private List<LocalDateTime> availableDates;
}
