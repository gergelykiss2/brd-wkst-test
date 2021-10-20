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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock private UserRepository userRepository;

    private User firstUser;
    private User secondUser;

    @BeforeEach
    void initTest() {
        this.userService = new UserService(userRepository);

        this.firstUser =
                User.builder()
                        .id(1)
                        .firstName("Test")
                        .lastName("User 1")
                        .email("test.user1@example.com")
                        .birthDate(ZonedDateTime.now())
                        .birthPlace("Birth City")
                        .phoneNumber("123456789")
                        .active(true)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();

        this.secondUser =
                User.builder()
                        .id(2)
                        .firstName("Test")
                        .lastName("User 2")
                        .email("test.user2@example.com")
                        .birthDate(ZonedDateTime.now())
                        .birthPlace("Birth City")
                        .phoneNumber("123456789")
                        .active(true)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();
    }

    @Test
    @DisplayName("Should found all users.")
    void foundAllUsersTest() {

        final List<User> users = new ArrayList<>();
        users.add(firstUser);
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

        when(userRepository.findById(1)).thenReturn(Optional.of(firstUser));

        final User foundUser = userService.findById(1).orElseThrow(NotFoundException::new);

        verify(userRepository, times(1)).findById(1);

        assertEquals(firstUser, foundUser);
    }

    @Test
    @DisplayName("Should not found an user by ID.")
    void notFoundUserByIdTest() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        final Optional<User> foundUser = userService.findById(1);

        verify(userRepository, times(1)).findById(1);

        assertEquals(Optional.empty(), foundUser);
    }

    @Test
    @DisplayName("Should save an user entity.")
    void createUserTest() {

        when(userRepository.save(firstUser)).thenReturn(firstUser);

        final User savedUser = userService.save(firstUser);

        verify(userRepository, times(1)).save(firstUser);

        assertEquals(firstUser, savedUser);
    }

    @Test
    @DisplayName("Should update an user entity.")
    void updateUserTest() {

        when(userRepository.save(firstUser)).thenReturn(firstUser);

        userService.save(firstUser);
        firstUser.setLastName("User 2");
        userService.update(firstUser, 1);

        verify(userRepository, times(2)).save(firstUser);

        assertEquals("User 2", firstUser.getLastName());
    }

    @Test
    @DisplayName("Should delete an user entity.")
    void deleteUserTest() {

        userService.save(firstUser);
        userService.delete(firstUser.getId());

        verify(userRepository, times(1)).deleteById(firstUser.getId());
    }
}
