package service;

import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.domain.Equipment;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import com.gergelytamas.brdwksttest.repository.CarRepository;
import com.gergelytamas.brdwksttest.service.CarService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Should found all cars.")
    void foundAllCarsTest() {

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

        assertEquals(2, carList.size());
        assertEquals(firstCar, carList.get(0));
        assertEquals(secondCar, carList.get(1));
    }

    @Test
    @DisplayName("Should found car by ID.")
    void foundCarByIdTest() throws InvalidKeyException {
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

        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Ford", car.getMake());
        assertEquals("S-Max", car.getModel());
        assertEquals(FuelType.DIESEL, car.getFuelType());
        assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
    }

    @Test
    @DisplayName("Should not found a car by ID.")
    void notFoundCarByID() {

        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        final Optional<Car> foundCar = carService.findById(1L);

        verify(carRepository, times(1)).findById(1L);

        assertEquals(Optional.empty(), foundCar);

    }

    // TODO: findAllByEquipment

    @Test
    @DisplayName("Should found all available cars.")
    void foundAllCarsByAvailability() {
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

        assertEquals(1, carList.size());
        assertEquals(firstCar, carList.get(0));
    }

    @Test
    @DisplayName("Should found all cars by make.")
    void foundAllCarsByMake() {
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

        assertEquals(1, carList.size());
        assertEquals(firstCar, carList.get(0));
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

        when(carRepository.save(car)).thenReturn(car);

        final Car foundCar = carService.save(car);

        verify(carRepository, times(1)).save(car);

        assertEquals(car, foundCar);
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

        when(carRepository.save(car)).thenReturn(car);

        carService.save(car);
        car.setLicensePlate("DEF456");
        carService.update(car, 1L);

        verify(carRepository, times(2)).save(car);

        assertEquals("DEF456", car.getLicensePlate());
        assertEquals("Ford", car.getMake());
        assertEquals("S-Max", car.getModel());
        assertEquals(FuelType.DIESEL, car.getFuelType());
        assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
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
