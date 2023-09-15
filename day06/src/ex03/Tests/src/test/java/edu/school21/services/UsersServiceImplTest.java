package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    private final String LOGIN = "login";

    private final String PASSWORD = "password";

    private final String INCORRECT_LOGIN = "incorrect_login";

    private final String INCORRECT_PASSWORD = "incorrect_password";

    private User user;

    @Mock
    private UsersRepository mockUserRepository;

    private UsersServiceImpl usersService;

    @BeforeEach
    void init() {
        user = new User(1L, LOGIN, PASSWORD, false);
        mockUserRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(mockUserRepository);
    }

    @Test
    void correctLoginPasswordTest() throws EntityNotFoundException, AlreadyAuthenticatedException {
        Mockito.doReturn(user).when(mockUserRepository).findByLogin(LOGIN);
        Mockito.doNothing().when(mockUserRepository).update(user);
        Assertions.assertTrue(usersService.authenticate(LOGIN, PASSWORD));
        Mockito.verify(mockUserRepository, times(1)).findByLogin(LOGIN);
        Mockito.verify(mockUserRepository, times(1)).update(user);
        Assertions.assertTrue(user.isAuthenticationStatus());
    }

    @Test
    void incorrectLoginTest() throws EntityNotFoundException {
        Mockito.doThrow(EntityNotFoundException.class).when(mockUserRepository).findByLogin(INCORRECT_LOGIN);
        Assertions.assertThrows(EntityNotFoundException.class, () -> usersService.authenticate(INCORRECT_LOGIN, PASSWORD));
        Mockito.verify(mockUserRepository, times(1)).findByLogin(INCORRECT_LOGIN);
        Mockito.verify(mockUserRepository, never()).update(user);
        Assertions.assertFalse(user.isAuthenticationStatus());
    }

    @Test
    void incorrectPasswordTest() throws AlreadyAuthenticatedException, EntityNotFoundException {
        Mockito.doReturn(user).when(mockUserRepository).findByLogin(LOGIN);
        Assertions.assertFalse(usersService.authenticate(LOGIN, INCORRECT_PASSWORD));
        Mockito.verify(mockUserRepository, times(1)).findByLogin(LOGIN);
        Mockito.verify(mockUserRepository, never()).update(user);
        Assertions.assertFalse(user.isAuthenticationStatus());
    }

    @Test
    void userAlreadyLoggedIn() throws EntityNotFoundException {
        user.setAuthenticationStatus(true);
        Mockito.doReturn(user).when(mockUserRepository).findByLogin(LOGIN);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate(LOGIN, PASSWORD));
        Mockito.verify(mockUserRepository, times(1)).findByLogin(LOGIN);
        Mockito.verify(mockUserRepository, never()).update(user);
        Assertions.assertTrue(user.isAuthenticationStatus());
    }
}
