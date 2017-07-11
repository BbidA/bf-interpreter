package iohelper;

import user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static iohelper.IOHelper.SEPARATOR;


/**
 * Created by liao on 2017/6/30.
 */
public class VersionHelper {

    private static final int VERSION_LIMIT = 10;

    public static void versionWrite(String file, String fileName, User user) {

        Map<String, ArrayList<String>> userFileMap = user.getUserFileMap();
        String numOfVersionFile = new SimpleDateFormat("MMddHHmmss").format(new Date());
        String versionName = user.getUserName() + SEPARATOR + fileName.split("\\.")[0] + SEPARATOR + numOfVersionFile + "." + fileName.split("\\.")[1];

        try (FileWriter versionWriter = new FileWriter(new File(IOHelper.DIR_PATH, versionName))) {

            ArrayList<String> versions = userFileMap.get(user.getUserName() + SEPARATOR + fileName);
            if (versions.size() > VERSION_LIMIT) {
                versions.remove(0);
            }
            versions.add(versionName);
            versionWriter.write(file);
            versionWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
