package com.project_1.ecp_v1.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMapper() {}

    public User dtoToUserMapper(UserCreationDTO userCreationDTO){
        User user = new User();
        user.setFirstname(userCreationDTO.firstname());
        user.setLastname(userCreationDTO.lastname());
        user.setEmail(userCreationDTO.email());
        user.setPosition(userCreationDTO.position());
        user.setDateOfBirth(userCreationDTO.dateOfBirth());
        user.setEmploymentDate(userCreationDTO.employmentDate());
        return user;
    }

    public UserDTO userToDtoMapper(User user){
        return new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
    }
}
