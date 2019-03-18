package Commons;

import DatabaseManager.Stringifiable;

import java.util.Date;

public class ServerEntry implements Stringifiable {
    public static int AUTO_INDEX = -1;

    public static enum Conversion {Yes, No, Unknown}

    private int id;
    private String userId;
    private Date entryDate;
    private Date exitDate;
    private Number pagesViewed;
    private Conversion conversion;

    public ServerEntry(int id, String userId, Date entryDate, Date exitDate, Number pagesViewed, Conversion conversion) {
        this.id = id;
        this.userId = userId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    public ServerEntry() {
        this(AUTO_INDEX, "", new Date(), new Date(), 0, Conversion.Unknown);
    }

    @Override
    public String stringify() {
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX) tmp = "NULL, '";
        else tmp = this.id + is;
        return (tmp +
                this.userId + is +
                globalDateFormat.format(this.entryDate) + is +
                globalDateFormat.format(this.exitDate) + is +
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

    public Date getExitDate() {
        return exitDate;
    }

    public String getUserId() {
        return userId;
    }

    public Number getPagesViewed() {
        return pagesViewed;
    }

    public Conversion getConversion() {
        return conversion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public void setPagesViewed(Number pagesViewed) {
        this.pagesViewed = pagesViewed;
    }

    public void setConversion(Conversion conversion) {
        this.conversion = conversion;
    }

}
