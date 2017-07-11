package rmi;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService, ExecuteService {
    /**
     *
     */
    private static final long serialVersionUID = 4029039744279087114L;
    private IOService iOService;
    private UserService userService;
    private ExecuteService executeService;

    protected DataRemoteObject() throws RemoteException {
        iOService = new IOServiceImpl();
        userService = new UserServiceImpl();
        executeService = new ExecuteServiceImpl();
    }

    @Override
    public boolean writeFile(String file, String userId, String fileName) throws RemoteException {
        return iOService.writeFile(file, userId, fileName);
    }

    @Override
    public String readFile(String userId, String fileName) throws RemoteException {
        return iOService.readFile(userId, fileName);
    }

    @Override
    public String readFile(String userId, String fileName, String version) throws RemoteException {
        return iOService.readFile(userId, fileName, version);
    }

    @Override
    public Map<String, ArrayList<String>> readFileMap(String userId) throws RemoteException {
        return iOService.readFileMap(userId);
    }

    @Override
    public ArrayList<String> getVersions(String userId, String fileName) throws RemoteException {
        return iOService.getVersions(userId, fileName);
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        return userService.login(username, password);
    }

    @Override
    public boolean logout(String username) throws RemoteException {
        return userService.logout(username);
    }

    @Override
    public boolean signUp(String username, String password) throws RemoteException {
        return userService.signUp(username, password);
    }

    @Override
    public String execute(String code, String param, String suffix) throws RemoteException {
        return executeService.execute(code, param, suffix);
    }
}
