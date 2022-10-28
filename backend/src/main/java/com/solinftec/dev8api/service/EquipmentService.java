package com.solinftec.dev8api.service;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.enums.OperationType;
import com.solinftec.dev8api.kafka.EquipmentStructure;
import com.solinftec.dev8api.kafka.KafkaService;
import com.solinftec.dev8api.repository.EquipmentRepository;
import com.solinftec.dev8api.requests.EquipmentPostRequestBody;
import com.solinftec.dev8api.requests.EquipmentPutRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class EquipmentService {
    private KafkaService kafkaService;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Equipment save(EquipmentPostRequestBody equipmentPostRequestBody) {
        manageColor(equipmentPostRequestBody);

        Equipment savedEquipment = equipmentRepository.save(Equipment.builder()
                .description(equipmentPostRequestBody.getDescription())
                .color(equipmentPostRequestBody.getColor())
                .latitude(equipmentPostRequestBody.getLatitude())
                .longitude(equipmentPostRequestBody.getLongitude())
                .build());

        kafkaService.sendEquipmentRequestBodyToKafka(new EquipmentStructure(savedEquipment, OperationType.CREATE));

        return savedEquipment;
    }

    public void delete(Long id) {
        Equipment equipment;
        equipment = findById(id);
        equipmentRepository.delete(equipment);
        kafkaService.sendEquipmentRequestBodyToKafka(new EquipmentStructure(equipment, OperationType.DELETE));
    }

    public void replace(EquipmentPutRequestBody equipmentPutRequestBody) {
        manageColor(equipmentPutRequestBody);

        Equipment equipment;
        equipment = findById(equipmentPutRequestBody.getId());

        equipmentRepository.save(equipment.builder()
                .id(equipmentPutRequestBody.getId())
                .description(equipmentPutRequestBody.getDescription())
                .color(equipmentPutRequestBody.getColor())
                .latitude(equipmentPutRequestBody.getLatitude())
                .longitude(equipmentPutRequestBody.getLongitude())
                .build());

        kafkaService.sendEquipmentRequestBodyToKafka(new EquipmentStructure(equipment, OperationType.UPDATE));
    }

    private void manageColor(EquipmentPostRequestBody equipment) {
        manageRegexColor(equipment.getColor());
    }

    private void manageColor(EquipmentPutRequestBody equipment) {
        manageRegexColor(equipment.getColor());
    }

    private void manageRegexColor(String color) {
//        Checks if the color is a valid hexadecimal
        if (!color.toLowerCase(Locale.ROOT).matches("^#?([a-f0-9]{6}|[a-f0-9]{3})$")) {
            throw new RuntimeException("Invalid color format");
        }
    }
}
