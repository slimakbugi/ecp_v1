package com.project_1.ecp_v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
