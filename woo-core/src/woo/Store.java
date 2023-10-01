package woo;

import woo.exceptions.*;
import java.io.*;
import java.util.TreeMap;
import java.util.Map;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Store Clients */
  private Map<String, Client> _clients = new TreeMap<String, Client>();

  /** Store suppliers */
  private Map<String, Supplier> _suppliers = new TreeMap<String, Supplier>();

  /** Store products */
  private Map<String, Product> _products = new TreeMap<String, Product>();

  /** Store transactions */
  private Map<Integer, Transaction> _transactions = new TreeMap<Integer, Transaction>();

  /** Transaction Key */
  private int _transactionKey = 0;

  /* Available Balance */
  private double _availableBalance = 0;

  /* Accounting Balance */
  private double _accountingBalance = 0;

  /** Current date.  */
  private int _currentDate = 0;


  /**
   * Imports the content from a file.
   *  
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, ImportFileException, MissingFileAssociationException, UnavailableFileException {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(txtfile));
        String line;
        while ((line = reader.readLine()) != null){
          String fields[] = line.split("\\|");
          try {
            registerFromFields(fields);
          } catch (ImportFileException e) {
            throw new ImportFileException();
          } catch (UnavailableFileException e){
            e.printStackTrace();
          } catch (IOException e){
            e.printStackTrace();
          }
        }
      } catch (IOException e){
        throw new ImportFileException();
      }
  }


  /**
   * Registers suppliers, clients and products from a string array.
   * 
   * @param fields
   *        the string array with the information
   * 
   * @throws IOException
   * @throws BadEntryException
   * @throws ImportFileException
   * @throws MissingFileAssociationException
   * @throws UnavailableFileException
   */
  public void registerFromFields(String[] fields) throws IOException, BadEntryException, ImportFileException, MissingFileAssociationException, UnavailableFileException {

    try {
      switch (fields[0]){
        case "SUPPLIER":
          registerSupplier(fields[1], fields[2], fields[3]);
          break;
        case "CLIENT":
          registerClient(fields[1], fields[2], fields[3]);
          break;
        default: {
            int price = Integer.parseInt(fields[fields.length-3]);
            int criticalValue = Integer.parseInt(fields[fields.length-2]);
            int existenceValue = Integer.parseInt(fields[fields.length-1]);
            switch (fields[0]){
              case "BOX":
                registerBox(fields[1], fields[2], fields[3], price, criticalValue, existenceValue);
                break;
              case "CONTAINER":  
                registerContainer(fields[1], fields[2], fields[3], fields[4], price, criticalValue, existenceValue);
                break;   
              case "BOOK":
                registerBook(fields[1], fields[2], fields[3], fields[4], fields[5], price, criticalValue, existenceValue);
                break;
              default: {
                throw new BadEntryException(fields[0]);
               }
            }
        }
      }
    } catch (ClientAlreadyExistsException e){
      throw new ImportFileException();
    } catch (ProductKeyAlreadyExistsException e) {
      e.printStackTrace();
    } catch (SupplierDoesNotExistException e) {
      e.printStackTrace();
    } catch (SupplierKeyAlreadyExistsException e) {
      e.printStackTrace();
    }catch (ServiceTypeDoesNotExistException e) {
      e.printStackTrace();
    } catch (ServiceLevelDoesNotExistException e) {
      e.printStackTrace();
    } 
  }

  /* --------------------------------- PRODUCTS ----------------------------------- */


   /**
   * Shows all the products that are registered in the store.
   * 
   * @return a list with all the products
   */
  public String showAllProducts(){
    String productsList = "";
    for (Map.Entry<String, Product> entry : _products.entrySet()){
      Product product = entry.getValue();
      productsList += product.toString();
    }
    return productsList;
  }

  /**
   * Registers a Box, which is a type of product.
   * 
   * @param productKey
   *              the produt's key
   * @param serviceType
   *              the type of service
   * @param supplierKey 
   *              the product's supplier key
   * @param price
   *              the product's price
   * @param criticalValue
   *              the product's critical value
   * @param existenceValue
   *              the amount of products of that type that exist
   * 
   * @throws ProductKeyAlreadyExistsException
   * @throws SupplierDoesNotExistException
   * @throws ServiceTypeDoesNotExistException
   */
  public void registerBox(String productKey, String serviceType, String supplierKey, int price, int criticalValue, int existenceValue)
            throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException, ServiceTypeDoesNotExistException {

    Product product = _products.get(productKey.toUpperCase());
    if (product != null)
      throw new ProductKeyAlreadyExistsException();

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());
    if (supplier == null)
      throw new SupplierDoesNotExistException();

    ServiceType servicetype = null;
    for (ServiceType validServiceType: ServiceType.values()){
      if (serviceType.equalsIgnoreCase(validServiceType.name())){
        servicetype = validServiceType;
        break;
      }
    }

    if (servicetype == null)
      throw new ServiceTypeDoesNotExistException();

    Box newbox = new Box(productKey, servicetype, supplierKey, price, criticalValue, existenceValue);
    _products.put(productKey.toUpperCase(), newbox);

    for (Map.Entry<String, Client> entry: _clients.entrySet())
      newbox.registerObserver(entry.getValue());
  }
  
  /**
   * Registers a Container, which is a type of product.
   * 
   * @param productKey
   *              the produt's key
   * @param seriveLevel
   *              the level of quality of the service provided
   * @param serviceType
   *              the type of service
   * @param supplierKey 
   *              the product's supplier key
   * @param price
   *              the product's price
   * @param criticalValue
   *              the product's critical value
   * @param existenceValue
   *              the amount of products of that type that exist
   * 
   * @throws ProductKeyAlreadyExistsException
   * @throws SupplierDoesNotExistException
   * @throws ServiceTypeDoesNotExistException
   * @throws ServiceLevelDoesNotExistException
   */
  public void registerContainer(String productKey, String serviceType, String serviceLevel, String supplierKey, int price, int criticalValue, int existenceValue) 
            throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException, ServiceTypeDoesNotExistException, ServiceLevelDoesNotExistException {

    Product product = _products.get(productKey.toUpperCase());
    if (product != null)
      throw new ProductKeyAlreadyExistsException();

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());
    if (supplier == null)
      throw new SupplierDoesNotExistException();

    ServiceType servicetype = null;
    for (ServiceType validServiceType: ServiceType.values()){
      if (serviceType.equalsIgnoreCase(validServiceType.name())){
        servicetype = validServiceType;
        break;
      }
    }

    if (servicetype == null)
      throw new ServiceTypeDoesNotExistException();

    ServiceLevel servicelevel = null;
    for (ServiceLevel validServiceLevel: ServiceLevel.values()){
      if (serviceLevel.equalsIgnoreCase(validServiceLevel.name())){
        servicelevel = validServiceLevel;
        break;
      }
    }

    if (servicelevel == null)
      throw new ServiceLevelDoesNotExistException(); 
    
    Container newcontainer = new Container(productKey, servicetype, servicelevel, supplierKey, price, criticalValue, existenceValue);
    _products.put(productKey.toUpperCase(), newcontainer);

    for (Map.Entry<String, Client> entry: _clients.entrySet())
      newcontainer.registerObserver(entry.getValue());
  }

  /**
   * Registers a Book, which is a type of product.
   * 
   * @param productKey
   *              the produt's key
   * @param title
   *              the book's title
   * @param author
   *              the book's author
   * @param ISBN
   *              the book's ISBN
   * @param supplierKey 
   *              the product's supplier key
   * @param price
   *              the product's price
   * @param criticalValue
   *              the product's critical value
   * @param existenceValue
   *              the amount of products of that type that exist
   * 
   * @throws ProductKeyAlreadyExistsException
   * @throws SupplierDoesNotExistException
   */
  public void registerBook(String productKey, String title, String author, String ISBN, String supplierKey, int price, int criticalValue, int existenceValue)
            throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException {

    if (_products.get(productKey.toUpperCase()) != null)
      throw new ProductKeyAlreadyExistsException();

    if (_suppliers.get(supplierKey.toUpperCase()) == null)
      throw new SupplierDoesNotExistException();

    Book newbook = new Book(productKey, title, author, ISBN, supplierKey, price, criticalValue, existenceValue);
    _products.put(productKey.toUpperCase(), newbook);

    for (Map.Entry<String, Client> entry: _clients.entrySet())
      newbook.registerObserver(entry.getValue());
  }

  /**
   * Changes the price of a product.
   * 
   * @param productKey
   *              the product's key
   * @param price
   *              the product's price
   * 
   * @throws ProductKeyDoesNotExistException
   * @throws FailedToChangePriceException
   */
  public void changePrice(String productKey, int price) throws ProductKeyDoesNotExistException, FailedToChangePriceException {
    
    Product product = _products.get(productKey.toUpperCase());
    if (product == null)
      throw new ProductKeyDoesNotExistException();
    else if (price < 0) /* MAIS MANEIRAS DE O CHANGEPRICE FALHAR SILENCIOSAMENTE??? */
      throw new FailedToChangePriceException();
    product.changePrice(price);
  }


  public String showProductsCheaperThan(int priceLimit){
    String productsList = "";
    for (Map.Entry<String, Product> entry : _products.entrySet()){
      Product product = entry.getValue();
      if (product.getPrice() < priceLimit)
        productsList += product.toString();
    }
    return productsList;
  }


  /* -------------------------------------- CLIENTS ------------------------------------------- */


  /**
   * Shows the store client with the key given as the argument.
   * 
   * @param clientKey
   *              the client's key
   * @return the client
   * 
   * @throws ClientNotFoundException
   *              if the client with that key does not exist in the store
   */
  public String showClient(String clientKey) throws ClientNotFoundException {
    
    Client client = _clients.get(clientKey.toUpperCase());
    if (client == null)
      throw new ClientNotFoundException();

    return client.toString() + client.showNotifications();
  } 

  /**
   * Shows all the clients that are registered in the store.
   * 
   * @return a list with all the clients
   */
  public String showAllClients(){
    String clientsList = "";
    for (Map.Entry<String, Client> entry : _clients.entrySet()){
      Client client = entry.getValue();
      clientsList += client.toString();
    }
    return clientsList;
  }

  /**
   * Registers a client.
   * 
   * @param clientKey 
   *            the client's key
   * @param clientName 
   *            the client's name
   * @param clientAddress 
   *            the client's address
   * @throws ClientAlreadyExistsException
   *            the client already exists in the store
   */
  public void registerClient(String clientKey, String clientName, String clientAddress) throws ClientAlreadyExistsException {

    if (_clients.containsKey(clientKey.toUpperCase()) == true){
      throw new ClientAlreadyExistsException();
    }
    Client newclient = new Client(clientKey, clientName, clientAddress);
    _clients.put(clientKey.toUpperCase(), newclient);

    for (Map.Entry<String, Product> entry: _products.entrySet()){
      entry.getValue().registerObserver(newclient);
    }
  }


  public String showClientTransactions(String clientKey) throws ClientNotFoundException {

    Client client = _clients.get(clientKey.toUpperCase());
    if (client == null)
      throw new ClientNotFoundException();
    return client.showAllTransactions();
  }

  public boolean toggleProductNotifications(String clientKey, String productKey) throws ClientNotFoundException, ProductKeyDoesNotExistException {

    Client client = _clients.get(clientKey.toUpperCase());
    if (client == null)
      throw new ClientNotFoundException();

    Product product = _products.get(productKey.toUpperCase());
    if (product == null)
      throw new ProductKeyDoesNotExistException();

    if (product.isObserver(client))
      product.removeObserver(client);
    else
      product.registerObserver(client);

    return product.isObserver(client);

  }

  public String showClientPaidTransactions(String clientKey) throws ClientNotFoundException {

    Client client = _clients.get(clientKey.toUpperCase());
    if (client == null)
      throw new ClientNotFoundException();
    return client.showPaidTransactions();
  }


  /* ------------------------------------- SUPPLIERS ------------------------------------- */


