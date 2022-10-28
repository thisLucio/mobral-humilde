package com.solinftec.dev8api.resource;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.requests.EquipmentPostRequestBody;
import com.solinftec.dev8api.requests.EquipmentPutRequestBody;
import com.solinftec.dev8api.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("equipment") //http://localhost:8080/equipment
@AllArgsConstructor
public class EquipmentResource {

    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<Equipment>> findAll() {
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Equipment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Equipment> save(@RequestBody EquipmentPostRequestBody equipmentPostRequestBody) {
        return new ResponseEntity<>(equipmentService.save(equipmentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody EquipmentPutRequestBody equipmentPutRequestBody) {
        equipmentService.replace(equipmentPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
