package com.example.notebookmgt.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cells")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @Builder.Default
    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Village> villages = new HashSet<>();
}
