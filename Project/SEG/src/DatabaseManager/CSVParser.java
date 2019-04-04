package DatabaseManager;

import Commons.Enums;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will take 3 file representing the 3 csv files, and will parse them
 * into the database (mapped by the DataExchange)
 */
public class CSVParser {

    private final MainController mainController;
    private final DataExchange dataExchange;
    private File impressionLogFile;
    private File clickLogFile;
    private File serverLogfile;

    private  Map<Integer, Long> sourceIdToNewId;

    public CSVParser(MainController mainController, File impressionLogFile, File clickLogFile, File serverLogfile) {
        this.mainController = mainController;
        this.dataExchange = this.mainController.getDataExchange();

        this.impressionLogFile = impressionLogFile;
        this.clickLogFile = clickLogFile;
        this.serverLogfile = serverLogfile;
    }

    /**
     * This method is needed as the format of the csv cannot comply with the
     * enumeration rules in java.
     *
     * @param string
     * @return
     */
    private static Enums.Age parseAge(String string) {
        if (string.equals("<25")) {
            return Enums.Age.Age_less_than_25;
        } else if (string.equals("25-34")) {
            return Enums.Age.Age_25_34;
        } else if (string.equals("35-44")) {
            return Enums.Age.Age_35_44;
        } else if (string.equals("45-54")) {
            return Enums.Age.Age_45_54;
        } else {
            return Enums.Age.Age_more_than_54;
        }
    }

    /**
     * This method clears the database, then parses all the csv files into.
     * temporarily.
     * <p>
     * csv is parsed to allow entries to be compiled into instances of the
     * objects in the Commons package.
     *
     * @throws Exception
     */
    public synchronized void parseAll(String campaignName) throws Exception {
        this.sourceIdToNewId = new HashMap<Integer, Long>();

        int newCampaignID = this.dataExchange.insertNewCampaign(campaignName);

        this.dataExchange.setForiegnKeyPragma(false);
        this.dataExchange.setAutoCommit(false);

        Statement insertionStatement = this.dataExchange.getSqlStatement();

        parseImpressionLogFile(insertionStatement, newCampaignID);
        parseClickLogFile(insertionStatement, newCampaignID);
        parseServerLogFile(insertionStatement, newCampaignID);

        this.dataExchange.writeSqlStatement(insertionStatement);
        this.dataExchange.commitNow();

        this.dataExchange.setForiegnKeyPragma(true);
        this.dataExchange.setAutoCommit(true);

        sourceIdToNewId.clear();
        System.gc();

        JOptionPane.showMessageDialog(null, "Successfully updated the library", "Alert", JOptionPane.INFORMATION_MESSAGE);
    }

    private void parseImpressionLogFile(Statement sqlStmt, int campaignID) throws SQLException, IOException {
        // 0     1      2       3       4         5            6
        //DATE | ID | Gender | Age | Income | Context | impressionCost
        // im    us     us      us     us        im           im

        try (BufferedReader br = new BufferedReader(new FileReader(this.impressionLogFile), 40000)) {
            long userIdCounter = 0;

            br.readLine();//skip first line

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tk = line.split(",");
                
                if (!tk[0].equals("n/a")) {
                    if (!sourceIdToNewId.containsKey(tk[1])) {
                        sqlStmt.addBatch("INSERT INTO USERS VALUES ('" + userIdCounter + "', '" + campaignID + "', '" + Enums.GenderToInt(Enums.Gender.valueOf(tk[2])) + "', '" + Enums.AgeToInt(parseAge(tk[3])) + "', '" + Enums.IncomeToInt(Enums.Income.valueOf(tk[4])) + "');");
                        sourceIdToNewId.put(tk[1].hashCode(), userIdCounter);
                        userIdCounter++;
                    }
                    sqlStmt.addBatch("INSERT INTO IMPRESSION_LOGS VALUES (NULL, '" + sourceIdToNewId.get(tk[1].hashCode()) + "', '" + campaignID + "', '" + Stringifiable.dateToSeconds(tk[0]) + "', '" + Enums.ContextToInt(Enums.Context.valueOf(tk[5].replace(" ", ""))) + "', '" + Double.parseDouble(tk[6]) + "');");
                }
            }

            br.close();
        }
    }

    private void parseClickLogFile(Statement sqlStmt, int campaignID) throws SQLException, IOException {
        // 0     1      2
        //DATE | ID | clickcost

        try (BufferedReader br = new BufferedReader(new FileReader(this.clickLogFile), 40000)) {
            br.readLine();//skip first line
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a")) {
                    sqlStmt.addBatch("INSERT INTO CLICK_LOGS VALUES (NULL, '" + sourceIdToNewId.get(tk[1].hashCode()) + "', '" + campaignID  + "','" + Stringifiable.dateToSeconds(tk[0]) + "','" + Double.parseDouble(tk[2]) + "');");
                }
            }
            br.close();
        }
    }

    private void parseServerLogFile(Statement sqlStmt, int campaignID) throws SQLException, IOException {
        //     0       1      2           3            4
        //entryDATE | ID | exitDate | pagesViewed | conversions

        try (BufferedReader br = new BufferedReader(new FileReader(this.serverLogfile), 40000)) {
            br.readLine(); //skip first line
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tk = line.split(",");
                if (!tk[0].equals("n/a") && !tk[2].equals("n/a")) {
                    sqlStmt.addBatch("INSERT INTO SERVER_LOGS VALUES (NULL,'" + sourceIdToNewId.get(tk[1].hashCode())  + "', '" + campaignID + "','" + Stringifiable.dateToSeconds(tk[0]) + "','" + Stringifiable.dateToSeconds(tk[2]) + "','" + Integer.parseInt(tk[3]) + "','" + Enums.ConversionToInt(Enums.Conversion.valueOf(tk[4])) + "');");
                }
            }
            br.close();
        }
    }

}
