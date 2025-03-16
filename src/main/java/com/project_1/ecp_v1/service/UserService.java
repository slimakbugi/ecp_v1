package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.UserCreationDTO;
import com.project_1.ecp_v1.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserDtoById(Integer id);
    List<UserDTO> getAllUsers();
    boolean deleteUser(Integer id);
    UserDTO addUser(UserCreationDTO userCreationDTO);
    Optional<UserDTO> partiallyUpdateUser(Integer id, UserCreationDTO userCreationDTO);
    boolean releaseUser(Integer id, LocalDate releaseDate);
    boolean hireUserAgain(Integer id, LocalDate employmentDate);

}
