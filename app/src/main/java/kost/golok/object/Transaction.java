package kost.golok.object;

/**
 * This class store the information about transaction such as
 * amount, date, type, and description of transaction
 */
public class Transaction {

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
    public Transaction(String amount, String desc, String date, String type) {
        mAmount = amount;
        mDescription = desc;
        mDate = date;
        mType = type;
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

    /**
     * Return an array containing all the field
     */
    public String[] getContent() {
        return new String[]{mAmount, mDate, mDescription, mType};
    }
}
