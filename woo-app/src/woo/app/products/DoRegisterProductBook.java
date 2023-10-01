package woo.app.products;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import woo.Storefront;
import woo.exceptions.ProductKeyAlreadyExistsException;
import woo.exceptions.SupplierDoesNotExistException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;


/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> _productKey;
  private Input<String> _title;
  private Input<String> _author;
  private Input<String> _ISBN;
  private Input<String> _supplierKey;
  private Input<Integer> _price;
  private Input<Integer> _criticalValue;

  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _title = _form.addStringInput(Message.requestBookTitle());
    _author = _form.addStringInput(Message.requestBookAuthor());
    _ISBN = _form.addStringInput(Message.requestISBN());
    _price = _form.addIntegerInput(Message.requestPrice());
    _criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());

  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerBook(_productKey.value(), _title.value(), _author.value(), _ISBN.value(),
              _supplierKey.value(), _price.value(), _criticalValue.value(), 0);
    } catch (ProductKeyAlreadyExistsException e){
      throw new DuplicateProductKeyException(_productKey.value());
    } catch (SupplierDoesNotExistException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    }
  }
}
