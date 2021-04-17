package pl.javastart.equipy.service;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.dto.AssetAssignmentDto;
import pl.javastart.equipy.dto.AssetDto;
import pl.javastart.equipy.exceptions.AssetNotFoundException;
import pl.javastart.equipy.exceptions.DuplicatePeselException;
import pl.javastart.equipy.exceptions.DuplicateSerialNumberException;
import pl.javastart.equipy.mapper.AssetMapper;
import pl.javastart.equipy.mapper.AssignmentMapper;
import pl.javastart.equipy.model.Asset;
import pl.javastart.equipy.repository.AssetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {

    private AssetRepository assetRepository;
    private AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    public List<AssetDto> findAll(){
        return assetRepository.findAll()
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<AssetDto> findAllByName(String text){
        return assetRepository.findByNameOrSerialNumber(text)
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }
    public AssetDto save(AssetDto assetDto){
        Optional<Asset> bySerialNumber = assetRepository.findBySerialNumber(assetDto.getSerialNumber());
        bySerialNumber.ifPresent(p ->{
            throw new DuplicateSerialNumberException();
        });
        return mapAndSave(assetDto);
    }
    public Optional<AssetDto> findById(Long id){
        return assetRepository.findById(id)
                .map(assetMapper::toDto);
    }
    public AssetDto update(AssetDto assetDto){
        Optional<Asset> bySerialNumber = assetRepository.findBySerialNumber(assetDto.getSerialNumber());
        bySerialNumber.ifPresent(p ->{
            if (!p.getId().equals(assetDto.getId()))
                throw new DuplicatePeselException();
        });
        return mapAndSave(assetDto);
    }

    private AssetDto mapAndSave(AssetDto assetDto) {
        Asset assetEntity = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }
    public List<AssetAssignmentDto> findAllAssignmentsByAssetId(Long assetId){
        return assetRepository.findById(assetId)
                .map(Asset::getAssignments)
                .orElseThrow(AssetNotFoundException::new)
                .stream()
                .map(AssignmentMapper::toDtoAssetAssignment)
                .collect(Collectors.toList());
    }
}
