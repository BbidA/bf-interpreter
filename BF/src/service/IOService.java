//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface IOService extends Remote {
    boolean writeFile(String file, String userId, String fileName) throws RemoteException;

    String readFile(String userId, String fileName) throws RemoteException;

    String readFile(String userId, String fileName, String version) throws RemoteException;

    Map<String, ArrayList<String>> readFileMap(String userId) throws RemoteException;

    ArrayList<String> getVersions(String userId, String fileName) throws RemoteException;
}
