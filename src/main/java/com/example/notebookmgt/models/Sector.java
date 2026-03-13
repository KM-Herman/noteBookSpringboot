package com.example.notebookmgt.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cell> cells = new HashSet<>();

    public Sector() {}

    public Sector(Long id, String name, String code, District district, Set<Cell> cells) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.district = district;
        this.cells = cells != null ? cells : new HashSet<>();
    }

    public static SectorBuilder builder() { return new SectorBuilder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }
    public Set<Cell> getCells() { return cells; }
    public void setCells(Set<Cell> cells) { this.cells = cells; }

    public static class SectorBuilder {
        private Long id;
        private String name;
        private String code;
        private District district;
        private Set<Cell> cells;

        public SectorBuilder id(Long id) { this.id = id; return this; }
        public SectorBuilder name(String name) { this.name = name; return this; }
        public SectorBuilder code(String code) { this.code = code; return this; }
        public SectorBuilder district(District district) { this.district = district; return this; }
        public SectorBuilder cells(Set<Cell> cells) { this.cells = cells; return this; }
        public Sector build() { return new Sector(id, name, code, district, cells); }
    }
}
