package com.example.MarinerUserREST;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface UserRepository extends JpaRepository<UserObject, Long> {
    List<UserObject> findByFamilyNameIs(String lastName);
}
