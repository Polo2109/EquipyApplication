package pl.javastart.equipy.mapper;

import pl.javastart.equipy.dto.UserDto;
import pl.javastart.equipy.model.User;

public class UserMapper {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPesel(user.getPesel());
        return dto;
    }

    public static User toEntity(UserDto dto){
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPesel(dto.getPesel());
        return user;
    }
}
