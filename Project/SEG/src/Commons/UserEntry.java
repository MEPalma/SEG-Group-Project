package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class UserEntry implements Stringifiable
{
    public static enum Gender {Male, Famale};
    public static enum Age {Age_less_than_25, Age_25_34, Age_35_44, Age_45_54, Age_more_than_54};
    public static enum Income {Low, Medium, Hight};
    
    //IN SAME ORDER AS IN DB TABLE
    private final int id;
    private Gender gender;
    private Age age;
    private Income income;

    public UserEntry(int id, Gender gender, Age age, Income income)
    {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }
    
    /*
        implementation of Stringifiable.java
    */
    @Override
    public String getDBContent()
    {
        String is = "', '";
        return ("'" + this.id + is + this.gender + is + this.age + is + this.income + "'");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                UserEntry tmp = new UserEntry(
                    resultSet.getInt("id"),
                    Gender.valueOf(resultSet.getString("gender")),
                    Age.valueOf(resultSet.getString("age")),
                    Income.valueOf(resultSet.getString("income"))
                );
                resultSet.close();
                return tmp;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getId()
    {
        return id;
    }

    public Gender getGender()
    {
        return gender;
    }

    public Age getAge()
    {
        return age;
    }

    public Income getIncome()
    {
        return income;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public void setAge(Age age)
    {
        this.age = age;
    }

    public void setIncome(Income income)
    {
        this.income = income;
    }
    
}
