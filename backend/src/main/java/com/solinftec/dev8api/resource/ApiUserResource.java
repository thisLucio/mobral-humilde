package com.solinftec.dev8api.resource;

import com.solinftec.dev8api.dto.ApiUserDTO;
import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.requests.ApiUserPostRequestBody;
import com.solinftec.dev8api.requests.ApiUserPutRequestBody;
import com.solinftec.dev8api.service.ApiUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user") //http://localhost:8080/user
@AllArgsConstructor
@Log4j2
public class ApiUserResource {
    private ApiUserService apiUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApiUserDTO>> findAll() {
        List<ApiUser> users = apiUserService.findAll();
        List<ApiUserDTO> usersDto;
        usersDto = users.stream().map(ApiUserDTO::new).toList();

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiUserDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(new ApiUserDTO(apiUserService.findById(id)));
    }

    @PostMapping

    public ResponseEntity<ApiUser> save(@Valid @ModelAttribute ApiUserPostRequestBody apiUserPostRequestBody) {
        return new ResponseEntity<>(apiUserService.save(apiUserPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apiUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiUserDTO> replace(@ModelAttribute ApiUserPutRequestBody apiUserPutRequestBody) {
        apiUserService.replace(apiUserPutRequestBody);
        return new ResponseEntity<>(apiUserService.replace(apiUserPutRequestBody), HttpStatus.OK);
    }
}
