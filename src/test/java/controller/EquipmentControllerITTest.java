package controller;

import com.gergelytamas.brdwksttest.BrdWkstTestApplication;
import com.gergelytamas.brdwksttest.domain.Equipment;
import com.gergelytamas.brdwksttest.repository.EquipmentRepository;
import com.gergelytamas.brdwksttest.service.dto.EquipmentDTO;
import com.gergelytamas.brdwksttest.service.mapper.EquipmentMapper;
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
class EquipmentControllerITTest {

    @Autowired private EntityManager em;

    @Autowired private MockMvc restEquipmentControllerMockMvc;

    @Autowired private EquipmentRepository equipmentRepository;

    @Autowired private EquipmentMapper equipmentMapper;

    private Equipment equipment;

    public static Equipment createEntity(final EntityManager em) {
        return new Equipment(1L, "Climate control", new HashSet<>());
    }

    @BeforeEach
    void initTest() {
        equipment = createEntity(em);
    }

    @Test
    @Transactional
    void createEquipment() throws Exception {
        // Initialize database
        final int equipmentDatabaseSizeBeforeCreate = equipmentRepository.findAll().size();

        final EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // Create the equipment entity
        restEquipmentControllerMockMvc
                .perform(
                        post("/api/equipments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
                .andExpect(status().isCreated());

        final List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(equipmentDatabaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        // Initialize database
        final int equipmentDatabaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Set the field null
        equipment.setDescription(null);
        final EquipmentDTO equipmentDTO = equipmentMapper.toDto(equipment);

        // Create the equipment entity, which fails
        restEquipmentControllerMockMvc
                .perform(
                        post("/api/equipments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
                .andExpect(status().isBadRequest());

        final List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(equipmentDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateEquipment() throws Exception {
        // Initialize database
        equipmentRepository.saveAndFlush(equipment);

        final int equipmentDatabaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment entity
        final Equipment updatedEquipment = equipmentRepository.findById(equipment.getId()).get();
        em.detach(updatedEquipment);

        updatedEquipment.setDescription("Dual zone climate control");

        final EquipmentDTO equipmentDTO = equipmentMapper.toDto(updatedEquipment);

        // Update the equipment entity
        restEquipmentControllerMockMvc
                .perform(
                        put("/api/equipments/{id}", updatedEquipment.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(equipmentDTO)))
                .andExpect(status().isOk());

        final List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(equipmentDatabaseSizeBeforeUpdate);
        final Equipment testEquipment = equipmentList.get(0);
        assertThat(testEquipment.getDescription()).isEqualTo("Dual zone climate control");
    }

    @Test
    @Transactional
    void getAllEquipments() throws Exception {
        // Initialize database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipment entities
        restEquipmentControllerMockMvc
                .perform(get("/api/equipments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
                .andExpect(
                        jsonPath("$.[*].description").value(hasItem(equipment.getDescription())));
    }

    @Test
    @Transactional
    void getEquipment() throws Exception {
        // Initialize database
        equipmentRepository.saveAndFlush(equipment);

        // Get the equipment entity
        restEquipmentControllerMockMvc
                .perform(get("/api/equipments/{id}", equipment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(equipment.getId().intValue()))
                .andExpect(jsonPath("$.description").value(equipment.getDescription()));
    }

    @Test
    @Transactional
    void deleteEquipment() throws Exception {
        // Initialize database
        equipmentRepository.saveAndFlush(equipment);

        final int equipmentDatabaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Delete the equipment entity
        restEquipmentControllerMockMvc
                .perform(
                        delete("/api/equipments/{id}", equipment.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(equipmentRepository.findAll()).hasSize(equipmentDatabaseSizeBeforeDelete - 1);
    }
}
