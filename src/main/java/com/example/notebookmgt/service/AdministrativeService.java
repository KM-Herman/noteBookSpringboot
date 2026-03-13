package com.example.notebookmgt.service;

import com.example.notebookmgt.models.*;
import com.example.notebookmgt.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
public class AdministrativeService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdministrativeService(ProvinceRepository provinceRepository,
                                 DistrictRepository districtRepository,
                                 SectorRepository sectorRepository,
                                 CellRepository cellRepository,
                                 VillageRepository villageRepository,
                                 ObjectMapper objectMapper) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.sectorRepository = sectorRepository;
        this.cellRepository = cellRepository;
        this.villageRepository = villageRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void importAdministrativeData(String json) throws Exception {
        JsonNode rootNode = objectMapper.readTree(json);
        
        // The expected JSON structure is hierarchical:
        // Province -> District -> Sector -> Cell -> Village
        
        // Example structure based on common Rwanda datasets:
        // { "Kigali": { "Gasabo": { "Kacyiru": { "Kamatamu": ["Village1", "Village2"] } } } }
        
        Iterator<Map.Entry<String, JsonNode>> provinces = rootNode.fields();
        while (provinces.hasNext()) {
            Map.Entry<String, JsonNode> provinceEntry = provinces.next();
            String provinceName = provinceEntry.getKey();
            
            Province province = provinceRepository.save(Province.builder()
                    .name(provinceName)
                    .code(generateCode(provinceName))
                    .build());
            
            Iterator<Map.Entry<String, JsonNode>> districts = provinceEntry.getValue().fields();
            while (districts.hasNext()) {
                Map.Entry<String, JsonNode> districtEntry = districts.next();
                String districtName = districtEntry.getKey();
                
                District district = districtRepository.save(District.builder()
                        .name(districtName)
                        .code(generateCode(districtName))
                        .province(province)
                        .build());
                
                Iterator<Map.Entry<String, JsonNode>> sectors = districtEntry.getValue().fields();
                while (sectors.hasNext()) {
                    Map.Entry<String, JsonNode> sectorEntry = sectors.next();
                    String sectorName = sectorEntry.getKey();
                    
                    Sector sector = sectorRepository.save(Sector.builder()
                            .name(sectorName)
                            .code(generateCode(sectorName))
                            .district(district)
                            .build());
                    
                    Iterator<Map.Entry<String, JsonNode>> cells = sectorEntry.getValue().fields();
                    while (cells.hasNext()) {
                        Map.Entry<String, JsonNode> cellEntry = cells.next();
                        String cellName = cellEntry.getKey();
                        
                        Cell cell = cellRepository.save(Cell.builder()
                                .name(cellName)
                                .code(generateCode(cellName))
                                .sector(sector)
                                .build());
                        
                        JsonNode villagesNode = cellEntry.getValue();
                        if (villagesNode.isArray()) {
                            for (JsonNode villageNode : villagesNode) {
                                String villageName = villageNode.asText();
                                villageRepository.save(Village.builder()
                                        .name(villageName)
                                        .code(generateCode(villageName))
                                        .cell(cell)
                                        .build());
                            }
                        }
                    }
                }
            }
        }
    }

    private String generateCode(String name) {
        // Simple code generation for demonstration. In production, this would use official codes.
        return name.substring(0, Math.min(name.length(), 4)).toUpperCase() + "-" + (int)(Math.random() * 1000);
    }

    public Optional<Village> findVillageByNameOrCode(String identity) {
        return villageRepository.findByCode(identity)
                .or(() -> villageRepository.findByName(identity));
    }
}
