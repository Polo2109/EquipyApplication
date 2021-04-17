package pl.javastart.equipy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.equipy.dto.UserAssignmentDto;
import pl.javastart.equipy.dto.UserDto;
import pl.javastart.equipy.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    List<UserDto> findAll(@RequestParam(required = false) String lastName){
        if (lastName != null)
            return userService.findByLastName(lastName);
        else
            return userService.finadAll();
    }

    @PostMapping("")
    public ResponseEntity<UserDto> save(@RequestBody UserDto user){
        if(user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zapisywany obiekt nie może mieć ustawionego id");
        UserDto savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto user){
        if(!id.equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        UserDto updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{userId}/assignments")
    public ResponseEntity<List<UserAssignmentDto>> findAllAssignmentsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findAllAssignmentsByUserId(userId));
    }

}
