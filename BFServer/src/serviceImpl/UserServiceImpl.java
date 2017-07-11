package serviceImpl;

import service.UserService;
import user.UserManager;

import java.rmi.RemoteException;

public class UserServiceImpl implements UserService {

    private UserManager userManager = UserManager.getInstance();
    @Override
    public boolean login(String username, String password) throws RemoteException {

        return userManager.login(username, password);
    }

    @Override
    public boolean logout(String username) throws RemoteException {

        return userManager.logout(username);
    }

    @Override
    public boolean signUp(String username, String password) throws RemoteException {

        return userManager.signUp(username, password);
    }
}
