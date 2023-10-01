package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for the clients. Each client has a key, a name, an address, a status
 * the value of their purchases and the actual amount spend by them in their 
 * purchases.
 */
public class Client extends ProductObserver implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 12L;

    private String _clientKey;
    private String _name;
    private String _address;
    private ClientState _state = new NormalState(this, 0);

    private List<Sale> _transactions = new ArrayList<Sale>();
    private List<Notification> _notifications = new ArrayList<Notification>();


    public Client(String clientKey, String clientName, String clientAddress){
        _clientKey = clientKey;
        _name = clientName;
        _address = clientAddress;
    }

    public String getClientKey(){
        return _clientKey;
    }

    public void setState(ClientState state){
        _state = state;
    }

    public void downgradeState(int delayedDays){
        _state.downgrade(delayedDays);
    }

    public void addPoints(int points){
        _state.addPoints(points);
    }

    public void updateLastTransactionToPayValue(int currentDate){
        Sale t = _transactions.get(_transactions.size()-1);
        int period = t.getProduct().getPaymentPeriod();
        _state.updateTransactionToPayValue(t, currentDate, period);
    }

    public double getLastTransactionToPayValue(){
        return _transactions.get(_transactions.size()-1).getToPayValue();
    }

    public void updateTransactionsToPayValue(int currentDate){
        for (Sale t: _transactions){
            int period = t.getProduct().getPaymentPeriod();
            _state.updateTransactionToPayValue(t, currentDate, period);
        }
    }

    public void addTransaction(Sale t){
        _transactions.add(t);
    }

    public String showAllTransactions(){
        String list = "";
        for (Transaction t: _transactions)
            list += t.toString();
        return list;
    }

    public String showPaidTransactions(){
        String list = "";
        for (Sale sale: _transactions){
            if (sale.isPaid())
                list += sale.toString();
        }
        return list;
    } 

    public void receiveNotification(Notification n){
        _notifications.add(n);
    }

    public String showNotifications(){
        String list = "";
        for (Notification notification: _notifications)
            list += notification.send();
        _notifications.clear();
        return list;
    }

    public double getTransactionsToPayValue(){
        double sum = 0;
        for (Sale t: _transactions){
            sum += t.getToPayValue();
        }
        return sum;
    }

    public double getTransactionsBaseValue(){
        double sum = 0;
        for (Sale t: _transactions){
            sum += t.getBaseValue();
        }
        return sum;
    }

    public double getPaidValue(){
        double sum = 0;
        for (Sale t: _transactions){
            if (t.isPaid())
                sum += t.getToPayValue();
        }
        return sum;
    }


    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%d|%d\n", _clientKey, _name, _address, _state, (int)getTransactionsBaseValue(), (int)getPaidValue());
    }
    
}
