package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.daos.UserRoleDAO;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.UserService;
import com.revature.util.exceptions.AuthenticationException;
import com.revature.util.exceptions.DataSourceException;
import com.revature.util.exceptions.InvalidRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService sut;

    //mock dependency classes
    private UserDAO mockUserDAO;
    private UserRoleDAO mockUserRoleDAO;

    @Before
    public void setup(){
        mockUserDAO = mock(UserDAO.class);
        mockUserRoleDAO = mock(UserRoleDAO.class);
        sut = new UserService(mockUserDAO, mockUserRoleDAO);
    }
    @Test
    public void test_ISUsernameValid_returnsFalse_givenEmptyString(){
        //AAA structure for testing
        //Act
        String username ="";
        //Act
        boolean result = sut.isUsernameValid(username);
        //Assert
        assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenNullString() {

        // Arrange
        String username = null;
        // Act
        boolean result = sut.isUsernameValid(null);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenShortUsername() {
        Assert.assertFalse(sut.isUsernameValid("short"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenLongUsername() {
        Assert.assertFalse(sut.isUsernameValid("waytolongofausernameforourapplication"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenUsernameWithIllegalCharacters() {
        Assert.assertFalse(sut.isUsernameValid("tester99!"));
    }

    @Test
    public void test_isUsernameValid_returnsTrue_givenValidUsername() {
        Assert.assertTrue(sut.isUsernameValid("tester99"));
    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsername() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("no", "p4$$W0RD");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDAO, times(0)).getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("tester99", "invalid");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDAO, times(0)).getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsernameAndPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid", "invalid");

        // Act
        try {
            sut.login(loginRequest);
        } finally {
            verify(mockUserDAO, times(0)).getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }
@Ignore
    @Test(expected = AuthenticationException.class)
    public void test_login_throwsAuthenticationException_givenUnknownUserCredentials() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("unknownuser", "p4$$W0RD");

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
        when(mockUserDAO.getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(null);

        // Act
        sut.login(loginRequest);

    }
@Ignore
    @Test
    public void test_login_returnsNonNullAppUser_givenValidAndKnownCredentials() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("iamwolverine", "p4$$Word");

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
       // when(mockUserDAO.getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(new User());
        mock(BCrypt.class);

        // Act
        User loginResult = spiedSut.login(loginRequest);

        // Assert
        assertNotNull(loginResult);
        verify(mockUserDAO, times(1)).getByUsernameandPassword(loginRequest.getUsername(), loginRequest.getPassword());
        verify(spiedSut, times(1)).isUsernameValid(loginRequest.getUsername());
        verify(spiedSut, times(1)).isPasswordValid(loginRequest.getPassword());

    }

    // register tests
    // - confirm the positive case (valid user provided, no conflicts)
    // - given invalid user data (empty strings/null values)
    // - given valid user, but has conflict in datasource

    @Test
    public void test_register_returnsPersistedAppUser_givenValidNewUserDataWithNoConflicts() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest stubbedRequest = new NewUserRequest("Tester", "McTesterson", "tester@revature.com", "tester99", "p4$$WORD");
        UserRole expectedRole = new UserRole("2", "USER");
        stubbedRequest.setRole(expectedRole.getRole());
        doReturn(true).when(spiedSut).isUserValid(any());
        doReturn(true).when(spiedSut).isUsernameAvailable(anyString());
        doReturn(true).when(spiedSut).isEmailAvailable(anyString());
        doReturn(expectedRole).when(mockUserRoleDAO).getById(any());

        // Act
        User registerResult = spiedSut.register(stubbedRequest);

        // Assert
        assertNotNull(registerResult);
        assertNotNull(registerResult.getUser_id());
        assertEquals(expectedRole, registerResult.getRole());
        verify(spiedSut, times(1)).isUserValid(any());
        verify(spiedSut, times(1)).isUsernameAvailable(anyString());
        verify(spiedSut, times(1)).isEmailAvailable(anyString());
        verify(mockUserDAO, times(1)).save(any());

    }

    @Test(expected = InvalidRequestException.class)
    public void test_register_throwsInvalidRequestException_givenInvalidNewUserData() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest stubbedRequest = new NewUserRequest();
        doReturn(false).when(spiedSut).isUserValid(any());

        // Act
        try {
            spiedSut.register(stubbedRequest);
        } finally {
            // Assert
            verify(spiedSut, times(1)).isUserValid(any());
            verify(spiedSut, times(0)).isUsernameAvailable(anyString());
            verify(spiedSut, times(0)).isEmailAvailable(anyString());
            verify(mockUserDAO, times(0)).save(any());
        }

    }

@Ignore
    @Test(expected = DataSourceException.class)
    public void test_register_propagatesDataSourceException_givenDaoThrows() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest stubbedRequest = new NewUserRequest("Tester", "McTesterson",
                "tester@revature.com", "tester99", "p4$$WORD");
        User extractedUser = stubbedRequest.extractUser();
        UserRole expectedRole = new UserRole("2", "USER");
        extractedUser.setRole(expectedRole);
        doReturn(true).when(spiedSut).isUserValid(extractedUser);
        doReturn(true).when(spiedSut).isUsernameAvailable(anyString());
        doReturn(true).when(spiedSut).isEmailAvailable(anyString());
        doThrow(new DataSourceException(new SQLException("stubbedSQLException"))).when(mockUserDAO).save(any());

        // Act
        try {
            spiedSut.register(stubbedRequest);
        } finally {
            verify(spiedSut, times(1)).isUserValid(any());
            verify(spiedSut, times(1)).isUsernameAvailable(anyString());
            verify(spiedSut, times(1)).isEmailAvailable(anyString());
            verify(mockUserDAO, times(1)).save(any());
        }
    }

}
