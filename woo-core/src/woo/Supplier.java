package woo;

import java.io.Serializable;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Class for the suppliers. Each supplier has a key, a name, an address and
 * it's transactions state (on or off).
 */
public class Supplier implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 1L;

    private String _supplierKey;
    private String _name;
    private String _address;
    private boolean _transactionsState = true;

    private Map<Integer, Order> _orders = new LinkedHashMap<Integer, Order>();

    public Supplier(String supplierKey, String supplierName, String supplierAddress){
        _supplierKey = supplierKey;
        _name = supplierName;
        _address = supplierAddress;
    }

    public boolean toggleTransactions(){
        if (_transactionsState == true)
            _transactionsState = false;
        else
            _transactionsState = true;
        return _transactionsState;
    }

    public String getTransactionsState(){
        if (_transactionsState == true)
            return "SIM";
        else
            return "NÃO"; 
    }

    public boolean isAvailable(){
        return _transactionsState;
    }

    public Order getTransaction(int transactionKey){
        return _orders.get(transactionKey);
    }

    public void addTransaction(int transactionKey, Order o){
        _orders.put(transactionKey, o);
    }

    public void removeTransaction(int transactionKey){
        _orders.remove(transactionKey);
    }

    public String showTransactions(){
        String list = "";
        for (Map.Entry<Integer, Order> entry: _orders.entrySet()){
            Order order = entry.getValue();
            list += order.toString();
        }
        return list;
    }


    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s\n", _supplierKey, _name, _address, getTransactionsState());
    }
}