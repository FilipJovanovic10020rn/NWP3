package rs.raf.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Permission;
import rs.raf.demo.model.User;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.requests.UserCreateRequest;
import rs.raf.demo.requests.UserEditRequest;
import rs.raf.demo.responses.LoginResponse;
import rs.raf.demo.services.UserService;
import rs.raf.demo.utils.JwtUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }



    //get all users
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getAllUsers(){
        return  ResponseEntity.ok(userService.findAll());
    }

    //create new user
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest userCreate){

        User user = new User();
        String encodedPassword = passwordEncoder.encode(userCreate.getPassword());

        user.setEmail(userCreate.getEmail());
        user.setFirstName(userCreate.getFirstName());
        user.setLastName(userCreate.getLastName());
        user.setPassword(encodedPassword);
        user.setPermissions(userCreate.getPermissions());
        return ResponseEntity.ok(userService.save(user));
    }

    //Find user by id
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();

    }
    //Delete user by id
    @DeleteMapping(value = "/delete/{id}")
    public  ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
        Optional<User> user = userService.findById(id);

        if(user.isPresent()){
            userService.deleteById(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    //Edit user
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id, @RequestBody UserEditRequest editUser){
        Optional<User> u = userService.findById(id);
        if(u.isPresent()){
            User user = u.get();

//            boolean perm = false;
//            for(Permission permission:user.getPermissions()){
//                if(permission.getDescription().equals("can_update_users")){
//                    perm = true;
//                }
//            }
//            if(!perm)
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");


            if(editUser.getFirstName() != null || editUser.getFirstName() != "")
                user.setFirstName(editUser.getFirstName());
            if(editUser.getLastName() != null || editUser.getLastName() != "")
                user.setLastName(editUser.getLastName());
            if(editUser.getEmail() != null || editUser.getEmail() != "")
                user.setEmail(editUser.getEmail());
            if(editUser.getPermissions() != null)
                user.setPermissions(editUser.getPermissions());
            return ResponseEntity.ok(userService.save(user));
        }
        return ResponseEntity.notFound().build();
    }

}