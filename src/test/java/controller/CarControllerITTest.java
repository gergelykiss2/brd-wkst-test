package controller;

import com.gergelytamas.brdwksttest.BrdWkstTestApplication;
import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import com.gergelytamas.brdwksttest.repository.CarRepository;
import com.gergelytamas.brdwksttest.service.dto.CarDTO;
import com.gergelytamas.brdwksttest.service.mapper.CarMapper;
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
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BrdWkstTestApplication.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class CarControllerITTest {

    @Autowired private EntityManager em;

    @Autowired private MockMvc restCarControllerMockMvc;

    @Autowired private CarRepository carRepository;

    @Autowired private CarMapper carMapper;

    private Car car;

    public static Car createEntity(final EntityManager em) {
        return new Car(
                1L,
                "ABC123",
                "Ford",
                "S-Max",
                FuelType.DIESEL,
                CarStatus.AVAILABLE,
                new HashSet<>(),
                new HashSet<>());
    }

    @BeforeEach
    void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    void createCar() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isCreated());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void checkLicensePlateIsRequired() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        // Set the field null
        car.setLicensePlate(null);
        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity, which fails
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isBadRequest());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMakeIsRequired() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        // Set the field null
        car.setMake(null);
        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity, which fails
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isBadRequest());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModelIsRequired() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        // Set the field null
        car.setModel(null);
        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity, which fails
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isBadRequest());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFuelTypeIsRequired() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        // Set the field null
        car.setFuelType(null);
        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity, which fails
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isBadRequest());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCarStatusIsRequired() throws Exception {
        // Initialize database
        final int carDatabaseSizeBeforeCreate = carRepository.findAll().size();

        // Set the field null
        car.setCarStatus(null);
        final CarDTO carDTO = carMapper.toDto(car);

        // Create the car entity, which fails
        restCarControllerMockMvc
                .perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isBadRequest());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateCar() throws Exception {
        // Initialize database
        carRepository.saveAndFlush(car);

        final int carDatabaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car entity
        final Car updatedCar = carRepository.findById(car.getId()).get();
        em.detach(updatedCar);

        updatedCar.setLicensePlate("DEF456");

        final CarDTO carDTO = carMapper.toDto(updatedCar);

        // Update the car entity
        restCarControllerMockMvc
                .perform(
                        put("/api/cars/{id}", updatedCar.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(carDTO)))
                .andExpect(status().isOk());

        final List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(carDatabaseSizeBeforeUpdate);
        final Car testCar = carList.get(0);
        assertThat(testCar.getLicensePlate()).isEqualTo("DEF456");
        assertThat(testCar.getMake()).isEqualTo(car.getMake());
        assertThat(testCar.getModel()).isEqualTo(car.getModel());
        assertThat(testCar.getFuelType()).isEqualTo(car.getFuelType());
        assertThat(testCar.getCarStatus()).isEqualTo(car.getCarStatus());
    }

    @Test
    @Transactional
    void getAllCars() throws Exception {
        // Initialize database
        carRepository.saveAndFlush(car);

        // Get all the car entities
        restCarControllerMockMvc
                .perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
                .andExpect(jsonPath("$.[*].licensePlate").value(hasItem(car.getLicensePlate())))
                .andExpect(jsonPath("$.[*].make").value(hasItem(car.getMake())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(car.getModel())))
                .andExpect(jsonPath("$.[*].fuelType").value(hasItem(car.getFuelType().toString())))
                .andExpect(
                        jsonPath("$.[*].carStatus").value(hasItem(car.getCarStatus().toString())));
    }

    @Test
    @Transactional
    void getCar() throws Exception {
        // Initialize database
        carRepository.saveAndFlush(car);

        // Get the car entity
        restCarControllerMockMvc
                .perform(get("/api/cars/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(car.getId().intValue()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.fuelType").value(car.getFuelType().toString()))
                .andExpect(jsonPath("$.carStatus").value(car.getCarStatus().toString()));
    }

    @Test
    @Transactional
    void deleteCar() throws Exception {
        // Initialize database
        carRepository.saveAndFlush(car);

        final int carDatabaseSizeBeforeDelete = carRepository.findAll().size();

        // Delete the car entity
        restCarControllerMockMvc
                .perform(delete("/api/cars/{id}", car.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(carRepository.findAll()).hasSize(carDatabaseSizeBeforeDelete - 1);
    }
}
