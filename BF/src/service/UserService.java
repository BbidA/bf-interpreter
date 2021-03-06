//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    boolean login(String username, String password) throws RemoteException;

    boolean logout(String username) throws RemoteException;

    boolean signUp(String username, String password) throws RemoteException;

}