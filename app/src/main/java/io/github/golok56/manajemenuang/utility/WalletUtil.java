package io.github.golok56.manajemenuang.utility;

/**
 * @author Satria Adi Putra
 */

class WalletUtil {
    static double revert(double currentWallet, double transactionAmount, boolean isIncome) {
        if (isIncome) {
            return currentWallet - transactionAmount;
        }

        return currentWallet + transactionAmount;
    }

    static double update(double currentWallet, double transactionAmount, boolean isIncome) {
        if(isIncome) {
            return currentWallet + transactionAmount;
        }

        return currentWallet - transactionAmount;
    }
}
