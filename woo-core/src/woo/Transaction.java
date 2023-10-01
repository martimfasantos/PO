package woo;

import java.io.Serializable;

public abstract class Transaction implements Serializable {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 1234567L;

    private int _transactionKey;
    private int _baseValue = 0;
    private int _paymentDate;
    private boolean _paid = false;

    public Transaction(int transactionKey){
    	_transactionKey = transactionKey;
    }

    public Transaction(int transactionKey, int baseValue){
    	this(transactionKey);
    	_baseValue = baseValue;
    }

    public int getTransactionKey(){
    	return _transactionKey;
    }

    public int getBaseValue(){
    	return _baseValue;
    }

    public boolean isPaid(){
        return _paid;
    }

    public void setAsPaid(int currentDate){
    	_paid = true;
    	_paymentDate = currentDate;
    }

    public abstract double pay(int currentDate);

    public int getPaymentDate(){
    	return _paymentDate;
    }
    
    public void increaseBaseValue(int amount){
    	_baseValue += amount;
    }

    public abstract String toString();
    
}