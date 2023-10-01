package woo;

import woo.exceptions.*;
import java.io.*;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
    oos.writeObject(_store);
    oos.close();
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
    _store = (Store) ois.readObject();
    ois.close();
    _filename = filename;

  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException | UnavailableFileException | MissingFileAssociationException e) {
      throw new ImportFileException(textfile);
    }
  }

  /**
   * @return filename
   */
  public String getFileName(){
    return _filename;
  }
  
  public int showCurrentDate(){
    return _store.showCurrentDate();
  }

  public void advanceDate(int days) throws InvalidNumberOfDaysException {
    _store.advanceDate(days);
  }

  /* ------------------------------ PRODUCTS ---------------------------------- */

  public String showAllProducts(){
    return _store.showAllProducts();
  }

  public void registerBox(String productKey, String serviceType, String supplierKey, int price, int criticalValue, int existenceValue)
          throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException, ServiceTypeDoesNotExistException {
    _store.registerBox(productKey, serviceType, supplierKey, price, criticalValue, existenceValue);
  }

  public void registerContainer(String productKey, String serviceLevel, String serviceType, String supplierKey, int price, int criticalValue, int existenceValue)
          throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException, ServiceTypeDoesNotExistException, ServiceLevelDoesNotExistException {
    _store.registerContainer(productKey, serviceLevel, serviceType, supplierKey, price, criticalValue, existenceValue);
  }

  public void registerBook(String productkey, String title, String author, String ISBN, String supplierKey, int price, int criticalValue, int existenceValue)
          throws ProductKeyAlreadyExistsException, SupplierDoesNotExistException {
    _store.registerBook(productkey, title, author, ISBN, supplierKey, price, criticalValue, existenceValue);
  }

  public void changePrice(String productKey, int price) throws ProductKeyDoesNotExistException, FailedToChangePriceException {
    _store.changePrice(productKey, price);
  }

  public String showProductsCheaperThan(int priceLimit) {
    return _store.showProductsCheaperThan(priceLimit);
  }


  /* ------------------------------- CLIENTS ----------------------------------*/


  public void registerClient(String clientKey, String clientName, String clientAddress) throws ClientAlreadyExistsException {
    _store.registerClient(clientKey, clientName, clientAddress);
  }

  public String showClient(String clientKey) throws ClientNotFoundException {
    return _store.showClient(clientKey);
  }

  public String showAllClients(){
    return _store.showAllClients();
  }

  public String showClientTransactions(String clientKey) throws ClientNotFoundException {
    return _store.showClientTransactions(clientKey);
  }

  public boolean toggleProductNotifications(String clientKey, String productKey) throws ClientNotFoundException, ProductKeyDoesNotExistException {
    return _store.toggleProductNotifications(clientKey, productKey);
  }

  public String showClientPaidTransactions(String clientKey) throws ClientNotFoundException {
    return _store.showClientPaidTransactions(clientKey);
  }


  /* ----------------------- SUPPLIERS -------------------------------------- */

  public void registerSupplier(String supplierKey, String supplierName, String supplierAddress) throws SupplierKeyAlreadyExistsException {
    _store.registerSupplier(supplierKey, supplierName, supplierAddress);
  }

  public String showSupplierTransactions(String supplierKey) throws SupplierDoesNotExistException {
    return _store.showSupplierTransactions(supplierKey);
  }

  public String showAllSuppliers(){
    return _store.showAllSuppliers();
  }

  public boolean toggleSupplierTransactions(String supplierKey) throws SupplierDoesNotExistException {
    return _store.toggleSupplierTransactions(supplierKey);
  }


  /* ----------------------- TRANSACTIONS ------------------------------------ */

  public void registerSale(String clientKey, int deadLine, String productKey, int amount) throws ClientNotFoundException, ProductKeyDoesNotExistException, NotEnoughExistingAmountException {
    _store.registerSale(clientKey, deadLine, productKey, amount);
  }

  public void registerOrder(String supplierKey) throws SupplierDoesNotExistException, SupplierIsNotAvailableException {
    _store.registerOrder(supplierKey);
  }

  public void addProductToOrder(String supplierKey, String productKey, int amount) throws ProductKeyDoesNotExistException, SupplierDoesNotHaveProductException {
    _store.addProductToOrder(supplierKey, productKey, amount);
  }

  public void finalizeOrder(String supplierKey){
    _store.finalizeOrder(supplierKey);
  }

  public String showTransaction(int transactionKey) throws TransactionKeyDoesNotExistException {
    return _store.showTransaction(transactionKey);
  }

  public void payTransaction(int transactionKey) throws TransactionKeyDoesNotExistException {
    _store.payTransaction(transactionKey);
  }

  /* ------------------------- OTHERS ----------------------------------------- */

  public int showAvailableBalance(){
    return _store.showAvailableBalance();
  }

  public int showAccountingBalance(){
    return _store.showAccountingBalance();
  }


} 

