package woo;

public class Sale extends Transaction {
    
    /** Serial number for serialization. */
    private static final long serialVersionUID = 1234597L;

    private Client _client;
    private int _deadLine;
    private Product _product;
    private int _amount;
    private double _toPayValue = 0;

    public Sale(int transactionKey, Client client, int deadLine, Product product,
            int amount, int baseValue){
        super(transactionKey, baseValue);
        _client = client;
        _deadLine = deadLine;
        _product = product;
        _amount = amount;
        _toPayValue = baseValue;
    }

    public Product getProduct(){
        return _product;
    }

    public int getDeadLine(){
        return _deadLine;
    }

    public double getToPayValue(){
        return _toPayValue;
    }

    public void updateToPayValue(double toPayValue){
        _toPayValue = toPayValue;
    }

    @Override
    public double pay(int currentDate){
        if (!isPaid()){
            if (currentDate < _deadLine)
                _client.addPoints((int)(10*getToPayValue()));
            else if (currentDate > _deadLine)
                _client.downgradeState(currentDate - _deadLine);
            setAsPaid(currentDate);
            return _toPayValue;
        }
        return 0;
    }

    @Override
    public String toString(){
        if (isPaid())
            return String.format("%d|%s|%s|%d|%d|%d|%s|%s\n", getTransactionKey() , _client.getClientKey(), _product.getProductKey(), _amount, 
                    getBaseValue(), Math.round(getToPayValue()), _deadLine, getPaymentDate());
        else
            return String.format("%d|%s|%s|%d|%d|%d|%s\n", getTransactionKey() , _client.getClientKey(), _product.getProductKey(), _amount, 
                    getBaseValue(), Math.round(getToPayValue()), _deadLine);

    }
}