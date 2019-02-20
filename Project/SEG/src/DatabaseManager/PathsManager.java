package DATABASE;

import java.io.File;
import java.io.IOException;

/*
 * @author marcoedoardopalma
 */
public class PathsManager
{

    private String db;
    private String invoiceMakerDir;

    private boolean isMac = false;

    public PathsManager()
    {
        String workingDirectory;
        //here, we assign the name of the OS, according to Java, to a variable...
        String OS = (System.getProperty("os.name")).toUpperCase();
        //to determine what the workingDirectory is.
        //if it is some version of Windows

        if (OS.contains("WIN"))
        {
            //it is simply the location of the "AppData" folder
            workingDirectory = System.getenv("AppData");
        } //Otherwise, we assume Linux or Mac
        else
        {
            //in either case, we would start in the user's home directory
            workingDirectory = System.getProperty("user.home");
            //if we are on a Mac, we are not done, we look for "Application Support"
            workingDirectory += "/Library";

            isMac = true;
        }
        //we are now free to set the workingDirectory to the subdirectory that is my folder

        this.db = workingDirectory + "/com.marcomadeit.com.Jackie/";

        File check = new File(db);
        if (!check.isDirectory()) check.mkdirs();

        this.db += "Jackie.db";

        check = new File(this.db);
        if (!check.exists())
        {
            try
            {
                check.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        this.invoiceMakerDir = workingDirectory + "/com.marcomadeit.com.Jackie/invoiceMakerDir/";
        check = new File(this.invoiceMakerDir);
        check.mkdir();
    }

    public String getDB()
    {
        return db;
    }

    public String getInvoiceMakerDir()
    {
        return this.invoiceMakerDir;
    }
}
