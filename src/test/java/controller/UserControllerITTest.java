package controller;

import com.gergelytamas.brdwksttest.BrdWkstTestApplication;
import com.gergelytamas.brdwksttest.domain.User;
import com.gergelytamas.brdwksttest.repository.UserRepository;
import com.gergelytamas.brdwksttest.service.dto.UserDTO;
import com.gergelytamas.brdwksttest.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import static controller.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BrdWkstTestApplication.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class UserControllerITTest {

    @Autowired
    private EntityManager em;

    @Autowired private MockMvc restUserControllerMockMvc;

    @Autowired private UserRepository userRepository;

    @Autowired private UserMapper userMapper;

    private User user;

    public static User createEntity(final EntityManager em) {
        return new User(
                1L,
                "Test",
                "User 1",
                "test.user2@example.com",
                ZonedDateTime.now(),
                "Birth City",
                "123456789",
                new HashSet<>(),
                true);
    }

    @BeforeEach
    void initTest() {
        user = createEntity(em);
    }

    @Test
    @Transactional
    void createUser() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isCreated());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        // Set the field null
        user.setFirstName(null);
        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity, which fails
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        // Set the field null
        user.setLastName(null);
        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity, which fails
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        // Set the field null
        user.setEmail(null);
        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity, which fails
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        // Set the field null
        user.setBirthDate(null);
        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity, which fails
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        // Initialize database
        final int userDatabaseSizeBeforeCreate = userRepository.findAll().size();

        // Set the field null
        user.setActive(null);
        final UserDTO userDTO = userMapper.toDto(user);

        // Create the user entity, which fails
        restUserControllerMockMvc
                .perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateUser() throws Exception {
        // Initialize database
        userRepository.saveAndFlush(user);

        final int userDatabaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user entity
        final User updatedUser = userRepository.findById(user.getId()).get();
        em.detach(updatedUser);

        updatedUser.setLastName("User 2");

        final UserDTO userDTO = userMapper.toDto(updatedUser);

        // Update the user entity
        restUserControllerMockMvc
                .perform(
                        put("/api/users/{id}", updatedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk());

        final List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(userDatabaseSizeBeforeUpdate);
        final User testUser = userList.get(0);
        assertThat(testUser.getLastName()).isEqualTo("User 2");
    }

    @Test
    @Transactional
    void getAllUsers() throws Exception {
        // Initialize database
        userRepository.saveAndFlush(user);

        // Get all the user entities
        restUserControllerMockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(user.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(user.getFirstName())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(user.getLastName())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(user.getEmail())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(user.getBirthDate()))))
                .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(user.getBirthPlace())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(user.getPhoneNumber())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(user.getActive())));
    }

    @Test
    @Transactional
    void getUser() throws Exception {
        // Initialize database
        userRepository.saveAndFlush(user);

        // Get the user entity
        restUserControllerMockMvc
                .perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(user.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.birthDate").value(sameInstant(user.getBirthDate())))
                .andExpect(jsonPath("$.birthPlace").value(user.getBirthPlace()))
                .andExpect(jsonPath("$.phoneNumber").value(user.getPhoneNumber()))
                .andExpect(jsonPath("$.active").value(user.getActive()));
    }

    @Test
    @Transactional
    void deleteUser() throws Exception {
        // Initialize database
        userRepository.saveAndFlush(user);

        final int userDatabaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user entity
        restUserControllerMockMvc
                .perform(
                        delete("/api/users/{id}", user.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findAll()).hasSize(userDatabaseSizeBeforeDelete - 1);
    }
}
