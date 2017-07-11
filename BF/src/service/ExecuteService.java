package service;

import java.rmi.RemoteException;

/**
 * Created by liao on 2017/6/3.
 */
public interface ExecuteService {

    String execute(String code, String param, String suffix) throws RemoteException;
}
