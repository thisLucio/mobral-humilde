package com.solinftec.dev8api.repository;

import com.solinftec.dev8api.entity.ApiUser;
import com.solinftec.dev8api.util.ApiUserFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for ApiUserRepository")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("development")
class ApiUserRepositoryTest {
    private ApiUserFactoryUtil apiUserFactoryUtil;

    @Autowired
    ApiUserRepository apiUserRepository;

    @BeforeEach
    void init() {
        apiUserFactoryUtil = new ApiUserFactoryUtil();
    }

    @Test
    void saveUserWhenSuccessful() {
        ApiUser apiUserToBeSaved, apiUserSaved;
        apiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();
        apiUserSaved = this.apiUserRepository.save(apiUserToBeSaved);

        Assertions.assertThat(apiUserSaved).isNotNull();
        Assertions.assertThat(apiUserSaved.getId()).isNotNegative();
        Assertions.assertThat(apiUserSaved.getUsername())
                .isNotNull()
                .isEqualTo(apiUserToBeSaved.getUsername());
    }

    @Test
    void saveOrReplaceUserWithEmptyUsernameWhenUnsuccessful() {
        ApiUser apiUserToBeSaved;
        apiUserToBeSaved = apiUserFactoryUtil.createUserWithUsernameEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(apiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUserWithEmptyPasswordWhenUnsuccessful() {
        ApiUser apiUserToBeSaved;
        apiUserToBeSaved = apiUserFactoryUtil.createUserWithPasswordEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(apiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUserWithEmptyEmailWhenUnsuccessful() {
        ApiUser apiUserToBeSaved;
        apiUserToBeSaved = apiUserFactoryUtil.createUserWithEmailEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(apiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUserWithEmailNotValidatedWhenUnsuccessful() {
        ApiUser apiUserToBeSaved;
        apiUserToBeSaved = apiUserFactoryUtil.createUserWithEmailNotValidated();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(apiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUserWithUsernameSameWhenUnsuccessful() {
        ApiUser firstApiUserToBeSaved, secondApiUserToBeSaved;

        firstApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();
        this.apiUserRepository.save(firstApiUserToBeSaved);

        secondApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSavedWithUsernameSame();

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(secondApiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUserWithEmailSameWhenUnsuccessful() {
        ApiUser firstApiUserToBeSaved, secondApiUserToBeSaved;

        firstApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();

        this.apiUserRepository.save(firstApiUserToBeSaved);
        secondApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSavedWithEmailSame();

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(secondApiUserToBeSaved));
    }

    @Test
    void saveOrReplaceUsersWithEmailAndUsernameSameWhenUnsuccessful() {
        ApiUser firstApiUserToBeSaved, secondApiUserToBeSaved;
        firstApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();
        secondApiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();

        this.apiUserRepository.save(firstApiUserToBeSaved);
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.apiUserRepository.save(secondApiUserToBeSaved));
    }

    @Test
    void findByNameUserWhenSuccessful() {
        ApiUser apiUserToBeSave, apiUserSaved, apiUserLoaded;
        apiUserToBeSave = apiUserFactoryUtil.createUserToBeSaved();
        apiUserSaved = this.apiUserRepository.save(apiUserToBeSave);

        String nameSearched;
        nameSearched = apiUserSaved.getUsername();

        apiUserLoaded = this.apiUserRepository.findByUsername(nameSearched);

        Assertions.assertThat(apiUserLoaded).isNotNull();
        Assertions.assertThat(apiUserLoaded.getUsername()).isEqualTo(apiUserSaved.getUsername());
    }

    @Test
    void findByNameUserWhenUnsuccessful() {
        ApiUser apiUserLoaded, apiUserOtherUser;
        apiUserOtherUser = apiUserFactoryUtil.createOtherUserToBeSaved();
        String nameSearched;
        nameSearched = apiUserOtherUser.getUsername();
        apiUserLoaded = this.apiUserRepository.findByUsername(nameSearched);

        Assertions.assertThat(apiUserLoaded).isNull();
    }

    @Test
    void replaceUserWhenSuccessful() {
        ApiUser apiUserToBeSaved, apiUserSaved, apiUserUpdated;
        apiUserToBeSaved = apiUserFactoryUtil.createUserToBeSaved();

        apiUserSaved = this.apiUserRepository.save(apiUserToBeSaved);
        apiUserSaved = apiUserFactoryUtil.changeUserSaved();
        apiUserUpdated = this.apiUserRepository.save(apiUserSaved);


        Assertions.assertThat(apiUserUpdated).isNotNull();
        Assertions.assertThat(apiUserUpdated.getId()).isNotNegative();
        Assertions.assertThat(apiUserUpdated.getUsername())
                .isNotNull()
                .isEqualTo(apiUserSaved.getUsername());
    }

    @Test
    void deleteUserWhenSuccessful() {
        ApiUser apiUserToBeSave, apiUserSaved;

        apiUserToBeSave = apiUserFactoryUtil.createUserToBeSaved();
        apiUserSaved = this.apiUserRepository.save(apiUserToBeSave);
        this.apiUserRepository.delete(apiUserSaved);

        Optional<ApiUser> apiUserLoaded;
        apiUserLoaded = this.apiUserRepository.findById(apiUserSaved.getId());

        Assertions.assertThat(apiUserLoaded).isEmpty();
    }
}