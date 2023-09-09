package com.playground.app.registration.ui;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.playground.app.registration.data.model.Result;
import com.playground.app.registration.data.repository.RegistrationRepository;
import com.playground.app.registration.ui.model.RegistrationUiState;
import com.playground.app.registration.ui.model.UserCredentials;
import com.playground.app.registration.ui.model.UserInput;
import com.playground.app.registration.ui.model.UserPersonalInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegistrationViewModel extends ViewModel {
    private final RegistrationRepository registrationRepository;
    @Inject
    RegistrationViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }
    private final MutableLiveData<RegistrationUiState> uiState = new MutableLiveData<>(new RegistrationUiState());
    public LiveData<RegistrationUiState> getUiState() {
        return uiState;
    }
    private final MutableLiveData<Boolean> _isUserPersonalInfoValid = new MutableLiveData<>(null);
    public LiveData<Boolean> isUserPersonalInfoValid() {
        return _isUserPersonalInfoValid;
    }
    private final MutableLiveData<Boolean> _isUserCredentialsValid = new MutableLiveData<>(null);
    public LiveData<Boolean> isUserCredentialsValid() {
        return _isUserCredentialsValid;
    }
    private final MutableLiveData<Boolean> _isImageSaved = new MutableLiveData<>(null);
    public LiveData<Boolean> isImageSaved() {
        return _isImageSaved;
    }

    private UserPersonalInfo _userPersonalInfo = new UserPersonalInfo(
        new UserInput(),
        new UserInput(),
        new UserInput(),
        new UserInput(),
        new UserInput()
    );
    public UserPersonalInfo getUserPersonalInfo() {
        return _userPersonalInfo;
    }
    private UserCredentials _userCredentials = new UserCredentials(
        new UserInput(),
        new UserInput()
    );
    public UserCredentials getUserCredentials() {
        return _userCredentials;
    }
    public void register() {


        registrationRepository.registerWithFirebaseAuth(_userPersonalInfo, _userCredentials, result -> {
            if (result instanceof Result.Success) {
                uiState.postValue(new RegistrationUiState());
            }
        });
    }
    public void updateCitizenId(String citizenId) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setCitizenId(
                new UserInput(
                    citizenId,
                    _userPersonalInfo.getCitizenId().getIsValid(),
                    _userPersonalInfo.getCitizenId().getError()
                )
            );
        }
    }
    public void updateFirstName(String firstName) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setFirstName(
                new UserInput(
                    firstName,
                    _userPersonalInfo.getFirstName().getIsValid(),
                    _userPersonalInfo.getFirstName().getError()
                )
            );
        }
    }
    public void updateLastName(String lastName) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setLastName(
                new UserInput(
                    lastName,
                    _userPersonalInfo.getLastName().getIsValid(),
                    _userPersonalInfo.getLastName().getError()
                )
            );
        }
    }
    public void updatePhoneNumber(String phoneNumber) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setPhoneNumber(
                new UserInput(
                    phoneNumber,
                    _userPersonalInfo.getPhoneNumber().getIsValid(),
                    _userPersonalInfo.getPhoneNumber().getError()
                )
            );
        }
    }
    public void updateEmail(String email) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setEmail(
                new UserInput(
                    email,
                    _userPersonalInfo.getEmail().getIsValid(),
                    _userPersonalInfo.getEmail().getError()
                )
            );
        }
    }
    public void updateUsername(String username) {
        if (_userCredentials != null) {
            _userCredentials.setUsername(
                new UserInput(
                    username,
                    _userCredentials.getUsername().getIsValid(),
                    _userCredentials.getUsername().getError()
                )
            );
        }
    }
    public void updatePassword(String password) {
        if (_userCredentials != null) {
            _userCredentials.setPassword(
                new UserInput(password,
                    _userCredentials.getPassword().getIsValid(),
                    _userCredentials.getPassword().getError()
                )
            );
        }
    }
    public void updatePictureUri(Uri picture) {
        if (_userPersonalInfo != null) {
            _userPersonalInfo.setPictureUri(picture);
        }
        _isImageSaved.postValue(true);
    }
    private String validateCitizenId(String citizenId) {

        String validationMessage = "";

        if (citizenId.length() != 13) {
            validationMessage += "\nCitizen ID should be 13 digits";
        }

        if (!citizenId.matches("[0-9]+")) {
            validationMessage += "\nCitizen ID should be number";
        }

        if (!validationMessage.isEmpty()) {
            return validationMessage;
        }

        int sum = 0;

        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(citizenId.charAt(i)) * (13 - i);
        }

        boolean isValid = (11 - sum % 11) % 10 == Character.getNumericValue(citizenId.charAt(12));

        if (!isValid) {
            validationMessage += "\nCitizen ID should be valid with thai citizen ID format";
        }

        return validationMessage;
    }

    private String validateFirstName(String firstName) {

        String validationMessage = "";

        if (firstName.length() == 0) {
            validationMessage += "\nFirst name should not be empty";
        }

        if (!firstName.matches("[a-zA-Z]+")) {
            validationMessage += "\nFirst name should be english letter";
        }

        if (!validationMessage.isEmpty()) {
            return validationMessage;
        }

        if (!Character.isUpperCase(firstName.charAt(0))) {
            validationMessage += "\nFirst name should start with capital letter";
        }

        return validationMessage;
    }

    private String validateLastName(String lastName) {

        String validationMessage = "";

        if (lastName.length() == 0) {
            validationMessage += "\nLast name should not be empty";
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            validationMessage += "\nLast name should be english letter";
        }

        if (!validationMessage.isEmpty()) {
            return validationMessage;
        }

        if (!Character.isUpperCase(lastName.charAt(0))) {
            validationMessage += "\nLast name should start with capital letter";
        }
        return validationMessage;
    }

    private String validatePhoneNumber(String phoneNumber) {

        String validationMessage = "";

        if (phoneNumber.length() != 10) {
            validationMessage += "\nPhone number should be 10 digits";
        }

        if (!phoneNumber.matches("[0-9]+")) {
            validationMessage += "\nPhone number should be number";
        }

        return validationMessage;
    }

    private String validateEmail(String email) {

        String validationMessage = "";

        if (email.length() == 0) {
            validationMessage += "\nEmail should not be empty";
        }

        if (!email.matches("^(.+)@(.+)$")) {
            validationMessage += "\nEmail should be in email format";
        }

        return validationMessage;
    }

    private String validateUsername(String email) {

        String validationMessage = "";

        if (email.length() == 0) {
            validationMessage += "\nUsername should not be empty";
        }

        if (!email.matches("[a-zA-Z0-9]+")) {
            validationMessage += "\nUsername should be english letter or number";
        }

        return validationMessage;
    }

    private String validatePassword(String password) {

        String validationMessage = "";

        if (password.length() == 0) {
            validationMessage += "\nPassword should not be empty";
        }

        if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            validationMessage += "\nPassword must contain" +
                "\nat least one capital english letter," +
                "\nat least one small english letter," +
                "\nat least one number and" +
                "\nat least one special character";
        }

        return validationMessage;
    }

    public void validateUserPersonalInfo() {

        boolean allValid = true;

        if (_userPersonalInfo != null) {

            UserInput newCitizenIdInput = new UserInput();
            UserInput oldCitizenIdInput = _userPersonalInfo.getCitizenId();
            String citizenIdValidationMessage = validateCitizenId(oldCitizenIdInput.getText());
            Boolean isCitizenIdValid = citizenIdValidationMessage.isEmpty();
            setNewUserInput(newCitizenIdInput, oldCitizenIdInput, isCitizenIdValid, citizenIdValidationMessage);
            if (!isCitizenIdValid) {
                allValid = false;
            }

            UserInput newFirstNameInput = new UserInput();
            UserInput oldFirstNameInput = _userPersonalInfo.getFirstName();
            String firstNameValidationMessage = validateFirstName(oldFirstNameInput.getText());
            Boolean isFirstNameValid = firstNameValidationMessage.isEmpty();
            setNewUserInput(newFirstNameInput, oldFirstNameInput, isFirstNameValid, firstNameValidationMessage);
            if (!isFirstNameValid) {
                allValid = false;
            }

            UserInput newLastNameInput = new UserInput();
            UserInput oldLastNameInput = _userPersonalInfo.getLastName();
            String lastNameValidationMessage = validateLastName(oldLastNameInput.getText());
            Boolean isLastNameValid = lastNameValidationMessage.isEmpty();
            setNewUserInput(newLastNameInput, oldLastNameInput, isLastNameValid, lastNameValidationMessage);
            if (!isLastNameValid) {
                allValid = false;
            }

            UserInput newPhoneNumberInput = new UserInput();
            UserInput oldPhoneNumberInput = _userPersonalInfo.getPhoneNumber();
            String phoneNumberValidationMessage = validatePhoneNumber(oldPhoneNumberInput.getText());
            Boolean isPhoneNumberValid = phoneNumberValidationMessage.isEmpty();
            setNewUserInput(newPhoneNumberInput, oldPhoneNumberInput, isPhoneNumberValid, phoneNumberValidationMessage);
            if (!isPhoneNumberValid) {
                allValid = false;
            }

            UserInput newEmailInput = new UserInput();
            UserInput oldEmailInput = _userPersonalInfo.getEmail();
            String emailValidationMessage = validateEmail(oldEmailInput.getText());
            Boolean isEmailValid = emailValidationMessage.isEmpty();
            setNewUserInput(newEmailInput, oldEmailInput, isEmailValid, emailValidationMessage);
            if (!isEmailValid) {
                allValid = false;
            }

            _userPersonalInfo = new UserPersonalInfo(newCitizenIdInput, newFirstNameInput, newLastNameInput, newPhoneNumberInput, newEmailInput);
            _isUserPersonalInfoValid.setValue(allValid);
        }
    }

    public void validateUserCredentials() {

        boolean allValid = true;

        if (_userCredentials != null) {

            UserInput newUsernameInput = new UserInput();
            UserInput oldUsernameInput = _userCredentials.getUsername();
            String usernameValidationMessage = validateUsername(oldUsernameInput.getText());
            Boolean isUsernameValid = usernameValidationMessage.isEmpty();
            setNewUserInput(newUsernameInput, oldUsernameInput, isUsernameValid, usernameValidationMessage);
            if (!isUsernameValid) {
                allValid = false;
            }

            UserInput newPasswordInput = new UserInput();
            UserInput oldPasswordInput = _userCredentials.getPassword();
            String passwordValidationMessage = validatePassword(oldPasswordInput.getText());
            Boolean isPasswordValid = passwordValidationMessage.isEmpty();
            setNewUserInput(newPasswordInput, oldPasswordInput, isPasswordValid, passwordValidationMessage);
            if (!isPasswordValid) {
                allValid = false;
            }

            _userCredentials = new UserCredentials(newUsernameInput, newPasswordInput);
            _isUserCredentialsValid.setValue(allValid);
        }
    }

    public void clearUserPersonalValidation() {
        _isUserPersonalInfoValid.setValue(null);
    }
    public void clearUserCredentialsValidation() {
        _isUserCredentialsValid.setValue(null);
    }

    private void setNewUserInput(
        UserInput newUserInput,
        UserInput oldUserInput,
        Boolean isValid,
        String validationMessage
    ) {
        newUserInput.setText(oldUserInput.getText());
        newUserInput.setIsValid(isValid);
        newUserInput.setError(validationMessage);
    }
}