package woo;

/**
 * Class for the product Box. A Box has the characteristics of a product
 * plus the type do delivery service.
 */
public class Box extends Product {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 1234L;

    private ServiceType _serviceType;

    public Box(String productKey, ServiceType serviceType, String supplierKey, int price, int criticalValue, int existenceValue){
        super(productKey, supplierKey, price, criticalValue, existenceValue, 5);
        _serviceType = serviceType;
    }
    
    public Box(String productKey, ServiceType serviceType, String supplierKey, int price, int criticalValue, int existenceValue, int paymentPeriod){
        super(productKey, supplierKey, price, criticalValue, existenceValue, paymentPeriod);
        _serviceType = serviceType;
    }

    public String getServiceType(){
        return _serviceType.toString();
    }

    @Override
    public String toString() {
        return String.format("BOX|%s|%s|%d|%d|%d|%s\n", getProductKey(), getSupplierKey(), getPrice(), getCriticalValue(), getExistenceValue(), _serviceType.toString());
    }
    
}
