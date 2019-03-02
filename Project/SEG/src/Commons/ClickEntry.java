package Commons;

import DatabaseManager.Stringifiable;

import java.util.Date;

public class ClickEntry implements Stringifiable {
    public static int AUTO_INDEX = -1;

    //IN THE SAME ORDER AS DECLARATION IN DB TABLE
    private int id;
    private String userId;
    private Date date;
    private Number clickCost;

    public ClickEntry(int id, String userId, Date date, Number clickCost) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.clickCost = clickCost;
    }

    public ClickEntry() {
        this(AUTO_INDEX, "", new Date(), 0);
    }

    @Override
    public String stringify() {
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX) {
            tmp = "NULL, '";
        } else {
            tmp = this.id + ", '";
        }
        return (tmp
                + this.userId + is
                + globalDateFormat.format(this.date) + is
                + this.clickCost.doubleValue()
                + "'");
    }

    @Override
    public String toString() {
        return stringify();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClickEntry) {
            if (this.id == ((ClickEntry) obj).id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public Date getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public Number getClickCost() {
        return clickCost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setClickCost(Number clickCost) {
        this.clickCost = clickCost;
    }

}
