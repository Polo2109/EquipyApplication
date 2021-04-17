package pl.javastart.equipy.mapper;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.dto.AssetDto;
import pl.javastart.equipy.model.Asset;
import pl.javastart.equipy.model.Category;
import pl.javastart.equipy.repository.CategoryRepository;

import java.util.Optional;

@Service
public class AssetMapper {

    private CategoryRepository categoryRepository;

    public AssetMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public AssetDto toDto (Asset asset){
        AssetDto dto = new AssetDto();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setDescription(asset.getDescription());
        dto.setSerialNumber(asset.getSerialNumber());
        if(asset.getCategory() != null)
            dto.setCategory(asset.getCategory().getName());
        return dto;
    }

    public Asset toEntity (AssetDto assetDto){
        Asset entity = new Asset();
        entity.setId(assetDto.getId());
        entity.setName(assetDto.getName());
        entity.setDescription(assetDto.getDescription());
        entity.setSerialNumber(assetDto.getSerialNumber());
        Optional<Category> category = categoryRepository.findByName(assetDto.getCategory());
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
