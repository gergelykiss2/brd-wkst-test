package com.gergelytamas.brdwksttest.service;

import com.gergelytamas.brdwksttest.domain.Equipment;
import com.gergelytamas.brdwksttest.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService extends BaseServiceImpl<Equipment, EquipmentRepository> {

    public EquipmentService(final EquipmentRepository repository) {
        super(repository);
    }
}
