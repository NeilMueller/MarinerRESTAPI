/**
 * User controller to handle listing users, adding users, remove users, find user by id,
 * grant/revoke permissions and find users by familyName
 * stores users in Jpa repository
 */

package com.example.MarinerUserREST;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Method to display all users
     * Triggered on GET request at endpoint /users
     * @return Collection of EntityModels of users
     */
    @GetMapping("/users")
    CollectionModel<EntityModel<UserObject>> all() {
        List<EntityModel<UserObject>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    /**
     * Method to display all users with given family name
     * Triggered on GET request at endpoint /users/familyName/{familyName}
     * @param familyName
     * @return Collection of EntityModels of users
     */
    //List Users by family name
    @GetMapping("/users/familyName/{familyName}")
    CollectionModel<EntityModel<UserObject>> searchByFamilyName(@PathVariable String familyName) {
        List<EntityModel<UserObject>> users = repository.findByFamilyNameIs(familyName).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    /**
     * Method to add new user from POST request which contains the JSON representation of a user
     * in the body of the request
     * @param newUser
     * @return
     */
    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody UserObject newUser){
        EntityModel<UserObject> entityModel = assembler.toModel(repository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Find a user from id from a GET request to /users/{id}
     * @param id
     * @return EntityModel of user
     */
    @GetMapping("/users/{id}")
    EntityModel<UserObject> one(@PathVariable Long id) {
        UserObject user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    /**
     * Grants user permission level 1
     * Only displayed in user Entity when permission level is 0
     * Returns HTTP 405 Method not allowed if user permission already set to 1
     * @param id
     * @return Response entity displaying updated user or HTTP405
     */
    @PutMapping("/users/{id}/grant")
    ResponseEntity<?> grantPermission(@PathVariable Long id) {
        UserObject user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if(user.getPermissionType() == UserObject.PERMISSIONLEVELZERO){
            user.setPermissionType(UserObject.PERMISSIONLEVELONE);
            return ResponseEntity.ok(assembler.toModel(repository.save(user)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                .withTitle("Method not allowed")
                .withDetail("User already has permission level " + user.getPermissionType()));

    }

    /**
     * Revokes user permission, setting permission to 0
     * Only displayed in user Entity when permission level is not 0
     * Returns HTTP 405 Method not allowed if user permission already set to 0
     * @param id
     * @return Response entity displaying updated user or HTTP405
     */
    @PutMapping("/users/{id}/revoke")
    ResponseEntity<?> revokePermission(@PathVariable Long id) {
        UserObject user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if(user.getPermissionType() == UserObject.PERMISSIONLEVELONE){
            user.setPermissionType(UserObject.PERMISSIONLEVELZERO);
            return ResponseEntity.ok(assembler.toModel(repository.save(user)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("User already has permission level " + user.getPermissionType()));
    }

    /**
     * Deletes user with provided id from the Jpa repository
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}/delete")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
