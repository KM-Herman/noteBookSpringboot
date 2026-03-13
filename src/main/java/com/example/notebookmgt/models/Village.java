package com.example.notebookmgt.models;

import jakarta.persistence.*;
@Entity
@Table(name = "villages")
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

    public Village() {}

    public Village(Long id, String name, String code, Cell cell) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cell = cell;
    }

    public static VillageBuilder builder() { return new VillageBuilder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Cell getCell() { return cell; }
    public void setCell(Cell cell) { this.cell = cell; }

    public static class VillageBuilder {
        private Long id;
        private String name;
        private String code;
        private Cell cell;

        public VillageBuilder id(Long id) { this.id = id; return this; }
        public VillageBuilder name(String name) { this.name = name; return this; }
        public VillageBuilder code(String code) { this.code = code; return this; }
        public VillageBuilder cell(Cell cell) { this.cell = cell; return this; }
        public Village build() { return new Village(id, name, code, cell); }
    }
}
