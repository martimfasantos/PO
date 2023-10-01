package woo.app.products;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import woo.Storefront;
import woo.exceptions.ProductKeyAlreadyExistsException;
import woo.exceptions.SupplierDoesNotExistException;
import woo.exceptions.ServiceTypeDoesNotExistException;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownServiceTypeException;


/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  private Input<String> _productKey;
  private Input<String> _supplierKey;
  private Input<Integer> _price;
  private Input<Integer> _criticalValue;
  private Input<String> _serviceType;

  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerBox(_productKey.value(), _serviceType.value(), _supplierKey.value(), _price.value(),
              _criticalValue.value(), 0);
    } catch (ProductKeyAlreadyExistsException e){
      throw new DuplicateProductKeyException(_productKey.value());
    } catch (SupplierDoesNotExistException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    } catch (ServiceTypeDoesNotExistException e){
      throw new UnknownServiceTypeException(_serviceType.value());
    }
  }
}
