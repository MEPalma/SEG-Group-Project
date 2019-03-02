package Main;

import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import java.text.ParseException;

import Gui.Gui;
import java.util.Locale;
import DatabaseManager.PathsManager;

import javax.xml.crypto.Data;

/**
 *
 * @author Group 31
 */
public class Main
{

    public static void main(String[] args) throws ParseException
    {
        Locale.setDefault(Locale.ENGLISH);
        DataExchange dataExchange = new DataExchange(new DatabaseManager());
        new Gui(dataExchange).setVisible(true);
        System.out.println(new PathsManager().getDB());

        /*
        SHOULD the first ones return just long?? Or is it fine float as well>
         */
//        dataExchange.getNumberOfImpressionsPerWeek();
//        dataExchange.getNumberOfClicksPerWeek();
//          dataExchange.getNumberOfUniques();
//        dataExchange.getNumberOfBounces();
//        dataExchange.getTotalCost();
//        dataExchange.getNumberOfImpressionsPerHour();
//          dataExchange.getNumberOfImpressionsPerWeek();
//        dataExchange.getNumberOfImpressionsPerDay();
//         dataExchange.getNumberOfClicksPerHour();
//        dataExchange.getNumberOfClicksPerDay();
//          dataExchange.getNumberOfUniquesPerDay();
//         dataExchange.getNumberOfUniquesPerWeek();
//         dataExchange.getNumberOfUniquesPerHour();
//            dataExchange.getNumberOfConversionsPerDay();
//        dataExchange.getNumberOfConversionsPerHour();
//        dataExchange.getNumberOfConversionsPerWeek();
//       dataExchange.getTotalCostPerDay();
//        dataExchange.getTotalCostPerHour();
//        dataExchange.getTotalCostPerWeek();
//        dataExchange.getCTRPerDay();
//        dataExchange.getCTRPerWeek();
        dataExchange.getCTRPerHour();
//        dataExchange.getCPCPerHour();
//        dataExchange.getCPCPerDay();
//        dataExchange.getCPCPerWeek();
//        dataExchange.getCPMPerDay();
//        dataExchange.getCPMPerWeek();
//        dataExchange.getCPMPerHour();
//        dataExchange.getBounceRatePerDay();
 //       dataExchange.getBounceRatePerWeek();
 //       dataExchange.getBounceRatePerHour();






//////////END OF PART 1///////////////////
//        String pattern = "yyyy-MM-dd hh:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        Date d = simpleDateFormat.parse("2015-01-20 16:12:47");
//        System.out.println(d.toString());
//        System.out.println(simpleDateFormat.format(new Date()));
//
//        DataExchange dataExchange = new DataExchange(new DatabaseManager());

//        File im = new File("/Users/mep/MEP2G17/Modules/COMP2211/Coursework/DataExample/2_week_campaign_2/impression_log.csv");
//        File cl = new File("/Users/mep/MEP2G17/Modules/COMP2211/Coursework/DataExample/2_week_campaign_2/click_log.csv");
//        File sr = new File("/Users/mep/MEP2G17/Modules/COMP2211/Coursework/DataExample/2_week_campaign_2/server_log.csv");

//        CSVParser parser = new CSVParser(dataExchange, im, cl, sr);
//        try
//        {
//            parser.parseAll();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        List<UserEntry> users = dataExchange.selectAllFrom_USERS();
//        System.out.println(users.size());
//        System.out.println(dataExchange.countAllFrom_USERS());
//        for (UserEntry x : users)
//            System.out.println(x.toString());
//
//        List<ClickEntry> clicks = dataExchange.selectAllFrom_CLICK_LOGS();
//        System.out.println(clicks.size());
//        System.out.println(dataExchange.countAllFrom_CLICK_LOGS());
//        for (ClickEntry cl : clicks)
//            System.out.println(cl.toString());
//
//        List<ImpressionEntry> impressions = dataExchange.selectAllFrom_IMPRESSION_LOGS();
//        System.out.println(impressions.size());
//        System.out.println(dataExchange.countAllFrom_IMPRESSION_LOGS());
//        for (ImpressionEntry im : impressions)
//            System.out.println(im.toString());
//
//        List<ServerEntry> server = dataExchange.selectAllFrom_SERVER_LOGS();
//        System.out.println(server.size());
//        System.out.println(dataExchange.countAllFrom_SERVER_LOGS());
//        for (ServerEntry x : server)
//            System.out.println(x.toString());

//        System.out.println(dataExchange.selectByIdFrom_USERS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_CLICK_LOGS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_IMPRESSION_LOGS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_SERVER_LOGS(1).toString());
//
//        System.out.println(dataExchange.selectByUserIdFrom_CLICK_LOGS(1).toString());
//        System.out.println(dataExchange.selectByUserIdFrom_IMPRESSION_LOGS(1).toString());
//        System.out.println(dataExchange.selectByUserIdFrom_SERVER_LOGS(1).toString());

//        dataExchange.close();
    }

}
