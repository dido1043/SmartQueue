package org.sqs.smartqueue.data.model;

import jakarta.persistence.*;
import lombok.Data;
import org.sqs.smartqueue.data.enums.BookingStatus;

import java.time.LocalDateTime;


@Entity
@Table(name = "app_booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business", nullable = false)
    private Business business;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client", nullable = false)
    private User client;
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
