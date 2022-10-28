package com.solinftec.dev8api.resource;

import com.solinftec.dev8api.dto.ApiUserDTO;
import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.requests.ApiUserPostRequestBody;
import com.solinftec.dev8api.requests.ApiUserPutRequestBody;
import com.solinftec.dev8api.service.ApiUserService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@DisplayName("Tests for ApiUserResource")
@ExtendWith(SpringExtension.class)
class ApiUserResourceTest {
    private ApiUserFactoryUtil apiUserFactoryUtil;
    private ApiUserPostRequestBodyFactoryUtil apiUserPostRequestBodyFactoryUtil;
    private ApiUserPutRequestBodyFactoryUtil apiUserPutRequestBodyFactoryUtil;

    @InjectMocks
    private ApiUserResource apiUserResource;

    @Mock
    private ApiUserService apiUserService;

    @BeforeEach
    void init() {
        this.apiUserFactoryUtil = new ApiUserFactoryUtil();

        List<ApiUser> apiUsers;
        apiUsers = List.of(apiUserFactoryUtil.createUserSaved());

        BDDMockito.when(this.apiUserService.save(ArgumentMatchers.any(ApiUserPostRequestBody.class)))
                .thenReturn(apiUserFactoryUtil.createUserSaved());
        BDDMockito.when(this.apiUserService.findAll())
                .thenReturn(apiUsers);
        BDDMockito.when(this.apiUserService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(apiUserFactoryUtil.createUserSaved());
        BDDMockito.doNothing().when(this.apiUserService).delete(ArgumentMatchers.anyLong());
        BDDMockito.doNothing().when(this.apiUserService).replace(ArgumentMatchers.any(ApiUserPutRequestBody.class));
    }

    @Test
    void saveUserWhenSuccessful() {
        apiUserPostRequestBodyFactoryUtil = new ApiUserPostRequestBodyFactoryUtil();
        ApiUser apiUserSaved;
        apiUserSaved = this.apiUserResource.save(apiUserPostRequestBodyFactoryUtil.createUserToBeSaved()).getBody();

        Assertions.assertThat(apiUserSaved).isNotNull();
        Assertions.assertThat(apiUserSaved.getId())
                .isNotNull()
                .isEqualTo(this.apiUserFactoryUtil.createUserSaved().getId());
    }

    @Test
    void returnAllUsersFoundWhenSuccessful() {
        String expectedName;
        expectedName = apiUserFactoryUtil.createUserToBeSaved().getUsername();
        List<ApiUserDTO> apiUserReturned;
        apiUserReturned = this.apiUserResource.findAll().getBody();

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
        ApiUserDTO apiUserDTOFound;
        apiUserDTOFound = this.apiUserResource.findById(expectedId).getBody();

        Assertions.assertThat(apiUserDTOFound)
                .isNotNull();
        Assertions.assertThat(apiUserDTOFound.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    void replaceUserWhenSuccessful() {
        apiUserPutRequestBodyFactoryUtil = new ApiUserPutRequestBodyFactoryUtil();

        Assertions.assertThatCode(() ->
                this.apiUserResource.replace(
                        apiUserPutRequestBodyFactoryUtil.createUserToBeSaved()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteUserWhenSuccessful() {
        Long expectedId;
        expectedId = this.apiUserFactoryUtil.createUserSaved().getId();

        Assertions.assertThatCode(() ->
                this.apiUserResource.delete(expectedId))
                .doesNotThrowAnyException();
    }
}
