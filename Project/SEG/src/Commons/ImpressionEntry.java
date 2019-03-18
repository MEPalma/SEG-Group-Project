package Commons;

import DatabaseManager.Stringifiable;

import java.util.Date;

public class ImpressionEntry implements Stringifiable {

    public static int AUTO_INDEX = -1;
    //IN SAME ORDER AS IN DB TABLE
    private int id;
    private String userId;
    private Date date;
    private Context context;
    private Number impressionCost;
    public ImpressionEntry(int id, String userId, Date date, Context context, Number impressionCost) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.context = context;
        this.impressionCost = impressionCost;
    }

    public ImpressionEntry() {
        this(AUTO_INDEX, "", new Date(), Context.Unknown, 0);
    }

    @Override
    public String stringify() {
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX) {
            tmp = "NULL, '";
        } else {
            tmp = "'" + this.id + is;
        }
        return (tmp
                + this.userId + is
                + globalDateFormat.format(this.date) + is
                + this.context + is
                + this.impressionCost.doubleValue()
                + "'");
    }

    @Override
    public String toString() {
        return stringify();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImpressionEntry) {
            if (this.id == ((ImpressionEntry) obj).id) {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Number getImpressionCost() {
        return impressionCost;
    }

    public void setImpressionCost(Number impressionCost) {
        this.impressionCost = impressionCost;
    }

    public static enum Context {
        News, Shopping, SocialMedia, Travel, Hobbies, Blog, Unknown
    }

}
