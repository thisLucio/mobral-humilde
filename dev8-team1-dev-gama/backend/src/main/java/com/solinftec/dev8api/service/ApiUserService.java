package com.solinftec.dev8api.service;

import com.solinftec.dev8api.dto.ApiUserDTO;
import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.kafka.KafkaService;
import com.solinftec.dev8api.kafka.UserStructure;
import com.solinftec.dev8api.repository.ApiUserRepository;
import com.solinftec.dev8api.requests.ApiUserPostRequestBody;
import com.solinftec.dev8api.requests.ApiUserPutRequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ApiUserService implements UserDetailsService {
    private ApiUserRepository apiUserRepository;
    private StorageService storageService;
    private KafkaService kafkaService;

    public List<ApiUser> findAll() {
        return apiUserRepository.findAll();
    }

    public ApiUser findById(Long id) {
        return apiUserRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ApiUser save(ApiUserPostRequestBody apiUserPostRequestBody) {
        apiUserPostRequestBody.setUrlPhoto(storageService.store(apiUserPostRequestBody.getPhoto()));
        UserStructure userStructure = new UserStructure(apiUserPostRequestBody.getUsername(), apiUserPostRequestBody.getEmail());
        ApiUser apiUser = apiUserRepository.save(
                ApiUser.builder()
                        .username(apiUserPostRequestBody.getUsername())
                        .password(new BCryptPasswordEncoder().encode(apiUserPostRequestBody.getPassword()))
                        .email(apiUserPostRequestBody.getEmail())
                        .urlPhoto(apiUserPostRequestBody.getUrlPhoto())
                        .admin(apiUserPostRequestBody.getAdmin())
                        .build());
        kafkaService.sendUserRequestBodyToKafka(userStructure);
        return apiUser;
    }

    public void delete(Long id) {
        ApiUser user;
        user = findById(id);
        storageService.delete(user.getUrlPhoto());
        apiUserRepository.delete(user);
    }

    public ApiUserDTO replace(ApiUserPutRequestBody apiUserPutRequestBody) {
        ApiUser apiUserLoaded;
        apiUserLoaded = findById(apiUserPutRequestBody.getId());

        String photoStored;
        photoStored = storageService.replace(
                apiUserLoaded.getUrlPhoto(),
                apiUserPutRequestBody.getPhoto());
        apiUserPutRequestBody.setUrlPhoto(photoStored);

        apiUserRepository.save(apiUserLoaded.builder()
                .id(apiUserPutRequestBody.getId())
                .username(apiUserPutRequestBody.getUsername())
                .password(
                        apiUserPutRequestBody.getPassword() == null ?
                                apiUserLoaded.getPassword() :
                                new BCryptPasswordEncoder().encode(apiUserPutRequestBody.getPassword())
                )
                .email(apiUserPutRequestBody.getEmail() == null ?
                        apiUserLoaded.getEmail() :
                        apiUserPutRequestBody.getEmail())
                .urlPhoto(apiUserPutRequestBody.getUrlPhoto())
                .admin(apiUserPutRequestBody.getAdmin())
                .build());

        ApiUserDTO apiUserDTO =
                new ApiUserDTO(apiUserLoaded.getId(),
                        apiUserLoaded.getUsername(),
                        apiUserLoaded.getEmail(),
                        apiUserLoaded.getUrlPhoto(),
                        apiUserLoaded.isAdmin());

        return apiUserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(apiUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
