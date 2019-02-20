package DatabaseManager;

/*
 * Created by Marco-Edoardo Palma.
 */

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public interface Stringifiable
{
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
    static String parse(String string)
    {
        return string.replace("'", "''").replace("\n", "").replace("-", "").trim();
    }

    public String getDBContent();

    public Object parseDBContent(ResultSet resultSet);
}
