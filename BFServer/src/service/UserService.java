//需要客户端的Stub
package service;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    boolean login(String username, String password) throws RemoteException;

    boolean logout(String username) throws RemoteException;

    boolean signUp(String username, String password) throws RemoteException;

}
