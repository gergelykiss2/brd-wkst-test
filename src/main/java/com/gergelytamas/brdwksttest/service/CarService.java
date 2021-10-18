package com.gergelytamas.brdwksttest.service;

import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CarService extends BaseServiceImpl<Car, CarRepository> {

    public CarService(final CarRepository repository) {
        super(repository);
    }

    public List<Car> findAllByEquipment(final Collection<String> equipment) {
        //        return this.repository.findAllByEquipment(equipment);
        return null;
    }

    public List<Car> findAllAvailable() {
        return this.repository.findAllAvailable();
    }

    public List<Car> findAllByMake(final String make) {
        return this.repository.findAllByMake(make);
    }
}
