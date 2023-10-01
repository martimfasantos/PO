package woo;

/**
 * Class for the product Container. A Container has the characteristics 
 * of a product plus the type do delivery service and the level of quality
 * of that service.
 */
public class Container extends Box {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 12345L;
    
    private ServiceLevel _serviceLevel;

    public Container(String productKey, ServiceType serviceType, ServiceLevel serviceLevel, String supplierKey, int price, int criticalValue, int existenceValue){
        super(productKey, serviceType, supplierKey, price, criticalValue, existenceValue, 8);
        _serviceLevel = serviceLevel;
    }

    @Override
    public String toString() {
        return String.format("CONTAINER|%s|%s|%d|%d|%d|%s|%s\n", getProductKey(), getSupplierKey(), getPrice(), getCriticalValue(), getExistenceValue(), getServiceType(), _serviceLevel.toString());
    }
}
