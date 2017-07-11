package serviceImpl;

import iohelper.IOHelper;
import iohelper.VersionHelper;
import service.IOService;
import user.User;
import user.UserManager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import static iohelper.IOHelper.SEPARATOR;

public class IOServiceImpl implements IOService {


    @Override
    public boolean writeFile(String file, String userId, String fileName) {

        User requester = UserManager.getInstance().getUserOnline(userId);
        return IOHelper.writeFile(requester, file, fileName);
    }

    @Override
    public String readFile(String userId, String fileName) {
        // TODO Auto-generated method stub

        return IOHelper.readFile(userId, fileName);
    }

    @Override
    public String readFile(String userId, String fileName, String version) throws RemoteException {
        return readFile(userId, fileName.split("\\.")[0] + SEPARATOR + version + "." + fileName.split("\\.")[1]);
    }

    @Override
    public Map<String, ArrayList<String>> readFileMap(String userId) {
        // TODO Auto-generated method stub

        return UserManager.getInstance().getUserOnline(userId).getUserFileMap();
    }

    @Override
    public ArrayList<String> getVersions(String userId, String fileName) throws RemoteException {
        return UserManager.getInstance().getUserOnline(userId).getVersions(fileName);
    }

    private void versionWrite(String file, String userId, String fileName) {
        //历史版本
        VersionHelper.versionWrite(file, fileName, UserManager.getInstance().getUserOnline(userId));
    }
}
