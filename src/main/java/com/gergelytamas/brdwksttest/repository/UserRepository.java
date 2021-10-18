package com.gergelytamas.brdwksttest.repository;

import com.gergelytamas.brdwksttest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where user.email =: email")
    Optional<User> findByEmail(@Param("email") final String email);

    @Query("select user from User user where user.birthDate =: birth_date")
    Optional<User> findByBirthDate(@Param("birth_date") final Date birthDate);
}
