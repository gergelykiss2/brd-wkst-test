package service;

import com.gergelytamas.brdwksttest.domain.Equipment;
import com.gergelytamas.brdwksttest.exception.NotFoundException;
import com.gergelytamas.brdwksttest.repository.EquipmentRepository;
import com.gergelytamas.brdwksttest.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    EquipmentService equipmentService;

    @Mock EquipmentRepository equipmentRepository;

    @BeforeEach
    void initTest() {
        this.equipmentService = new EquipmentService(equipmentRepository);
    }

    @Test
    @DisplayName("Should found all equipments.")
    void foundAllEquipmentsTest() {

        final List<Equipment> equipments = new ArrayList<>();
        final Equipment firstEquipment = new Equipment(1L, "Rain sensor", new HashSet<>());
        final Equipment secondEquipment =
                new Equipment(2L, "Automatic cruise control", new HashSet<>());

        equipments.add(firstEquipment);
        equipments.add(secondEquipment);

        when(equipmentRepository.findAll()).thenReturn(equipments);

        final List<Equipment> equipmentList = equipmentService.findAll();

        verify(equipmentRepository, times(1)).findAll();

        assertEquals(2, equipmentList.size());
        assertEquals(firstEquipment, equipmentList.get(0));
        assertEquals(secondEquipment, equipmentList.get(1));
    }

    @Test
    @DisplayName("Should found an equipment by ID.")
    void foundEquipmentByIdTest() {

        final Equipment equipment = new Equipment(1L, "Climate control", new HashSet<>());

        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));

        final Equipment foundEquipment =
                equipmentService.findById(1L).orElseThrow(NotFoundException::new);

        verify(equipmentRepository, times(1)).findById(1L);

        assertEquals(equipment, foundEquipment);
    }

    @Test
    @DisplayName("Should not found an equipment by ID.")
    void notFoundEquipmentByIdTest() {

        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

        final Optional<Equipment> foundEquipment = equipmentService.findById(1L);

        verify(equipmentRepository, times(1)).findById(1L);

        assertEquals(Optional.empty(), foundEquipment);
    }

    @Test
    @DisplayName("Should save an equipment entity.")
    void createEquipmentTest() {

        final Equipment equipment = new Equipment(1L, "Navigation", new HashSet<>());

        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        final Equipment foundEquipment = equipmentService.save(equipment);

        verify(equipmentRepository, times(1)).save(equipment);

        assertEquals(equipment, foundEquipment);
    }

    @Test
    @DisplayName("Should update an equipment entity.")
    void updateEquipmentTest() {

        final Equipment equipment = new Equipment(1L, "Climate control", new HashSet<>());

        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        equipmentService.save(equipment);
        equipment.setDescription("Dual zone climate control");
        equipmentService.update(equipment, 1L);

        verify(equipmentRepository, times(2)).save(equipment);

        assertEquals("Dual zone climate control", equipment.getDescription());
    }

    @Test
    @DisplayName("Should delete an equipment entity.")
    void deleteEquipmentTest() {

        final Equipment equipment = new Equipment(1L, "Navigation", new HashSet<>());

        equipmentService.save(equipment);
        equipmentService.delete(equipment.getId());

        verify(equipmentRepository, times(1)).deleteById(equipment.getId());
    }
}
