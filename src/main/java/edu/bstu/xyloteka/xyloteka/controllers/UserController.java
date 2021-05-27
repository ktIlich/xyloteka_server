package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Classis;
import edu.bstu.xyloteka.xyloteka.models.Role;
import edu.bstu.xyloteka.xyloteka.models.User;
import edu.bstu.xyloteka.xyloteka.models.UserRole;
import edu.bstu.xyloteka.xyloteka.payload.request.RoleRequest;
import edu.bstu.xyloteka.xyloteka.repo.RoleRepository;
import edu.bstu.xyloteka.xyloteka.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        try {
            List<User> users = new ArrayList<>(repo.findAll());

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = repo.findById(id);

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername(@RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String email) {
        Optional<User> userData = Optional.empty();
        if (username != null) {
             userData = repo.findByUsername(username);
        } else if (email != null) {
            userData = repo.findByEmail(email);
        }

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = repo.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setFirstName(user.getFirstName());
            _user.setSecondName(user.getSecondName());
            _user.setLastName(user.getLastName());
            _user.setOrganization(user.getOrganization());
            _user.setPhone(user.getPhone());
            _user.setAbout(user.getAbout());
            return new ResponseEntity<>(repo.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{id}/role/")
    public ResponseEntity<User> updateUserRole(@PathVariable("id") long id, @RequestBody boolean admin) {
        Optional<User> userData = repo.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            if (admin) {
                Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            }
            _user.setRoles(roles);
            return new ResponseEntity<>(repo.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

