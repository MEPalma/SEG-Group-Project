package DatabaseManager;

/*
 * @author Marco-Edoardo Palma
 */

import java.io.File;
import java.io.IOException;

/*
 * This class is in charge of returning a always valid path to the database file,
 * independently from the os the app is running on.
 */
public class PathsManager {

    private String db;

    public PathsManager() {
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();

        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
        } else {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library";
        }

        this.db = workingDirectory + "/com.seggroup31.Dashboard/";

        File check = new File(db);
        if (!check.isDirectory()) {
            check.mkdirs();
        }

        this.db += "DashboardProject.database";

        check = new File(this.db);
        if (!check.exists()) {
            try {
                check.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDB() {
        return db;
    }

}
