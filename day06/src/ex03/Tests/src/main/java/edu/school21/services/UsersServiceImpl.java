package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);
        if (user.isAuthenticationStatus()) {
            throw new AlreadyAuthenticatedException("The user is already logged in!");
        }
        boolean result = password.equals(user.getPassword());
        if (result) {
            user.setAuthenticationStatus(true);
            usersRepository.update(user);
        }
        return result;
    }
}
