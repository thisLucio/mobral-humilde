package com.solinftec.dev8api.resource;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.requests.EquipmentPostRequestBody;
import com.solinftec.dev8api.requests.EquipmentPutRequestBody;
import com.solinftec.dev8api.service.EquipmentService;
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

@DataJpaTest
@DisplayName("Tests for EquipmentResource")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("development")
class EquipmentResourceTest {
    private EquipmentFactoryUtil equipmentFactoryUtil;
    private EquipmentPostRequestBodyFactoryUtil equipmentPostRequestBodyFactoryUtil;
    private EquipmentPutRequestBodyFactoryUtil equipmentPutRequestBodyFactoryUtil;

    @InjectMocks
    EquipmentResource equipmentResource;

    @Mock
    EquipmentService equipmentService;

    @BeforeEach
    void init() {
        equipmentFactoryUtil = new EquipmentFactoryUtil();

        List<Equipment> equipmentList;
        equipmentList = List.of(equipmentFactoryUtil.createEquipmentSaved());

        BDDMockito.when(this.equipmentService.save(ArgumentMatchers.any(EquipmentPostRequestBody.class)))
                .thenReturn(equipmentFactoryUtil.createEquipmentSaved());
        BDDMockito.when(this.equipmentService.findAll())
                .thenReturn(equipmentList);
        BDDMockito.when(this.equipmentService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(equipmentFactoryUtil.createEquipmentSaved());
        BDDMockito.doNothing().when(this.equipmentService).delete(ArgumentMatchers.anyLong());
        BDDMockito.doNothing().when(this.equipmentService).replace(ArgumentMatchers.any(EquipmentPutRequestBody.class));
    }

    @Test
    void saveEquipmentWhenSuccessful() {
        equipmentPostRequestBodyFactoryUtil = new EquipmentPostRequestBodyFactoryUtil();
        Equipment equipmentSaved;
        equipmentSaved = this.equipmentResource.save(equipmentPostRequestBodyFactoryUtil.createEquipmentToBeSaved()).getBody();

        Assertions.assertThat(equipmentSaved).isNotNull();
        Assertions.assertThat(equipmentSaved.getId())
                .isNotNull()
                .isEqualTo(this.equipmentFactoryUtil.createEquipmentSaved().getId());
    }

    @Test
    void returnAllEquipmentsFoundWhenSuccessful() {
        String expectedName;
        expectedName = equipmentFactoryUtil.createEquipmentToBeSaved().getDescription();
        List<Equipment> equipmentListReturned;
        equipmentListReturned = this.equipmentResource.findAll().getBody();

        Assertions.assertThat(equipmentListReturned)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(equipmentListReturned.get(0).getDescription())
                .isNotNull()
                .isEqualTo(expectedName);
    }

    @Test
    void returnEquipmentFoundByIdWhenSuccessful() {
        Long expectedId;
        expectedId = equipmentFactoryUtil.createEquipmentSaved().getId();
        Equipment equipmentFound;
        equipmentFound = this.equipmentResource.findById(expectedId).getBody();

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
                this.equipmentResource.replace(
                        equipmentPutRequestBodyFactoryUtil.createEquipmentToBeSaved()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteEquipmentWhenSuccessful() {
        Long expectedId;
        expectedId = this.equipmentFactoryUtil.createEquipmentSaved().getId();

        Assertions.assertThatCode(() ->
                this.equipmentResource.delete(expectedId))
                .doesNotThrowAnyException();
    }
}