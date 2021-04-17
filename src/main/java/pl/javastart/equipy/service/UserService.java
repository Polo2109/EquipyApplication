package pl.javastart.equipy.service;

import org.springframework.stereotype.Service;
import pl.javastart.equipy.dto.UserAssignmentDto;
import pl.javastart.equipy.dto.UserDto;
import pl.javastart.equipy.exceptions.DuplicatePeselException;
import pl.javastart.equipy.exceptions.UserNotFoundException;
import pl.javastart.equipy.mapper.AssignmentMapper;
import pl.javastart.equipy.mapper.UserMapper;
import pl.javastart.equipy.model.User;
import pl.javastart.equipy.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> finadAll(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<UserDto> findByLastName(String lastName){
        return userRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    public UserDto save(UserDto user){
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent(p->{
            throw new DuplicatePeselException();
        });
        return mapAndSave(user);
    }

    public Optional<UserDto> findById(Long id){
        return userRepository.findById(id).map(UserMapper::toDto);
    }
    public UserDto update(UserDto user){
        Optional<User> userByPesel = userRepository.findByPesel(user.getPesel());
        userByPesel.ifPresent(p->{
            if(!p.getId().equals(user.getId()))
                throw new DuplicatePeselException();
        });
        return mapAndSave(user);
    }
    public UserDto mapAndSave(UserDto user){
        User userEntity = UserMapper.toEntity(user);
        User savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }
    public List<UserAssignmentDto> findAllAssignmentsByUserId (Long userId){
        return userRepository.findById(userId)
                .map(User::getAssignments)
                .orElseThrow(UserNotFoundException::new)
                .stream()
                .map(AssignmentMapper::toDtoUserAssignment)
                .collect(Collectors.toList());
    }

}
