package com.example.notebookmgt.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "villages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id", nullable = false)
    private Cell cell;
}
