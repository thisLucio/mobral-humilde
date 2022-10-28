package com.solinftec.dev8api.service;

import com.solinftec.dev8api.requests.ApiUserPutRequestBody;
import com.solinftec.dev8api.service.exceptions.StorageException;
import com.solinftec.dev8api.util.ApiUserPutRequestBodyFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest
@DisplayName("Tests for StorageService")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("development")
class StorageServiceTest {
    private ApiUserPutRequestBodyFactoryUtil apiUserPutRequestBodyFactoryUtil;

    @InjectMocks
    private StorageService storageService;

    @BeforeEach
    void init() {
        storageService = new StorageService();
        apiUserPutRequestBodyFactoryUtil = new ApiUserPutRequestBodyFactoryUtil();
    }

    @Test
    void replaceUserWithEmptyURLImageIsNullAndMultiPartWhenUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        String photoUrl;
        photoUrl = apiUserToBeReplaced.getUrlPhoto();

        StorageService storageService;
        storageService = Mockito.spy(StorageService.class);
        Mockito.doReturn(photoUrl).when(storageService).store(ArgumentMatchers.any());

        MultipartFile multipartFile;
        multipartFile = Mockito.mock(MultipartFile.class);

        Assertions.assertThat(
                        storageService.replace(null, multipartFile))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(photoUrl);
    }

    @Test
    void replaceUserWithEmptyURLImageIsEmptyAndMultiPartWhenUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        String PhotoUrl;
        PhotoUrl = apiUserToBeReplaced.getUrlPhoto();

        StorageService storageService;
        storageService = Mockito.spy(StorageService.class);
        Mockito.doReturn(PhotoUrl).when(storageService).store(ArgumentMatchers.any());

        MultipartFile multipartFile;
        multipartFile = Mockito.mock(MultipartFile.class);

        Assertions.assertThat(
                        storageService.replace(new String(), multipartFile))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(PhotoUrl);
    }

    @Test
    void replaceUserWithURLImageAndMultiPartIsNullWhenUnsuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        String PhotoUrl;
        PhotoUrl = apiUserToBeReplaced.getUrlPhoto();

        String url;
        url = apiUserToBeReplaced.getUrlPhoto();

        Assertions.assertThat(
                        this.storageService.replace(url, null))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(PhotoUrl);
    }

    @Test
    void replaceUserWithURLImageAndMultiPartIsEmptyWhenUnsuccessful() {
        MultipartFile multipartFile;
        multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.isEmpty()).thenReturn(true);

        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        String photoUrl;
        photoUrl = apiUserToBeReplaced.getUrlPhoto();

        Assertions.assertThatExceptionOfType(StorageException.class)
                .isThrownBy(() -> this.storageService.replace(photoUrl, multipartFile));
    }

    @Test
    void replaceWhenSuccessful() {
        ApiUserPutRequestBody apiUserToBeReplaced;
        apiUserToBeReplaced = apiUserPutRequestBodyFactoryUtil.createUserToBeSaved();
        String photoUrl;
        photoUrl = apiUserToBeReplaced.getUrlPhoto();

        StorageService storageService;
        storageService = Mockito.spy(StorageService.class);
        Mockito.doNothing().when(storageService).delete(ArgumentMatchers.any());
        Mockito.doReturn(photoUrl).when(storageService).store(ArgumentMatchers.any());

        MultipartFile multipartFile;
        multipartFile = Mockito.mock(MultipartFile.class);

        Assertions.assertThat(storageService.replace(photoUrl, multipartFile))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(photoUrl);
    }
}