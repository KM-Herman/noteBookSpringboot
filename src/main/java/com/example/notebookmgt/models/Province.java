package com.example.notebookmgt.models;

import java.util.*;

import jakarta.persistence.*;
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<District> districts = new HashSet<>();

    public Province() {}

    public Province(Long id, String name, String code, Set<District> districts) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.districts = districts != null ? districts : new HashSet<>();
    }

    public static ProvinceBuilder builder() {
        return new ProvinceBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Set<District> getDistricts() { return districts; }
    public void setDistricts(Set<District> districts) { this.districts = districts; }

    public static class ProvinceBuilder {
        private Long id;
        private String name;
        private String code;
        private Set<District> districts;

        public ProvinceBuilder id(Long id) { this.id = id; return this; }
        public ProvinceBuilder name(String name) { this.name = name; return this; }
        public ProvinceBuilder code(String code) { this.code = code; return this; }
        public ProvinceBuilder districts(Set<District> districts) { this.districts = districts; return this; }
        public Province build() { return new Province(id, name, code, districts); }
    }
}
