package com.solinftec.dev8api.service;

import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.kafka.KafkaService;
import com.solinftec.dev8api.repository.ApiUserRepository;
import com.solinftec.dev8api.util.ApiUserFactoryUtil;
import com.solinftec.dev8api.util.ApiUserPostRequestBodyFactoryUtil;
import com.solinftec.dev8api.util.ApiUserPutRequestBodyFactoryUtil;
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
@DisplayName("Tests for ApiUserService")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("development")
class ApiUserServiceTest {
    private ApiUserFactoryUtil apiUserFactoryUtil;
    private ApiUserPostRequestBodyFactoryUtil apiUserPostRequestBodyFactoryUtil;
    private ApiUserPutRequestBodyFactoryUtil apiUserPutRequestBodyFactoryUtil;

    @InjectMocks
    ApiUserService apiUserService;

    @Mock
    ApiUserRepository apiUserRepository;

    @Mock
    StorageService storageService;

    @Mock
    KafkaService kafkaService;

    @Mock
    EmailConfirmationService emailConfirmationService;

    @BeforeEach
    void init() {
        this.apiUserFactoryUtil = new ApiUserFactoryUtil();

        List<ApiUser> apiUsers;
        apiUsers = List.of(apiUserFactoryUtil.createUserSaved());

        BDDMockito.when(this.apiUserRepository.save(ArgumentMatchers.any(ApiUser.class)))
                .thenReturn(apiUserFactoryUtil.createUserSaved());
        BDDMockito.when(this.apiUserRepository.findAll())
                .thenReturn(apiUsers);
        BDDMockito.when(this.apiUserRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(apiUserFactoryUtil.createUserSaved()));
        BDDMockito.doNothing().when(this.apiUserRepository).delete(ArgumentMatchers.any(ApiUser.class));
    }

    @Test
    void saveUserWhenSuccessful() {
        this.apiUserPostRequestBodyFactoryUtil = new ApiUserPostRequestBodyFactoryUtil();
        ApiUser apiUserSaved;
        apiUserSaved = this.apiUserService.save(this.apiUserPostRequestBodyFactoryUtil.createUserToBeSaved());

        Assertions.assertThat(apiUserSaved).isNotNull();
        Assertions.assertThat(apiUserSaved.getId())
                .isNotNegative()
                .isEqualTo(this.apiUserFactoryUtil.createUserSaved().getId());
    }

    @Test
    void returnAllUsersFoundWhenSuccessful() {
        String expectedName;
        expectedName = apiUserFactoryUtil.createUserToBeSaved().getUsername();
        List<ApiUser> apiUserReturned;
        apiUserReturned = this.apiUserService.findAll();

        Assertions.assertThat(apiUserReturned)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(apiUserReturned.get(0).getUsername())
                .isNotNull()
                .isEqualTo(expectedName);
    }

    @Test
    void returnUserFoundByIdWhenSuccessful() {
        Long expectedId;
        expectedId = apiUserFactoryUtil.createUserSaved().getId();
        ApiUser apiUserFound;
        apiUserFound = this.apiUserService.findById(expectedId);

        Assertions.assertThat(apiUserFound)
                .isNotNull();
        Assertions.assertThat(apiUserFound.getId())
                .isNotNegative()
                .isEqualTo(expectedId);
    }

    @Test
    void replaceUserWhenSuccessful() {
        apiUserPostRequestBodyFactoryUtil = new ApiUserPostRequestBodyFactoryUtil();
        this.apiUserService.save(this.apiUserPostRequestBodyFactoryUtil.createUserToBeSaved());

        apiUserPutRequestBodyFactoryUtil = new ApiUserPutRequestBodyFactoryUtil();
        Assertions.assertThatCode(() ->
                this.apiUserService.replace(
                        apiUserPutRequestBodyFactoryUtil.createUserToBeSaved()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteUserWhenSuccessful() {

        Assertions.assertThatCode(() ->
                this.apiUserService.delete(this.apiUserFactoryUtil.createUserSaved().getId()))
                .doesNotThrowAnyException();
    }


}
