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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    private EquipmentService equipmentService;

    @Mock private EquipmentRepository equipmentRepository;

    private Equipment firstEquipment;
    private Equipment secondEquipment;

    @BeforeEach
    void initTest() {
        this.equipmentService = new EquipmentService(equipmentRepository);

        this.firstEquipment =
                Equipment.builder()
                        .id(1)
                        .description("Climate control")
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();

        this.secondEquipment =
                Equipment.builder()
                        .id(2)
                        .description("Automatic cruise control")
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();
    }

    @Test
    @DisplayName("Should found all equipments.")
    void foundAllEquipmentsTest() {

        final List<Equipment> equipments = new ArrayList<>();
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

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(firstEquipment));

        final Equipment foundEquipment =
                equipmentService.findById(1).orElseThrow(NotFoundException::new);

        verify(equipmentRepository, times(1)).findById(1);

        assertEquals(firstEquipment, foundEquipment);
    }

    @Test
    @DisplayName("Should not found an equipment by ID.")
    void notFoundEquipmentByIdTest() {

        when(equipmentRepository.findById(1)).thenReturn(Optional.empty());

        final Optional<Equipment> foundEquipment = equipmentService.findById(1);

        verify(equipmentRepository, times(1)).findById(1);

        assertEquals(Optional.empty(), foundEquipment);
    }

    @Test
    @DisplayName("Should save an equipment entity.")
    void createEquipmentTest() {

        when(equipmentRepository.save(firstEquipment)).thenReturn(firstEquipment);

        final Equipment foundEquipment = equipmentService.save(firstEquipment);

        verify(equipmentRepository, times(1)).save(firstEquipment);

        assertEquals(firstEquipment, foundEquipment);
    }

    @Test
    @DisplayName("Should update an equipment entity.")
    void updateEquipmentTest() {

        when(equipmentRepository.save(firstEquipment)).thenReturn(firstEquipment);

        equipmentService.save(firstEquipment);
        firstEquipment.setDescription("Dual zone climate control");
        equipmentService.update(firstEquipment, 1);

        verify(equipmentRepository, times(2)).save(firstEquipment);

        assertEquals("Dual zone climate control", firstEquipment.getDescription());
    }

    @Test
    @DisplayName("Should delete an equipment entity.")
    void deleteEquipmentTest() {

        equipmentService.save(firstEquipment);
        equipmentService.delete(firstEquipment.getId());

        verify(equipmentRepository, times(1)).deleteById(firstEquipment.getId());
    }
}
