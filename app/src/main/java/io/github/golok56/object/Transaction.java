package io.github.golok56.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class store the information about transaction such as
 * amount, date, type, and description of transaction
 */
public class Transaction implements Parcelable {

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    /**
     * Store id of transaction on database
     */
    private long _id;

    /**
     * Store the total amount of transaction
     */
    private String mAmount;
    /**
     * Store the date of transaction
     */
    private String mDate;
    /**
     * Store the description of the transaction
     */
    private String mDescription;
    /**
     * Store the type of transaction
     */
    private String mType;

    /**
     * Create an instance of {@link Transaction}
     *
     * @param amount the total amount of transaction
     * @param desc   the description of the transaction
     * @param date   the date of transaction
     * @param type   the type of transaction
     */
    public Transaction(long id, String amount, String desc, String date, String type) {
        _id = id;
        mAmount = amount;
        mDescription = desc;
        mDate = date;
        mType = type;
    }

    protected Transaction(Parcel in) {
        _id = in.readLong();
        mAmount = in.readString();
        mDate = in.readString();
        mDescription = in.readString();
        mType = in.readString();
    }

    /**
     * Return if of transaction
     */
    public long getID() {
        return _id;
    }

    /**
     * Return the total amount of transaction
     */
    public String getAmount() {
        return mAmount;
    }

    /**
     * Return the type of transaction
     */
    public String getType() {
        return mType;
    }

    /**
     * Return the date of transaction
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Return the description of transaction
     */
    public String getDescription() {
        return mDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(mAmount);
        dest.writeString(mDate);
        dest.writeString(mDescription);
        dest.writeString(mType);
    }
}
