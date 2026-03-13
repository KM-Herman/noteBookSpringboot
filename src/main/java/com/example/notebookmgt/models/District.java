package com.example.notebookmgt.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sector> sectors = new HashSet<>();

    public District() {}

    public District(Long id, String name, String code, Province province, Set<Sector> sectors) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.province = province;
        this.sectors = sectors != null ? sectors : new HashSet<>();
    }

    public static DistrictBuilder builder() { return new DistrictBuilder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
    public Set<Sector> getSectors() { return sectors; }
    public void setSectors(Set<Sector> sectors) { this.sectors = sectors; }

    public static class DistrictBuilder {
        private Long id;
        private String name;
        private String code;
        private Province province;
        private Set<Sector> sectors;

        public DistrictBuilder id(Long id) { this.id = id; return this; }
        public DistrictBuilder name(String name) { this.name = name; return this; }
        public DistrictBuilder code(String code) { this.code = code; return this; }
        public DistrictBuilder province(Province province) { this.province = province; return this; }
        public DistrictBuilder sectors(Set<Sector> sectors) { this.sectors = sectors; return this; }
        public District build() { return new District(id, name, code, province, sectors); }
    }
}
