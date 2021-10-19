package service;

import com.gergelytamas.brdwksttest.domain.User;
import com.gergelytamas.brdwksttest.exception.NotFoundException;
import com.gergelytamas.brdwksttest.repository.UserRepository;
import com.gergelytamas.brdwksttest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    @Mock UserRepository userRepository;

    @BeforeEach
    void initTest() {
        this.userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Should found all users.")
    void foundAllUsersTest() {

        final List<User> users = new ArrayList<>();
        final User firstUser =
                new User(
                        1L,
                        "Test",
                        "User 1",
                        "test.user1@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);
        final User secondUser =
                new User(
                        2L,
                        "Test",
                        "User 2",
                        "test.user2@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);

//        users.add(firstUser);
        users.add(secondUser);

        when(userRepository.findAll()).thenReturn(users);

        final List<User> userList = userService.findAll();

        verify(userRepository, times(1)).findAll();

        assertEquals(2, userList.size());
        assertEquals(firstUser, userList.get(0));
        assertEquals(secondUser, userList.get(1));
    }

    @Test
    @DisplayName("Should found an user by ID.")
    void foundUserByIdTest() throws NotFoundException {

        final User user =
                new User(
                        1L,
                        "Test",
                        "User 1",
                        "test.user1@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        final User foundUser = userService.findById(1L).orElseThrow(NotFoundException::new);

        verify(userRepository, times(1)).findById(1L);

        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Should not found an user by ID.")
    void notFoundUserByIdTest() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        final Optional<User> foundUser = userService.findById(1L);

        verify(userRepository, times(1)).findById(1L);

        assertEquals(Optional.empty(), foundUser);
    }

    @Test
    @DisplayName("Should save an user entity.")
    void createUserTest() {

        final User user =
                new User(
                        1L,
                        "Test",
                        "User 1",
                        "test.user1@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);

        when(userRepository.save(user)).thenReturn(user);

        final User savedUser = userService.save(user);

        verify(userRepository, times(1)).save(user);

        assertEquals(user, savedUser);
    }

    @Test
    @DisplayName("Should update an user entity.")
    void updateUserTest() {

        final User user =
                new User(
                        1L,
                        "Test",
                        "User 1",
                        "test.user1@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);

        when(userRepository.save(user)).thenReturn(user);

        userService.save(user);
        user.setLastName("User 2");
        userService.update(user, 1L);

        verify(userRepository, times(2)).save(user);

        assertEquals("User 2", user.getLastName());
    }

    @Test
    @DisplayName("Should delete an user entity.")
    void deleteUserTest() {

        final User user =
                new User(
                        1L,
                        "Test",
                        "User 1",
                        "test.user1@example.com",
                        new Date(System.currentTimeMillis()),
                        null,
                        null,
                        new HashSet<>(),
                        true);

        userService.save(user);
        userService.delete(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
