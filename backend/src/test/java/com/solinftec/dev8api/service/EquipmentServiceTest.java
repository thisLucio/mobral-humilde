package com.solinftec.dev8api.service;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.kafka.KafkaService;
import com.solinftec.dev8api.repository.EquipmentRepository;
import com.solinftec.dev8api.requests.EquipmentPostRequestBody;
import com.solinftec.dev8api.requests.EquipmentPutRequestBody;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for EquipmentService")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("development")
class EquipmentServiceTest {
    private EquipmentFactoryUtil equipmentFactoryUtil;
    private EquipmentPostRequestBodyFactoryUtil equipmentPostRequestBodyFactoryUtil;
    private EquipmentPutRequestBodyFactoryUtil equipmentPutRequestBodyFactoryUtil;

    @InjectMocks
    EquipmentService equipmentService;

    @Mock
    EquipmentRepository equipmentRepository;

    @Mock
    KafkaService kafkaService;

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
        EquipmentPostRequestBody equipmentToBeSaved;
        equipmentToBeSaved = this.equipmentPostRequestBodyFactoryUtil.createEquipmentWithoutColor();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.equipmentService.save(equipmentToBeSaved));
    }

    @Test
    void saveEquipmentWithColorNameInvalidWhenUnsuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        EquipmentPostRequestBody equipmentToBeSaved;
        equipmentToBeSaved = this.equipmentPostRequestBodyFactoryUtil.createEquipmentWithColorNameInvalid();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.equipmentService.save(equipmentToBeSaved));
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
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        EquipmentPostRequestBody equipmentToBeSaved;
        equipmentToBeSaved = this.equipmentPostRequestBodyFactoryUtil.createEquipmentToBeSaved();
        this.equipmentService.save(equipmentToBeSaved);

        equipmentPutRequestBodyFactoryUtil = new EquipmentPutRequestBodyFactoryUtil();
        EquipmentPutRequestBody equipmentToBeReplaced;
        equipmentToBeReplaced = equipmentPutRequestBodyFactoryUtil.createEquipmentToBeSaved();
        Assertions.assertThatCode(() ->
                        this.equipmentService.replace(equipmentToBeReplaced))
                .doesNotThrowAnyException();
    }

    @Test
    void replaceEquipmentWithoutColorWhenUnsuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        EquipmentPostRequestBody equipmentToBeSaved;
        equipmentToBeSaved = this.equipmentPostRequestBodyFactoryUtil.createEquipmentToBeSaved();
        this.equipmentService.save(equipmentToBeSaved);

        equipmentPutRequestBodyFactoryUtil = new EquipmentPutRequestBodyFactoryUtil();
        EquipmentPutRequestBody equipmentToBeReplaced;
        equipmentToBeReplaced = this.equipmentPutRequestBodyFactoryUtil.createEquipmentWithoutColor();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.equipmentService.replace(equipmentToBeReplaced));
    }

    @Test
    void replaceEquipmentWithColorNameInvalidWhenUnsuccessful() {
        this.equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        EquipmentPostRequestBody equipmentToBeSaved;
        equipmentToBeSaved = this.equipmentPostRequestBodyFactoryUtil.createEquipmentToBeSaved();
        this.equipmentService.save(equipmentToBeSaved);

        equipmentPutRequestBodyFactoryUtil = new EquipmentPutRequestBodyFactoryUtil();
        EquipmentPutRequestBody equipmentToBeReplaced;
        equipmentToBeReplaced = this.equipmentPutRequestBodyFactoryUtil.createEquipmentWithColorNameInvalid();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.equipmentService.replace(equipmentToBeReplaced));
    }

    @Test
    void deleteUserWhenSuccessful() {

        Assertions.assertThatCode(() ->
                        this.equipmentService.delete(this.equipmentFactoryUtil.createEquipmentSaved().getId()))
                .doesNotThrowAnyException();
    }
}