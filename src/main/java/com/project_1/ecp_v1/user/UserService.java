package com.project_1.ecp_v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> getUserDtoById(Integer id){
        return userRepository.findById(id)
                .map(userMapper::userToDtoMapper);
    }

    public List<UserDTO> getAllUsers(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::userToDtoMapper)
                .toList();
    }

    public boolean deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            return false;
        } else {
            userRepository.deleteById(id);
            return true;
        }
    }

    public UserDTO addUser(UserCreationDTO userCreationDTO){
        User user = userMapper.dtoToUserMapper(userCreationDTO);

        userRepository.save(user);
        return userMapper.userToDtoMapper(user);
    }

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
