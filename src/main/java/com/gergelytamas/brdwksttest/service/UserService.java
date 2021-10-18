package com.gergelytamas.brdwksttest.service;

import com.gergelytamas.brdwksttest.domain.User;
import com.gergelytamas.brdwksttest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService extends BaseServiceImpl<User, UserRepository> {

    public UserService(final UserRepository repository) {
        super(repository);
    }

    public Optional<User> findByEmail(final String email) {
        return this.repository.findByEmail(email);
    }

    public Optional<User> findByBirthDate(final Date birthDate) {
        return this.repository.findByBirthDate(birthDate);
    }
}
