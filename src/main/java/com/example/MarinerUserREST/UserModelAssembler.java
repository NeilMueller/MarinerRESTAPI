/**
 * Assembler to cover UserObjects into Spring HATEOAS EntityModel type
 * EntityModel stores the user data as well as a collection of related links
 * for hypermedia powered clients to jump to
 * It always has UserEntities display links to: itself, all users, delete the user, and find all users of the same family name
 * It will display either grant or revoke permissions based on what permission level the user has
 */

package com.example.MarinerUserREST;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserObject, EntityModel<UserObject>> {

    @Override
    public EntityModel<UserObject> toModel(UserObject entity) {
        EntityModel<UserObject> userModel =  EntityModel.of(entity,
                //Create links to self, users, delete and family which are always shown
                linkTo(methodOn(UserController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"),
                linkTo(methodOn(UserController.class).deleteUser(entity.getId())).withRel("delete"),
                linkTo(methodOn(UserController.class).searchByFamilyName(entity.getFamilyName())).withRel("family"));

        //Add link to grant or revoke permission based on current permission type
        if(entity.getPermission().getPermissionType() == Permission.PERMISSIONLEVELZERO){
            userModel.add(linkTo(methodOn(UserController.class).grantPermission(entity.getId())).withRel("grant"));
        } else {
            userModel.add(linkTo(methodOn(UserController.class).revokePermission(entity.getId())).withRel("revoke"));
        }

        return userModel;
    }
}
