package com.solinftec.dev8api.service;

import com.solinftec.dev8api.requests.ApiUserPutRequestBody;
import com.solinftec.dev8api.util.ApiUserPutRequestBodyFactoryUtil;
import com.solinftec.dev8api.util.MultipartFileFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest
@DisplayName("Tests for StorageService")
@ExtendWith(SpringExtension.class)
class StorageServiceTest {
    private ApiUserPutRequestBodyFactoryUtil apiUserPutRequestBodyFactoryUtil;
    private MultipartFileFactoryUtil multipartFileFactoryUtil;

    @InjectMocks
    private StorageService storageService;


    @BeforeEach
    void init() {
        storageService = new StorageService();
        apiUserPutRequestBodyFactoryUtil = new ApiUserPutRequestBodyFactoryUtil();
        multipartFileFactoryUtil = new MultipartFileFactoryUtil();
    }

    @Test
    void replaceUserWithURLImageAndMultiPartNullWhenUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        MultipartFile multipartFile;
        multipartFile = multipartFileFactoryUtil.createMultiPartFileNull();

        Assertions.assertThat(
                this.storageService.replace(
                        apiUserToBeReplaced.getUrlPhoto(), multipartFile))
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void replaceWhenUserWithEmptyURLImageAndMultiPartUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserWithPhotoURLEmpty();
        MultipartFile multipartFile;
        multipartFile = multipartFileFactoryUtil.createMultiPartFileNull();

        Assertions.assertThat(
                this.storageService.replace(
                        apiUserToBeReplaced.getUrlPhoto(), multipartFile)).isNullOrEmpty();
    }

    @Test
    void replaceUserWithURLImageAndMultiPartWhenUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        MultipartFile multipartFile;
        multipartFile = multipartFileFactoryUtil.createMultiPartFile();

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.storageService.replace(apiUserToBeReplaced.getUrlPhoto(), multipartFile));
    }

}