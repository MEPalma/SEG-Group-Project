package DatabaseManager;

/*
 * Created by Marco-Edoardo Palma.
 */

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public interface Stringifiable
{
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
//    static String parseDate(String string)
//    {
//        return string.trim().replace("-", "@").trim();
//    }
//    static String parseDateBack(String string)
//    {
//        return string.replace("''", "'").replace("@", "-");
//    }

    public String getDBContent();

    public Object parseDBContent(ResultSet resultSet);
}
