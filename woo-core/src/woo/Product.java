package woo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is an abstract class for products. Every product has a key, the product's supplier key,
 * a price, a critical value and an existance value. Subclasses refine this class
 * in accordance with the type of product.
 */
public abstract class Product implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 123L;
    
    private String _productKey;
    private String _supplierKey;
    private int _price;
    private int _criticalValue;
    private int _existenceValue;
    private int _paymentPeriod;

    private List<ProductObserver> _observers = new ArrayList<ProductObserver>();

    /**
     * Constructor for class Product.
     * 
     * @param productKey
     *          product key
     * @param supplierKey
     *          product's supplier key
     * @param price
     *          product's price
     * @param criticalValue
     *          product's critical value
     * @param existenceValue
     *          amount of products of that type that exist
     */
    public Product(String productKey, String supplierKey, int price, int criticalValue, int existenceValue, int paymentPeriod){
        _productKey = productKey;
        _supplierKey = supplierKey;
        _price = price;
        _criticalValue = criticalValue;
        _existenceValue = existenceValue;
        _paymentPeriod = paymentPeriod;
    }
    /**
     * @return the product key.
     */
    public String getProductKey(){
        return _productKey;
    }

    /**
     * @return the supplier key of that product's supplier.
     */
    public String getSupplierKey(){
        return _supplierKey;
    }

    /**
     * @return the product's price.
     */
    public int getPrice(){
        return _price;
    }

    /**
     * @return the product's critical value.
     */
    public int getCriticalValue(){
        return _criticalValue;
    }

    /**
     * @return the product's existance value.
     */
    public int getExistenceValue(){
        return _existenceValue;
    }

    public int getPaymentPeriod(){
        return _paymentPeriod;
    }

    public void removeStock(int amount){
        _existenceValue -= amount;
    }

    public void addStock(int amount){
        if (_existenceValue == 0)
            notifyObservers(new Notification("NEW", this));
        _existenceValue += amount;
    }

    /**
     * Changes the product's price to the number passed as
     * argument.
     * 
     * @param newprice
     */
    public void changePrice (int newprice){
        if (newprice < _price)
            notifyObservers(new Notification("BARGAIN", this));
        _price = newprice;

    }

    public boolean isObserver(Client c){
        return _observers.contains(c);
    }

    public void registerObserver(Client c){
        _observers.add(c);
    }

    public void removeObserver(Client c){
        int i = _observers.indexOf(c);
        if (i >= 0)
            _observers.remove(i);
    }

    public void notifyObservers(Notification n){
        for (ProductObserver observer: _observers)
            observer.receiveNotification(n);
    }

    public abstract String toString();

}
