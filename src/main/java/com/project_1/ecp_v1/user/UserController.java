package com.project_1.ecp_v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = userService.getAllUsers();

        if(allUsers.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allUsers);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        Optional<UserDTO> user = userService.getUserDtoById(id);

        return user
                .map(u -> ResponseEntity.ok().body(u))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<UserDTO> addUser(@RequestBody UserCreationDTO user){
        UserDTO addedUser = userService.addUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedUser.id())
                .toUri();

        return ResponseEntity.created(uri).body(addedUser);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        boolean isUserDeleted = userService.deleteUser(id);

        if(isUserDeleted){
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
