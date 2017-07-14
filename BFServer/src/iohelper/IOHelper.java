package iohelper;

import user.User;

import java.io.*;

/**
 * Created by liao on 2017/6/30.
 */
public class IOHelper {

    static final String DIR_PATH = VersionHelper.class.getResource("/userfiles").getFile() + "/";
    public static final String SEPARATOR = "_";

    public static boolean writeFile(User requester, String file, String fileName) {
        String fileRealName = requester.getUserName() + SEPARATOR + fileName;
        File f = new File(DIR_PATH, fileRealName);
        try {

            if (requester.haveFile(fileRealName)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                StringBuilder code = new StringBuilder();
                String tmp;
                while ((tmp = bufferedReader.readLine()) != null) {
                    code.append(tmp);
                }
                //保存的内容没有改变则不写入
                if (code.toString().equals(file)) {
                    System.out.println(true);
                    return true;
                } else {
                    FileWriter fw = new FileWriter(f, false);
                    fw.write(file);
                    fw.flush();
                    fw.close();
                    VersionHelper.versionWrite(file, fileName, requester);
                    return true;
                }
            } else {

                //新的文件
                FileWriter fw = new FileWriter(f, false);
                fw.write(file);
                fw.flush();
                fw.close();
                requester.addNewFile(fileRealName);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFile(String userId, String fileName) {
        File target = new File(DIR_PATH, userId + SEPARATOR + fileName);
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(target))) {
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                result.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(DIR_PATH);
    }
}
