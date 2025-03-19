package com.project_1.ecp_v1.mapper;

import com.project_1.ecp_v1.dto.UserCreationDTO;
import com.project_1.ecp_v1.dto.UserDTO;
import com.project_1.ecp_v1.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMapper() {}

    public User dtoToUser(UserCreationDTO userCreationDTO){
        if (userCreationDTO == null){
            return null;
        }

        User user = new User();
        user.setFirstname(userCreationDTO.firstname());
        user.setLastname(userCreationDTO.lastname());
        user.setEmail(userCreationDTO.email());
        user.setPosition(userCreationDTO.position());
        user.setDateOfBirth(userCreationDTO.dateOfBirth());
        user.setEmploymentDate(userCreationDTO.employmentDate());
        return user;
    }

    public UserDTO userToDto(User user){
        if (user == null){
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
    }
}
