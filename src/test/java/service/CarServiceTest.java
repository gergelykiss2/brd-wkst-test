package service;

import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import com.gergelytamas.brdwksttest.repository.CarRepository;
import com.gergelytamas.brdwksttest.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    CarService carService;

    @Mock CarRepository carRepository;

    @BeforeEach
    void initTest() {
        this.carService = new CarService(carRepository);
    }

    @Test
    @DisplayName("Should get all cars.")
    void getAllCarsTest() {
        final List<Car> cars = new ArrayList<>();
        final Car firstCar =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());
        final Car secondCar =
                new Car(
                        2L,
                        "DEF456",
                        "Ford",
                        "Galaxy",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());

        cars.add(firstCar);
        cars.add(secondCar);

        when(carRepository.findAll()).thenReturn(cars);

        final List<Car> carList = carService.findAll();

        verify(carRepository, times(1)).findAll();

        Assertions.assertEquals(2, carList.size());
        Assertions.assertEquals(firstCar, carList.get(0));
        Assertions.assertEquals(secondCar, carList.get(1));
    }

    @Test
    @DisplayName("Should get car by ID.")
    void getCarByIdTest() throws InvalidKeyException {
        when(carRepository.findById(1L))
                .thenReturn(
                        Optional.of(
                                new Car(
                                        1L,
                                        "ABC123",
                                        "Ford",
                                        "S-Max",
                                        FuelType.DIESEL,
                                        CarStatus.AVAILABLE,
                                        new HashSet<>(),
                                        new HashSet<>())));

        final Car car = carService.findById(1L).orElseThrow(InvalidKeyException::new);

        verify(carRepository, times(1)).findById(1L);

        Assertions.assertEquals("ABC123", car.getLicensePlate());
        Assertions.assertEquals("Ford", car.getMake());
        Assertions.assertEquals("S-Max", car.getModel());
        Assertions.assertEquals(FuelType.DIESEL, car.getFuelType());
        Assertions.assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
    }

    // TODO: findAllByEquipment

    @Test
    @DisplayName("Should get all available cars.")
    void getAllCarsByAvailability() {
        final List<Car> cars = new ArrayList<>();
        final Car firstCar =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());
        final Car secondCar =
                new Car(
                        2L,
                        "DEF456",
                        "Ford",
                        "Galaxy",
                        FuelType.DIESEL,
                        CarStatus.IN_SERVICE,
                        new HashSet<>(),
                        new HashSet<>());

        cars.add(firstCar);
        cars.add(secondCar);

        when(carRepository.findAllAvailable()).thenReturn(cars.subList(0, 1));

        final List<Car> carList = carService.findAllAvailable();

        verify(carRepository, times(1)).findAllAvailable();

        Assertions.assertEquals(1, carList.size());
        Assertions.assertEquals(firstCar, carList.get(0));
    }

    @Test
    @DisplayName("Should get all cars by make.")
    void getAllCarsByMake() {
        final List<Car> cars = new ArrayList<>();
        final Car firstCar =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());
        final Car secondCar =
                new Car(
                        2L,
                        "DEF456",
                        "Opel",
                        "Astra",
                        FuelType.DIESEL,
                        CarStatus.IN_SERVICE,
                        new HashSet<>(),
                        new HashSet<>());

        cars.add(firstCar);
        cars.add(secondCar);

        when(carRepository.findAllByMake("Ford")).thenReturn(cars.subList(0, 1));

        final List<Car> carList = carService.findAllByMake("Ford");

        verify(carRepository, times(1)).findAllByMake("Ford");

        Assertions.assertEquals(1, carList.size());
        Assertions.assertEquals(firstCar, carList.get(0));
    }

    @Test
    @DisplayName("Should save a car entity.")
    void createCarTest() {
        final Car car =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());

        carService.save(car);

        verify(carRepository, times(1)).save(car);
    }

    @Test
    @DisplayName("Should update a car entity.")
    void updateCarTest() {
        final Car car =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());

        carService.save(car);
        car.setLicensePlate("DEF456");
        carService.update(car, 1L);

        verify(carRepository, times(2)).save(car);

        Assertions.assertEquals("DEF456", car.getLicensePlate());
        Assertions.assertEquals("Ford", car.getMake());
        Assertions.assertEquals("S-Max", car.getModel());
        Assertions.assertEquals(FuelType.DIESEL, car.getFuelType());
        Assertions.assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
    }

    @Test
    @DisplayName("Should delete a car entity.")
    void deleteCarTest() {
        final Car car =
                new Car(
                        1L,
                        "ABC123",
                        "Ford",
                        "S-Max",
                        FuelType.DIESEL,
                        CarStatus.AVAILABLE,
                        new HashSet<>(),
                        new HashSet<>());

        carService.save(car);
        carService.delete(car.getId());

        verify(carRepository, times(1)).deleteById(car.getId());
    }
}
