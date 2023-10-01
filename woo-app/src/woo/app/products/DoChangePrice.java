package woo.app.products;

import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import woo.Storefront;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.FailedToChangePriceException;
import woo.exceptions.ProductKeyDoesNotExistException;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<String> _productKey;
  private Input<Integer> _price;
  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.changePrice(_productKey.value(), _price.value());
    } catch (ProductKeyDoesNotExistException e){
      throw new UnknownProductKeyException(_productKey.value());
    } catch (FailedToChangePriceException e) {
      // empty on purpose
    }
  }
}
