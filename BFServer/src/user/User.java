package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import static iohelper.IOHelper.SEPARATOR;

/**
 * Created by liao on 2017/6/3.
 */
public class User implements Serializable {


    private String userName;
    private String password;
    private Map<String, ArrayList<String>> userFileMap;

    User(String userName, String password, Map<String, ArrayList<String>> userFileMap) {
        this.userName = userName;
        this.password = password;
        this.userFileMap = userFileMap;

    }

    boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public boolean haveFile(String fileName) {
        return userFileMap.containsKey(fileName);
    }

    public void addNewFile(String fileName) {
        userFileMap.put(fileName, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return userFileMap != null ? userFileMap.equals(user.userFileMap) : user.userFileMap == null;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }

    public String getUserName() {
        return userName;
    }

    public Map<String, ArrayList<String>> getUserFileMap() {
        return userFileMap;
    }

    public ArrayList<String> getVersions(String baseFileName) {
        return userFileMap.get(userName + SEPARATOR + baseFileName);
    }
}
