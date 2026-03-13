package com.example.notebookmgt.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "cells")
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

    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Village> villages = new HashSet<>();

    public Cell() {}

    public Cell(Long id, String name, String code, Sector sector, Set<Village> villages) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sector = sector;
        this.villages = villages != null ? villages : new HashSet<>();
    }

    public static CellBuilder builder() { return new CellBuilder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
    public Set<Village> getVillages() { return villages; }
    public void setVillages(Set<Village> villages) { this.villages = villages; }

    public static class CellBuilder {
        private Long id;
        private String name;
        private String code;
        private Sector sector;
        private Set<Village> villages;

        public CellBuilder id(Long id) { this.id = id; return this; }
        public CellBuilder name(String name) { this.name = name; return this; }
        public CellBuilder code(String code) { this.code = code; return this; }
        public CellBuilder sector(Sector sector) { this.sector = sector; return this; }
        public CellBuilder villages(Set<Village> villages) { this.villages = villages; return this; }
        public Cell build() { return new Cell(id, name, code, sector, villages); }
    }
}
