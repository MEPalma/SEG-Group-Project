package DatabaseManager;

/*
 * Created by Marco-Edoardo Palma.
 */

import java.sql.ResultSet;

public interface Stringifiable
{
    static String parse(String string)
    {
        return string.replace("'", "''").replace("\n", "").replace("-", "").trim();
    }

    public String getDBContent();

    public Object parseDBContent(ResultSet resultSet);
}
