package Commons;

import DatabaseManager.Stringifiable;

import java.util.Date;

public class ImpressionEntry implements Stringifiable {

    public static int AUTO_INDEX = -1;
    //IN SAME ORDER AS IN DB TABLE
    private int id;
    private int campaignId;
    private long userId;
    private Date date;
    private Enums.Context context;
    private Number impressionCost;

    public ImpressionEntry(int id, long userId, int campaignId, Date date, Enums.Context context, Number impressionCost) {
        this.id = id;
        this.campaignId = campaignId;
        this.userId = userId;
        this.date = date;
        this.context = context;
        this.impressionCost = impressionCost;
    }

    public ImpressionEntry() {
        this(AUTO_INDEX, AUTO_INDEX, AUTO_INDEX, new Date(), Enums.Context.Unknown, 0);
    }

    @Override
    public String stringify() {
        if (this.campaignId == AUTO_INDEX) return null;
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX) {
            tmp = "NULL, '";
        } else {
            tmp = "'" + this.id + is;
        }
        return (tmp
                + this.userId + is
                + this.campaignId + is
                + Stringifiable.dateToSeconds(this.date) + is
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Enums.Context getContext() {
        return context;
    }

    public void setContext(Enums.Context context) {
        this.context = context;
    }

    public Number getImpressionCost() {
        return impressionCost;
    }

    public void setImpressionCost(Number impressionCost) {
        this.impressionCost = impressionCost;
    }

}
