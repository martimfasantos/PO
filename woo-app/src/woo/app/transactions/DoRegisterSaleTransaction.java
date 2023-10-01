package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.exceptions.NotEnoughExistingAmountException;
import woo.app.exceptions.UnavailableProductException;
import woo.exceptions.ClientNotFoundException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.ProductKeyDoesNotExistException;
import woo.app.exceptions.UnknownProductKeyException;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  private Input<String> _clientKey;
  private Input<Integer> _paymentDeadline;
  private Input<String> _productKey;
  private Input<Integer> _amount;

  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _paymentDeadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    _productKey = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException {
  	_form.parse();
    try{
    	_receiver.registerSale(_clientKey.value(), _paymentDeadline.value(), _productKey.value(), _amount.value());
      
    } catch (ClientNotFoundException e){
    	throw new UnknownClientKeyException(_clientKey.value());
	  } catch (ProductKeyDoesNotExistException e) {
      throw new UnknownProductKeyException(_productKey.value());
    } catch (NotEnoughExistingAmountException e){
		  int existingAmount = e.getExistingAmount();
    	throw new UnavailableProductException(_productKey.value(), _amount.value(), existingAmount);
    }
  }

}