/**
   * Registers a supplier.
   * 
   * @param supplierKey
   *              the supplier's key
   * @param supplierName
   *              the supplier's name
   * @param supplierAddress 
   *              the supplier's address
   */
  public void registerSupplier(String supplierKey, String supplierName, String supplierAddress) throws SupplierKeyAlreadyExistsException {
    
    if (_suppliers.get(supplierKey.toUpperCase()) != null)
      throw new SupplierKeyAlreadyExistsException();

    Supplier newsupplier = new Supplier(supplierKey, supplierName, supplierAddress);
    _suppliers.put(supplierKey.toUpperCase(), newsupplier);

  }

  public String showSupplierTransactions(String supplierKey) throws SupplierDoesNotExistException {

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());
    if (supplier == null)
      throw new SupplierDoesNotExistException();

    return supplier.showTransactions();
  }


  public boolean toggleSupplierTransactions(String supplierKey) throws SupplierDoesNotExistException {

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());
    if (supplier == null)
      throw new SupplierDoesNotExistException();

    return supplier.toggleTransactions();
  }

  /**
   * Shows all the supplier that are registered in the store.
   * 
   * @return a list with all the suppliers
   */
  public String showAllSuppliers(){
    String suppliersList = "";
    for (Map.Entry<String, Supplier> entry : _suppliers.entrySet()){
      Supplier supplier = entry.getValue();
      suppliersList += supplier.toString();
    }
    return suppliersList;
  }


  /* ---------------------------------- TRANSACTIONS ------------------------------------ */


  public void registerOrder(String supplierKey) throws SupplierDoesNotExistException, SupplierIsNotAvailableException {

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());
    if (supplier == null)
      throw new SupplierDoesNotExistException();

    if (supplier.isAvailable() != true)
      throw new SupplierIsNotAvailableException();

    Order order = new Order(_transactionKey, supplierKey, _currentDate);
    _transactions.put(_transactionKey, order);
    supplier.addTransaction(_transactionKey, order);

  }

  public void addProductToOrder(String supplierKey, String productKey, int amount) throws ProductKeyDoesNotExistException, SupplierDoesNotHaveProductException {

    Supplier supplier = _suppliers.get(supplierKey.toUpperCase());

    Product product = _products.get(productKey.toUpperCase());
    if (product == null){
      _transactions.remove(_transactionKey);
      supplier.removeTransaction(_transactionKey);
      throw new ProductKeyDoesNotExistException();
    }

    if (product.getSupplierKey().equalsIgnoreCase(supplierKey) != true ){
      _transactions.remove(_transactionKey);
      supplier.removeTransaction(_transactionKey);
      throw new SupplierDoesNotHaveProductException();
    }

    supplier.getTransaction(_transactionKey).addProduct(product, amount);

  }

  public void finalizeOrder(String supplierKey){
    Order order = _suppliers.get(supplierKey.toUpperCase()).getTransaction(_transactionKey);
    for (Map.Entry<String, Integer> entry: order.getProducts().entrySet()){
      String productKey = entry.getKey();
      int amount = entry.getValue();
      _products.get(productKey.toUpperCase()).addStock(amount);
    }
    _availableBalance -= order.getBaseValue();
    _accountingBalance -= order.getBaseValue();

    _transactionKey++;

  }

  public void registerSale(String clientKey, int deadLine, String productKey, int amount) throws ClientNotFoundException, ProductKeyDoesNotExistException, NotEnoughExistingAmountException {
    Client client = _clients.get(clientKey.toUpperCase());

    if (client == null)
      throw new ClientNotFoundException();

    Product product = _products.get(productKey.toUpperCase());
    if (product == null)
      throw new ProductKeyDoesNotExistException();

    int existingAmount = product.getExistenceValue();
    if (amount > existingAmount)
      throw new NotEnoughExistingAmountException(existingAmount);

    product.removeStock(amount);

    int unitPrice = _products.get(productKey.toUpperCase()).getPrice();
    int baseValue = amount * unitPrice;

    Sale sale = new Sale(_transactionKey, client, deadLine, product, amount, baseValue);
    _transactions.put(_transactionKey, sale);
    client.addTransaction(sale);
    client.updateLastTransactionToPayValue(_currentDate);
    _accountingBalance += client.getLastTransactionToPayValue();

    _transactionKey++;
  }

  public String showTransaction(int transactionKey) throws TransactionKeyDoesNotExistException {

    if (transactionKey < 0 || transactionKey >= _transactionKey)
      throw new TransactionKeyDoesNotExistException();

    return _transactions.get(transactionKey).toString();
  }

  public void payTransaction(int transactionKey) throws TransactionKeyDoesNotExistException {

    if (transactionKey < 0 || transactionKey >= _transactionKey)
      throw new TransactionKeyDoesNotExistException();

    Transaction t = _transactions.get(transactionKey);

    _availableBalance += t.pay(_currentDate);

  }

  /* ---------------------------------- OTHERS ---------------------------------------- */


 /**
   * @return current date
   */
  public int showCurrentDate(){
    return _currentDate;
  }
  
  /**
   * @param days
   *        number of days to advance
   * @throws InvalidNumberOfDaysException
   *        if the number passed as argument is not valid
   */
  public void advanceDate(int days) throws InvalidNumberOfDaysException {
    if (days < 1)
      throw new InvalidNumberOfDaysException();

    _currentDate += days;

    for (Map.Entry<String, Client> entry: _clients.entrySet()){
      Client client = entry.getValue();
      double oldValue = client.getTransactionsToPayValue();
      client.updateTransactionsToPayValue(_currentDate);
      double newValue = client.getTransactionsToPayValue();
      _accountingBalance += (newValue - oldValue);
    }

  }

  public int showAvailableBalance(){
    return (int)Math.round(_availableBalance);
  }

  public int showAccountingBalance(){
    return (int)Math.round(_accountingBalance);
  }

}
