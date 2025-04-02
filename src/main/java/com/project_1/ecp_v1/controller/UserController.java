package com.project_1.ecp_v1.controller;

import com.project_1.ecp_v1.dto.UserCreationDTO;
import com.project_1.ecp_v1.dto.UserDTO;
import com.project_1.ecp_v1.service.UserServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userServiceImpl.getAllUsers();

        if(allUsers.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allUsers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        Optional<UserDTO> user = userServiceImpl.getUserDtoById(id);

        return user
                .map(u -> ResponseEntity.ok().body(u))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UserCreationDTO user){

        if(userServiceImpl.isUserNull(user)) return ResponseEntity.badRequest().body("Some data are null!");

        UserDTO addedUser;
        try {
            addedUser = userServiceImpl.addUser(user);
        } catch (DataIntegrityViolationException e ){
            return ResponseEntity.badRequest().body("Email address already exists!");
        }

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(addedUser.id())
                    .toUri();

            return ResponseEntity.created(uri).body(addedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserCreationDTO user){
        Optional<UserDTO> updatedUser = userServiceImpl.partiallyUpdateUser(id, user);

        return updatedUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        boolean isUserDeleted = userServiceImpl.deleteUser(id);

        if(isUserDeleted){
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
