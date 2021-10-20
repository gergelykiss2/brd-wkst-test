package service;

import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import com.gergelytamas.brdwksttest.exception.NotFoundException;
import com.gergelytamas.brdwksttest.repository.CarRepository;
import com.gergelytamas.brdwksttest.service.CarService;
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
class CarServiceTest {

    private CarService carService;

    @Mock private CarRepository carRepository;

    private Car firstCar;

    private Car secondCar;

    @BeforeEach
    void initTest() {
        this.carService = new CarService(carRepository);

        this.firstCar =
                Car.builder()
                        .id(1)
                        .licensePlate("ABC123")
                        .make("Ford")
                        .model("S-Max")
                        .fuelType(FuelType.DIESEL)
                        .carStatus(CarStatus.AVAILABLE)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();

        this.secondCar =
                Car.builder()
                        .id(1)
                        .licensePlate("DEF456")
                        .make("Opel")
                        .model("Astra")
                        .fuelType(FuelType.GASOLINE)
                        .carStatus(CarStatus.IN_SERVICE)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();
    }

    @Test
    @DisplayName("Should found all cars.")
    void foundAllCarsTest() {

        final List<Car> cars = new ArrayList<>();
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
    void foundCarByIdTest() throws NotFoundException {

        when(carRepository.findById(1)).thenReturn(Optional.of(firstCar));

        final Car car = carService.findById(1).orElseThrow(NotFoundException::new);

        verify(carRepository, times(1)).findById(1);

        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Ford", car.getMake());
        assertEquals("S-Max", car.getModel());
        assertEquals(FuelType.DIESEL, car.getFuelType());
        assertEquals(CarStatus.AVAILABLE, car.getCarStatus());
    }

    @Test
    @DisplayName("Should not found a car by ID.")
    void notFoundCarByID() {

        when(carRepository.findById(1)).thenReturn(Optional.empty());

        final Optional<Car> foundCar = carService.findById(1);

        verify(carRepository, times(1)).findById(1);

        assertEquals(Optional.empty(), foundCar);
    }

    // TODO: findAllByEquipment

    @Test
    @DisplayName("Should found all available cars.")
    void foundAllCarsByAvailability() {

        final List<Car> cars = new ArrayList<>();
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

        when(carRepository.save(firstCar)).thenReturn(firstCar);

        final Car foundCar = carService.save(firstCar);

        verify(carRepository, times(1)).save(firstCar);

        assertEquals(firstCar, foundCar);
    }

    @Test
    @DisplayName("Should update a car entity.")
    void updateCarTest() {

        when(carRepository.save(firstCar)).thenReturn(firstCar);

        carService.save(firstCar);
        firstCar.setLicensePlate("DEF456");
        carService.update(firstCar, 1);

        verify(carRepository, times(2)).save(firstCar);

        assertEquals("DEF456", firstCar.getLicensePlate());
        assertEquals("Ford", firstCar.getMake());
        assertEquals("S-Max", firstCar.getModel());
        assertEquals(FuelType.DIESEL, firstCar.getFuelType());
        assertEquals(CarStatus.AVAILABLE, firstCar.getCarStatus());
    }

    @Test
    @DisplayName("Should delete a car entity.")
    void deleteCarTest() {

        carService.save(firstCar);
        carService.delete(firstCar.getId());

        verify(carRepository, times(1)).deleteById(firstCar.getId());
    }
}
