package Commons;

import DatabaseManager.Stringifiable;

import java.util.Date;

public class ServerEntry implements Stringifiable {
    public static int AUTO_INDEX = -1;
    private int id;
    private int campaignId;
    private long userId;
    private Date entryDate;
    private Date exitDate;
    private Number pagesViewed;
    private Enums.Conversion conversion;

    public ServerEntry(int id, long userId, int campaignId, Date entryDate, Date exitDate, Number pagesViewed, Enums.Conversion conversion) {
        this.id = id;
        this.userId = userId;
        this.campaignId = campaignId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    public ServerEntry() {
        this(AUTO_INDEX, AUTO_INDEX, AUTO_INDEX, new Date(), new Date(), 0, Enums.Conversion.Unknown);
    }

    @Override
    public String stringify() {
        if (this.userId == AUTO_INDEX) return null;
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX) tmp = "NULL, '";
        else tmp = this.id + is;
        return (tmp +
                this.userId + is +
                this.campaignId + is +
                Stringifiable.dateToSeconds(this.entryDate) + is +
                Stringifiable.dateToSeconds(this.exitDate) + is +
                this.pagesViewed.intValue() + is +
                this.conversion +
                "'");
    }

    @Override
    public String toString() {
        return stringify();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerEntry)
            if (this.id == ((ServerEntry) obj).id) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Number getPagesViewed() {
        return pagesViewed;
    }

    public void setPagesViewed(Number pagesViewed) {
        this.pagesViewed = pagesViewed;
    }

    public Enums.Conversion getConversion() {
        return conversion;
    }

    public void setConversion(Enums.Conversion conversion) {
        this.conversion = conversion;
    }

    public void setId(int id) {
        this.id = id;
    }

}
