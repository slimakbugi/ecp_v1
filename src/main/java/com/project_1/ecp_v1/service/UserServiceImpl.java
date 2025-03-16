package com.project_1.ecp_v1.service;

import com.project_1.ecp_v1.dto.UserCreationDTO;
import com.project_1.ecp_v1.dto.UserDTO;
import com.project_1.ecp_v1.mapper.UserMapper;
import com.project_1.ecp_v1.model.User;
import com.project_1.ecp_v1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDTO> getUserDtoById(Integer id){
        return userRepository.findById(id)
                .map(userMapper::userToDtoMapper);
    }

    @Override
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::userToDtoMapper)
                .toList();
    }

    @Override
    public boolean deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            return false;
        } else {
            userRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public UserDTO addUser(UserCreationDTO userCreationDTO){
        User user = userMapper.dtoToUserMapper(userCreationDTO);

        userRepository.save(user);
        return userMapper.userToDtoMapper(user);
    }

    @Override
    public Optional<UserDTO> partiallyUpdateUser(Integer id, UserCreationDTO userCreationDTO){
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userCreationDTO.firstname() != null) existingUser.setFirstname(userCreationDTO.firstname());
                    if (userCreationDTO.lastname() != null) existingUser.setLastname(userCreationDTO.lastname());
                    if (userCreationDTO.email() != null) existingUser.setEmail(userCreationDTO.email());
                    if (userCreationDTO.dateOfBirth() != null) existingUser.setDateOfBirth(userCreationDTO.dateOfBirth());
                    if (userCreationDTO.position() != null) existingUser.setPosition(userCreationDTO.position());
                    if (userCreationDTO.employmentDate() != null) existingUser.setEmploymentDate(userCreationDTO.employmentDate());

                    return userRepository.save(existingUser);
                })
                .map(userMapper::userToDtoMapper);
    }

    @Override
    public boolean releaseUser(Integer id, LocalDate releaseDate){
        if (userRepository.existsById(id)) {
            userRepository.findById(id)
                    .map(user -> {
                        user.setIsHired(false);
                        user.setReleaseDate(releaseDate);

                        return userRepository.save(user);
                    });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hireUserAgain(Integer id, LocalDate employmentDate){
        if (userRepository.existsById(id)) {
            userRepository.findById(id)
                    .map(user -> {
                        user.setIsHired(true);
                        user.setReleaseDate(LocalDate.of(2099,12,31));
                        user.setEmploymentDate(employmentDate);

                        return userRepository.save(user);
                    });
            return true;
        } else {
            return false;
        }
    }
}
