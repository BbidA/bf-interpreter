//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface IOService extends Remote {
    boolean writeFile(String file, String userId, String fileName) throws RemoteException;

    String readFile(String userId, String fileName) throws RemoteException;

    //读不同版本
    String readFile(String userId, String fileName, String version) throws RemoteException;

    Map<String, ArrayList<String>> readFileMap(String userId) throws RemoteException;

    //获取用户版本列表
    ArrayList<String> getVersions(String userId, String fileName) throws RemoteException;
}
