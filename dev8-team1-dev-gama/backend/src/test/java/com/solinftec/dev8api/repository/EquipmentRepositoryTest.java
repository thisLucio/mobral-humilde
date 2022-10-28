package com.solinftec.dev8api.repository;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.util.EquipmentFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for EquipmentRepository")
@ExtendWith(SpringExtension.class)
class EquipmentRepositoryTest {
    private EquipmentFactoryUtil equipmentFactoryUtil;

    @Autowired
    EquipmentRepository equipmentRepository;

    @BeforeEach
    void init() {
        equipmentFactoryUtil = new EquipmentFactoryUtil();
    }

    @Test
    void saveEquipmentWhenSuccessful() {
        Equipment equipmentToBeSaved, equipmentSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentToBeSaved();
        equipmentSaved = this.equipmentRepository.save(equipmentToBeSaved);

        Assertions.assertThat(equipmentSaved).isNotNull();
        Assertions.assertThat(equipmentSaved.getId()).isNotNull();
        Assertions.assertThat(equipmentSaved.getDescription())
                .isNotNull()
                .isEqualTo(equipmentToBeSaved.getDescription());
    }

    @Test
    void saveEquipmentWithSmallerLatitudeWhenUnsuccessful() {
        Equipment equipmentToBeSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentWithSmallerLatitude();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.equipmentRepository.save(equipmentToBeSaved));
    }

    @Test
    void saveEquipmentWithLargerLatitudeWhenUnsuccessful() {
        Equipment equipmentToBeSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentWithLargerLatitude();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.equipmentRepository.save(equipmentToBeSaved));
    }

    @Test
    void saveEquipmentWithSmallerLongitudeWhenUnsuccessful() {
        Equipment equipmentToBeSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentWithSmallerLongitude();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.equipmentRepository.save(equipmentToBeSaved));
    }

    @Test
    void saveEquipmentWithLargerLongitudeWhenUnsuccessful() {
        Equipment equipmentToBeSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentWithLargerLongitude();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.equipmentRepository.save(equipmentToBeSaved));
    }

    @Test
    void saveEquipmentWithSizeOversizedColorWhenUnsuccessful() {
        Equipment equipmentToBeSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentWithSizeOversizedColor();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.equipmentRepository.save(equipmentToBeSaved));
    }

    @Test
    void updateEquipmentWhenSuccessful() {
        Equipment equipmentToBeSaved, equipmentSaved, equipmentUpdated;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentToBeSaved();
        equipmentSaved = this.equipmentRepository.save(equipmentToBeSaved);
        equipmentSaved = equipmentFactoryUtil.changeEquipment(equipmentSaved);
        equipmentUpdated = this.equipmentRepository.save(equipmentSaved);

        Assertions.assertThat(equipmentUpdated).isNotNull();
        Assertions.assertThat(equipmentUpdated.getId()).isNotNull();
        Assertions.assertThat(equipmentUpdated.getDescription())
                .isNotNull()
                .isEqualTo(equipmentSaved.getDescription());
    }

    @Test
    void deleteEquipmentWhenSuccessful() {
        Equipment equipmentToBeSaved, equipmentSaved;
        equipmentToBeSaved = equipmentFactoryUtil.createEquipmentToBeSaved();
        equipmentSaved = this.equipmentRepository.save(equipmentToBeSaved);
        this.equipmentRepository.delete(equipmentSaved);

        Optional<Equipment> equipmentLoaded;
        equipmentLoaded = this.equipmentRepository.findById(equipmentSaved.getId());
        Assertions.assertThat(equipmentLoaded).isEmpty();
    }
}