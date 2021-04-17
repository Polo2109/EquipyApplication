package pl.javastart.equipy.service;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.dto.AssignmentDto;
import pl.javastart.equipy.exceptions.AssignmentNotFoundException;
import pl.javastart.equipy.exceptions.InvalidAssignmentException;
import pl.javastart.equipy.mapper.AssignmentMapper;
import pl.javastart.equipy.model.Asset;
import pl.javastart.equipy.model.Assignment;
import pl.javastart.equipy.model.User;
import pl.javastart.equipy.repository.AssetRepository;
import pl.javastart.equipy.repository.AssignmentRepository;
import pl.javastart.equipy.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssignmentsService {

    private AssignmentRepository assignmentRepository;
    private AssetRepository assetRepository;
    private UserRepository userRepository;

    public AssignmentsService(AssignmentRepository assignmentRepository, AssetRepository assetRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        Optional<Assignment> activeAssignment = assignmentRepository.findByAsset_IdAndEndIsNull(assignmentDto.getAssetId());
        activeAssignment.ifPresent(p -> {
            throw new InvalidAssignmentException("To wyposażenie jest aktualnie do kogoś przypisane");
        });
        Optional<Asset> asset = assetRepository.findById(assignmentDto.getAssetId());
        Optional<User> user = userRepository.findById(assignmentDto.getUserId());
        Assignment assignment = new Assignment();
        assignment.setAsset(asset.orElseThrow(() ->
                new InvalidAssignmentException("Brak wyposażenia z id: " + assignmentDto.getAssetId())));
        assignment.setUser(user.orElseThrow(() ->
                new InvalidAssignmentException("Brak użytkownika z id:" + assignmentDto.getUserId())));
        assignment.setStart(LocalDateTime.now());
        return AssignmentMapper.toDtoAssignment(assignmentRepository.save(assignment));
    }

    @Transactional
    public LocalDateTime updateEndTime(Long assignmentId) {
        LocalDateTime endTime = LocalDateTime.now();
        assignmentRepository.findById(assignmentId)
                .ifPresentOrElse(p -> {
                    if(p.getEnd() != null)
                        throw new InvalidAssignmentException("Wyposażenie ma już przypisaną datę zwrotu (zostało już zwrócone)");
                    p.setEnd(endTime);
                }, () -> {
                    throw new AssignmentNotFoundException();
                });
        return endTime;
    }
}
