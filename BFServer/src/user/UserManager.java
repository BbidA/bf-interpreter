package user;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by liao on 2017/6/5.
 */
public class UserManager {

    private static Map<String, User> registeredUsers;
    private static final String FILE_PATH = UserManager.class.getResource("/user_db.ser").getFile();
    private static ArrayList<User> onlineUsers = new ArrayList<>();

    // 初始化代码
//    static {
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
//            objectOutputStream.writeObject(null);
//            objectOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static UserManager userManager = new UserManager();

    public static UserManager getInstance() {
        return userManager;
    }

    private UserManager() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            //noinspection unchecked
            registeredUsers = (Map<String, User>) objectInputStream.readObject();
            if (registeredUsers == null) {
                registeredUsers = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {

        Optional<User> tmp = nullSafeGetUserFromMap(username).filter(p -> p.checkPassword(password) && !userAlreadyOnline(username));
        tmp.ifPresent(onlineUsers::add);

        return tmp.isPresent();
    }

    public boolean logout(String username) {

        writeBackInfo();
        return onlineUsers.remove(registeredUsers.get(username));
    }

    public boolean signUp(String username, String password) {

        if (registeredUsers.containsKey(username)) {
            return false;
        } else if (password.equals("")) {
            return false;
        }

        registeredUsers.put(username, new User(username, password, new HashMap<>()));
        writeBackInfo();
        return true;
    }

    public User getUserOnline(String username) {

        for (User user : onlineUsers) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public Optional<User> nullSafeGetUserFromMap(String username) {

        return Optional.ofNullable(registeredUsers.get(username));
    }

    private boolean userAlreadyOnline(String username) {

        return getUserOnline(username) != null;
    }

    private void writeBackInfo() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            objectOutputStream.writeObject(registeredUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
