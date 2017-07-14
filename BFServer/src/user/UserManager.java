package user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by liao on 2017/6/5.
 */
public class UserManager {

    private static Map<String, User> registeredUsers;
    //    private static final String FILE_PATH = UserManager.class.getResource("/user_db.ser").getFile();
    private static final String JSON_FILE_PATH = UserManager.class.getResource("/user_info.json").getFile();
    private static ArrayList<User> onlineUsers = new ArrayList<>();
    private static Gson gson = new Gson();

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
        Type mapType = new TypeToken<Map<String, User>>() {}.getType();
        try {
            registeredUsers = gson.fromJson(new FileReader(JSON_FILE_PATH), mapType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
//            //noinspection unchecked
//            registeredUsers = (Map<String, User>) objectInputStream.readObject();
//            if (registeredUsers == null) {
//                registeredUsers = new HashMap<>();
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
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

        try (PrintWriter writer = new PrintWriter(JSON_FILE_PATH)) {
            writer.print(gson.toJson(registeredUsers));

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
//            objectOutputStream.writeObject(registeredUsers);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
