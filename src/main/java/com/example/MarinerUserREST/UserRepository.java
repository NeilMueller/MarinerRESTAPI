/**
 * Jpa repository for storing users
 * currently supports all() query and Family Name IS query
 * See https://www.baeldung.com/spring-data-derived-queries for adding more queries
 */

package com.example.MarinerUserREST;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<UserObject, Long> {
    List<UserObject> findByFamilyNameIs(String lastName);
    List<UserObject> findByEmail(String email);

}
