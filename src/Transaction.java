package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class Transaction {
    protected Customer accountHolder;
    Dictionary<String,Double> startingBalance = new Hashtable<>();
    Dictionary<String,Double> currentBalance = new Hashtable<>();
    List<String> transactions = new ArrayList<>();
    Date todaysDate = new Date();
    Dictionary<String,Account> accounts;

    public void setInformation(Customer accountHolder){
        this.accountHolder = accountHolder;
        this.accounts = accountHolder.getAccounts();
        double checkingAccountStartingBalance = accounts.get("checking").getStartingBalance();
        double savingsAccountStartingBalance = accounts.get("saving").getStartingBalance();
        double creditAccountStartingBalance = accounts.get("credit").getStartingBalance();

        startingBalance.put("checking", checkingAccountStartingBalance);
        startingBalance.put("saving", savingsAccountStartingBalance);
        startingBalance.put("credit", creditAccountStartingBalance);
    }

    public void setCurrentBalance(Customer accountHolder){
        double checkingAccountCurrentBalance = accounts.get("checking").getBalance();
        double savingsAccountCurrentBalance = accounts.get("saving").getBalance();
        double creditAccountCurrentBalance = accounts.get("credit").getBalance();

        startingBalance.put("checking", checkingAccountCurrentBalance);
        startingBalance.put("saving", savingsAccountCurrentBalance);
        startingBalance.put("credit", creditAccountCurrentBalance);
    }

    public void addTransactionEntry(String entry){
        transactions.add(entry);
    }
}
