package woo;

import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Order extends Transaction {
	
	/** Serial number for serialization. */
    private static final long serialVersionUID = 1234117L;

    private String _supplierKey;

    private Map<String, Integer> _products = new LinkedHashMap<String, Integer>();

    public Order(int transactionKey, String supplierKey, int paymentDate){
        super(transactionKey);
    	_supplierKey = supplierKey;
        setAsPaid(paymentDate);
    }

    public Map<String, Integer> getProducts(){
        return _products;
    }

    public void addProduct(Product product, int amount){
        _products.put(product.getProductKey(), amount);
        increaseBaseValue(amount*product.getPrice());
    }

    @Override
    public double pay(int currentDate){
        // order cannot be paid
        return 0;
    }

    @Override
    public String toString(){
    	String list = String.format("%d|%s|%d|%d\n", getTransactionKey(), _supplierKey, getBaseValue(), getPaymentDate());
        for (Map.Entry<String, Integer> product: _products.entrySet()){
            String productKey = product.getKey();
            int amount = product.getValue();
            list += String.format("%s|%d\n", productKey, amount);
        }
        return list;
    }
}
