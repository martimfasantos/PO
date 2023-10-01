package woo;

/**
 * Class for the product Book. A Book has the characteristics 
 * of a product plus a title, an author and an ISBN.
 */
public class Book extends Product {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 123456L;
    
    private String _title;
    private String _author;
    private String _ISBN;

    public Book(String productkey, String title, String author, String ISBN, String supplierkey, int price, int criticalvalue, int existenceValue){
        super(productkey, supplierkey, price, criticalvalue, existenceValue, 3);
        _title = title;
        _author = author;
        _ISBN = ISBN;
    }

    @Override
    public String toString() {
        return String.format("BOOK|%s|%s|%d|%d|%d|%s|%s|%s\n", super.getProductKey(), super.getSupplierKey(), super.getPrice(), super.getCriticalValue(), super.getExistenceValue(), _title, _author, _ISBN);
    }
}
