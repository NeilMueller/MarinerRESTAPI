package com.example.MarinerUserREST;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    UserController(UserRepository repository, UserModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    //List Users
    @GetMapping("/users")
    CollectionModel<EntityModel<UserObject>> all() {
        List<EntityModel<UserObject>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    //List Users by family name
    @GetMapping("/users/familyName/{familyName}")
    CollectionModel<EntityModel<UserObject>> searchByFamilyName(@PathVariable String familyName) {
        List<EntityModel<UserObject>> users = repository.findByFamilyNameIs(familyName).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    //Add user
    @PostMapping("/users")
    UserObject newUser(@RequestBody UserObject newUser){
        return repository.save(newUser);
    }

    //Single user methods
    //Find user by id
    @GetMapping("/users/{id}")
    EntityModel<UserObject> one(@PathVariable Long id) {
        UserObject user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    //UpdateUserPermissions
    @PutMapping("/users/{id}")
    UserObject updatePermission(@RequestBody int permissionType, @PathVariable Long id) {
        return repository.findById(id)
                .map(userObject -> {
                    userObject.setPermissionType(permissionType);
                    Long millis = System.currentTimeMillis();
                    userObject.setPermissionGrantedDate(new Date(millis));
                    return repository.save(userObject);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    //DeleteUser
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        repository.deleteById(id);
    }
}
