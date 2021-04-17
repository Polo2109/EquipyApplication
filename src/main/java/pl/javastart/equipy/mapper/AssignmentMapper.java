package pl.javastart.equipy.mapper;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.dto.AssetAssignmentDto;
import pl.javastart.equipy.dto.AssignmentDto;
import pl.javastart.equipy.dto.UserAssignmentDto;
import pl.javastart.equipy.model.Asset;
import pl.javastart.equipy.model.Assignment;
import pl.javastart.equipy.model.User;
import pl.javastart.equipy.repository.AssetRepository;
import pl.javastart.equipy.repository.UserRepository;

import java.util.Optional;

public class AssignmentMapper {

//    private AssetRepository assetRepository;
//    private UserRepository userRepository;
//
//    public AssignmentMapper(AssetRepository assetRepository, UserRepository userRepository) {
//        this.assetRepository = assetRepository;
//        this.userRepository = userRepository;
//    }

    public static UserAssignmentDto toDtoUserAssignment(Assignment assignment){
        UserAssignmentDto userAssignmentDto = new UserAssignmentDto();
        userAssignmentDto.setId(assignment.getId());
        userAssignmentDto.setStart(assignment.getStart());
        userAssignmentDto.setEnd(assignment.getEnd());
        if(assignment.getAsset() != null) {
            userAssignmentDto.setAssetId(assignment.getAsset().getId());
            userAssignmentDto.setAssetName(assignment.getAsset().getName());
            userAssignmentDto.setAssetSerialNumber(assignment.getAsset().getSerialNumber());
        }
        return userAssignmentDto;
    }
    public static AssetAssignmentDto toDtoAssetAssignment(Assignment assignment){
        AssetAssignmentDto assetAssignmentDto = new AssetAssignmentDto();
        assetAssignmentDto.setId(assignment.getId());
        assetAssignmentDto.setStart(assignment.getStart());
        assetAssignmentDto.setEnd(assignment.getEnd());
        if(assignment.getUser() != null) {
            assetAssignmentDto.setUserId(assignment.getUser().getId());
            assetAssignmentDto.setFirstName(assignment.getUser().getFirstName());
            assetAssignmentDto.setLastName(assignment.getUser().getLastName());
            assetAssignmentDto.setPesel(assignment.getUser().getPesel());
        }
        return assetAssignmentDto;
    }
    public static AssignmentDto toDtoAssignment(Assignment assignment){
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setId(assignment.getId());
        assignmentDto.setStart(assignment.getStart());
        assignmentDto.setEnd(assignment.getEnd());
        assignmentDto.setUserId(assignment.getUser().getId());
        assignmentDto.setAssetId(assignment.getAsset().getId());
        return assignmentDto;
    }
//    public Assignment toEntity(UserAssignmentDto userAssignmentDto, Long userId){
//        Assignment assignment = new Assignment();
//        assignment.setId(userAssignmentDto.getId());
//        assignment.setStart(userAssignmentDto.getStart());
//        assignment.setEnd(userAssignmentDto.getEnd());
//        Optional<Asset> asset = assetRepository.findById(userAssignmentDto.getAssetId());
//        asset.ifPresent(assignment::setAsset);
//        Optional<User> user = userRepository.findById(userId);
//        user.ifPresent(assignment::setUser);
//        return assignment;
//    }
}
