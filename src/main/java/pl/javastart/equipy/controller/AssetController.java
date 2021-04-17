package pl.javastart.equipy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.equipy.dto.AssetAssignmentDto;
import pl.javastart.equipy.dto.AssetDto;
import pl.javastart.equipy.dto.UserAssignmentDto;
import pl.javastart.equipy.service.AssetService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("")
    public List<AssetDto> findAll(@RequestParam(required = false) String text){
        if(text != null)
            return assetService.findAllByName(text);
        else
            return assetService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<AssetDto> save(@RequestBody AssetDto assetDto){
        if (assetDto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wyposażenie z takim numerem seryjnym już istnieje");
        AssetDto savedAsset = assetService.save(assetDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAsset.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAsset);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssetDto> findById(@PathVariable Long id){
        return assetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<AssetDto> update(@RequestBody AssetDto assetDto, @PathVariable Long id){
        if(!assetDto.getId().equals(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        AssetDto updatedAsset = assetService.update(assetDto);
        return ResponseEntity.ok(updatedAsset);
    }
    @GetMapping("/{assetId}/assignments")
    public ResponseEntity<List<AssetAssignmentDto>> findAllAssignmentsById(@PathVariable Long assetId){
        return ResponseEntity.ok(assetService.findAllAssignmentsByAssetId(assetId));
    }


}
