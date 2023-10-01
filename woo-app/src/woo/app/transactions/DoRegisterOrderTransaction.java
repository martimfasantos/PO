package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.SupplierDoesNotExistException;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.exceptions.SupplierIsNotAvailableException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.ProductKeyDoesNotExistException;
import woo.app.exceptions.WrongSupplierException;
import woo.exceptions.SupplierDoesNotHaveProductException;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  private Input<String> _supplierKey;
  private Input<String> _productKey;
  private Input<Integer> _amount;
  private Input<Boolean> _requestMore;

  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
  }

  @Override
  public final void execute() throws DialogException {
  	_form.clear();
    try {
    	_supplierKey = _form.addStringInput(Message.requestSupplierKey());
      _productKey = _form.addStringInput(Message.requestProductKey());
      _amount = _form.addIntegerInput(Message.requestAmount());
      _requestMore = _form.addBooleanInput(Message.requestMore());
      _form.parse();
      _receiver.registerOrder(_supplierKey.value());
      _receiver.addProductToOrder(_supplierKey.value(), _productKey.value(), _amount.value());
    	
      while (_requestMore.value()){
    		_form.clear();
    		_productKey = _form.addStringInput(Message.requestProductKey());
    		_amount = _form.addIntegerInput(Message.requestAmount());
        _requestMore = _form.addBooleanInput(Message.requestMore());
    		_form.parse();
    		_receiver.addProductToOrder(_supplierKey.value(), _productKey.value(), _amount.value());
    	}
      
    	_receiver.finalizeOrder(_supplierKey.value());

    } catch (SupplierDoesNotExistException e) {
    	throw new UnknownSupplierKeyException(_supplierKey.value());
   	} catch (SupplierIsNotAvailableException e) {
   		throw new UnauthorizedSupplierException(_supplierKey.value());
    } catch (ProductKeyDoesNotExistException e) {
    	throw new UnknownProductKeyException(_productKey.value());
    } catch (SupplierDoesNotHaveProductException e){
    	throw new WrongSupplierException(_supplierKey.value(), _productKey.value());
	}
  
  }

}
