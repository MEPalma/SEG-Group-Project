package DatabaseManager;

import Commons.UserEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.sql.Statement;

/**
 *
 * @author Marco-Edoardo Palma
 */
/**
 * This class will take 3 file representing the 3 csv files, and will parse them
 * into the database (mapped by the DataExchange)
 */
public class CSVParser
{

    private DataExchange dataExchange;
    private File impressionLogFile;
    private File clickLogFile;
    private File serverLogfile;

    public CSVParser(DataExchange dataExchange, File impressionLogFile, File clickLogFile, File serverLogfile)
    {
        this.dataExchange = dataExchange;
        this.impressionLogFile = impressionLogFile;
        this.clickLogFile = clickLogFile;
        this.serverLogfile = serverLogfile;
    }

    /**
     * This method clears the database, then parses all the csv files into
     * memory, then flushes the result into the database. During the process
     * pragma on foreign keys is disabled and commitments are set manually
     * temporarily.
     *
     * csv is parsed to allow entries to be compiled into instances of the
     * objects in the Commons package.
     *
     * @throws Exception
     */
    public synchronized void parseAll() throws Exception
    {
        this.dataExchange.dropAll_noSettings();

        this.dataExchange.setForiegnKeyPragma(false);
        this.dataExchange.setAutoCommit(false);

        Statement insertionStatement = this.dataExchange.getSqlStatement();

        parseImpressionLogFile(insertionStatement);
        parseClickLogFile(insertionStatement);
        parseServerLogFile(insertionStatement);

        this.dataExchange.writeSqlStatement(insertionStatement);
        this.dataExchange.commitNow();

        this.dataExchange.setAutoCommit(true);
        this.dataExchange.setForiegnKeyPragma(true);
    }

    private void parseImpressionLogFile(Statement sqlStmt) throws FileNotFoundException, IOException, Exception
    {
        // 0     1      2       3       4         5            6
        //DATE | ID | Gender | Age | Income | Context | impressionCost
        // im    us     us      us     us        im           im

        try (BufferedReader br = new BufferedReader(new FileReader(this.impressionLogFile), 40000))
        {
            Set<Integer> addedUserIds = new HashSet<Integer>();//no duplicates!

            br.readLine();//skip first line

            String line = "";
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                int userIdhash = tk[1].hashCode();
                if (!addedUserIds.contains(userIdhash))
                {
                    sqlStmt.addBatch("INSERT INTO USERS VALUES ('" + tk[1] + "', '" + tk[2] + "', '" + parseAge(tk[3]) + "', '" + tk[4] + "');");
                    addedUserIds.add(userIdhash);
                }
                if (!tk[0].equals("n/a"))
                {
                    sqlStmt.addBatch("INSERT INTO IMPRESSION_LOGS VALUES (NULL, '" + tk[1] + "', '" + tk[0] + "', '" + tk[5].replace(" ", "") + "', '" + Double.parseDouble(tk[6]) + "');");
                }

            }

            br.close();
        }
    }

    private void parseClickLogFile(Statement sqlStmt) throws FileNotFoundException, IOException, Exception
    {
        // 0     1      2     
        //DATE | ID | clickcost

        try (BufferedReader br = new BufferedReader(new FileReader(this.clickLogFile), 40000))
        {
            br.readLine();//skip first line
            String line = br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a"))
                {
                    sqlStmt.addBatch("INSERT INTO CLICK_LOGS VALUES (NULL, '" + tk[1] + "','" + tk[0] + "','" + Double.parseDouble(tk[2]) + "');");
                }
            }
            br.close();
        }
    }

    private void parseServerLogFile(Statement sqlStmt) throws FileNotFoundException, IOException, Exception
    {
        //     0       1      2           3            4
        //entryDATE | ID | exitDate | pagesViewed | conversions

        try (BufferedReader br = new BufferedReader(new FileReader(this.serverLogfile), 40000))
        {
            br.readLine(); //skip first line
            String line = "";
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a") && !tk[2].equals("n/a"))
                {
                    sqlStmt.addBatch("INSERT INTO SERVER_LOGS VALUES (NULL,'" + tk[1] + "','" + tk[0] + "','" + tk[2] + "','" + Integer.parseInt(tk[3]) + "','" + tk[4] + "');");
                }
            }
            br.close();
        }
    }

    /**
     * This method is needed as the format of the csv cannot comply with the
     * enumeration rules in java.
     *
     * @param string
     * @return
     */
    private static UserEntry.Age parseAge(String string)
    {
        if (string.equals("<25"))
        {
            return UserEntry.Age.Age_less_than_25;
        } else if (string.equals("25-34"))
        {
            return UserEntry.Age.Age_25_34;
        } else if (string.equals("35-44"))
        {
            return UserEntry.Age.Age_35_44;
        } else if (string.equals("45-54"))
        {
            return UserEntry.Age.Age_45_54;
        } else
        {
            return UserEntry.Age.Age_more_than_54;
        }
    }

//    private static ImpressionEntry.Context parseContext(String string)
//    {
//        if (string.equals("Social Media"))
//        {
//            return ImpressionEntry.Context.SocialMedia;
//        }
//        return ImpressionEntry.Context.valueOf(string);
//    }
}
