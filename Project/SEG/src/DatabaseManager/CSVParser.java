package DatabaseManager;

import Commons.ClickEntry;
import Commons.ImpressionEntry;
import Commons.ServerEntry;
import Commons.UserEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Marco-Edoardo Palma
 */

/*
 * This class will take 3 file representing the 3 csv files, and will parse them into
 * the database (mapped by the DataExchange)
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

    public synchronized void parseAll() throws Exception
    {
        this.dataExchange.dropAll_noSettings();
        parseImpressionLogFile();
        parseClickLogFile();
        parseServerLogFile();
    }

    private void parseImpressionLogFile() throws FileNotFoundException, IOException, Exception
    {
        // 0     1      2      3       4         5             6
        //DATE | ID | Gender | Age | Income | Context | impressionCost
        // im    us     us      us     us        im           im

        try (BufferedReader br = new BufferedReader(new FileReader(this.impressionLogFile)))
        {
            Set<String> addedUserIds = new HashSet<String>();//no duplicates!

            String line = br.readLine(); //skip first line
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                if (!addedUserIds.contains(tk[1]))
                {
                    this.dataExchange.insertUserStmt(new UserEntry(tk[1],
                            UserEntry.Gender.valueOf(tk[2]),
                            parseAge(tk[3]),
                            UserEntry.Income.valueOf(tk[4])));
                    addedUserIds.add(tk[1]);
                }
                if (!tk[0].equals("n/a"))
                {
                    this.dataExchange.insertImpressionStmt(new ImpressionEntry(ImpressionEntry.AUTO_INDEX,
                            tk[1],
                            Stringifiable.simpleDateFormat.parse(tk[0]),
                            parseContext(tk[5]),
                            Double.parseDouble(tk[6])));
                }
            }
        }
    }

    private void parseClickLogFile() throws FileNotFoundException, IOException, Exception
    {
        // 0     1      2     
        //DATE | ID | clickcost

        try (BufferedReader br = new BufferedReader(new FileReader(this.clickLogFile)))
        {
            String line = br.readLine(); //skip first line
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a"))
                {
                    this.dataExchange.insertClickStmt(new ClickEntry(ClickEntry.AUTO_INDEX,
                            tk[1],
                            Stringifiable.simpleDateFormat.parse(tk[0]),
                            Double.parseDouble(tk[2])));
                }
            }
        }
    }

    private void parseServerLogFile() throws FileNotFoundException, IOException, Exception
    {
        //     0       1      2           3            4
        //entryDATE | ID | exitDate | pagesViewed | conversions

        try (BufferedReader br = new BufferedReader(new FileReader(this.serverLogfile)))
        {
            String line = br.readLine(); //skip first line
            while ((line = br.readLine()) != null)
            {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a") && !tk[2].equals("n/a"))
                {
                    this.dataExchange.insertServerStmt(new ServerEntry(ServerEntry.AUTO_INDEX,
                            tk[1],
                            Stringifiable.simpleDateFormat.parse(tk[0]),
                            Stringifiable.simpleDateFormat.parse(tk[2]),
                            Integer.parseInt(tk[3]),
                            ServerEntry.Conversion.valueOf(tk[4])));
                }
            }
        }
    }

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

    private static ImpressionEntry.Context parseContext(String string)
    {
        if (string.equals("Social Media"))
        {
            return ImpressionEntry.Context.SocialMedia;
        }
        return ImpressionEntry.Context.valueOf(string);
    }
}
