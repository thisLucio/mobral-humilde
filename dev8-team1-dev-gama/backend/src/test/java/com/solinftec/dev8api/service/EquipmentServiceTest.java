package com.solinftec.dev8api.service;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.repository.EquipmentRepository;
import com.solinftec.dev8api.util.EquipmentFactoryUtil;
import com.solinftec.dev8api.util.EquipmentPostRequestBodyFactoryUtil;
import com.solinftec.dev8api.util.EquipmentPutRequestBodyFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for EquipmentService")
@ExtendWith(SpringExtension.class)
class EquipmentServiceTest {
    private EquipmentFactoryUtil equipmentFactoryUtil;
    private EquipmentPostRequestBodyFactoryUtil equipmentPostRequestBodyFactoryUtil;
    private EquipmentPutRequestBodyFactoryUtil equipmentPutRequestBodyFactoryUtil;

    @InjectMocks
    EquipmentService equipmentService;

    @Mock
    EquipmentRepository equipmentRepository;

    @BeforeEach
    void init() {
        this.equipmentFactoryUtil = new EquipmentFactoryUtil();

        List<Equipment> equipmentList;
        equipmentList = List.of(equipmentFactoryUtil.createEquipmentSaved());

        BDDMockito.when(this.equipmentRepository.save(ArgumentMatchers.any(Equipment.class)))
                .thenReturn(equipmentFactoryUtil.createEquipmentSaved());
        BDDMockito.when(this.equipmentRepository.findAll())
                .thenReturn(equipmentList);
        BDDMockito.when(this.equipmentRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(equipmentFactoryUtil.createEquipmentSaved()));
        BDDMockito.doNothing().when(this.equipmentRepository).delete(ArgumentMatchers.any(Equipment.class));
    }

    @Test
    void saveEquipmentWhenSuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        Equipment equipmentSaved;
        equipmentSaved = this.equipmentService.save(this.equipmentPostRequestBodyFactoryUtil.createEquipmentToBeSaved());

        Assertions.assertThat(equipmentSaved).isNotNull();
        Assertions.assertThat(equipmentSaved.getId())
                .isNotNull()
                .isEqualTo(this.equipmentFactoryUtil.createEquipmentSaved().getId());
    }

    @Test
    void saveEquipmentWithoutColorWhenUnsuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> this.equipmentService.save(this.equipmentPostRequestBodyFactoryUtil.createEquipmentWithoutColor()));
    }

    @Test
    void saveEquipmentWithColorNameInvalidWhenUnsuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.equipmentService.save(this.equipmentPostRequestBodyFactoryUtil.createEquipmentWithColorNameInvalid()));
    }

    @Test
    void returnAllEquipmentsFoundWhenSuccessful() {
        String expectedName;
        expectedName = equipmentFactoryUtil.createEquipmentToBeSaved().getDescription();
        List<Equipment> equipmentReturned;
        equipmentReturned = this.equipmentService.findAll();

        Assertions.assertThat(equipmentReturned)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(equipmentReturned.get(0).getDescription())
                .isNotNull()
                .isEqualTo(expectedName);
    }

    @Test
    void returnEquipmentFoundByIdWhenSuccessful() {
        Long expectedId;
        expectedId = equipmentFactoryUtil.createEquipmentSaved().getId();
        Equipment equipmentFound;
        equipmentFound = this.equipmentService.findById(expectedId);

        Assertions.assertThat(equipmentFound)
                .isNotNull();
        Assertions.assertThat(equipmentFound.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    void replaceEquipmentWhenSuccessful() {
        equipmentPutRequestBodyFactoryUtil = new EquipmentPutRequestBodyFactoryUtil();
        Assertions.assertThatCode(() ->
                this.equipmentService.replace(
                        equipmentPutRequestBodyFactoryUtil.createEquipmentToBeSaved()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteUserWhenSuccessful() {

        Assertions.assertThatCode(() ->
                this.equipmentService.delete(this.equipmentFactoryUtil.createEquipmentSaved().getId()))
                .doesNotThrowAnyException();
    }
}