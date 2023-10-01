package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.exceptions.ClientNotFoundException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.ProductKeyDoesNotExistException;
import woo.app.exceptions.UnknownProductKeyException;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  private Input<String> _clientKey;
  private Input<String> _productKey;

  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _productKey = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
    	if (_receiver.toggleProductNotifications(_clientKey.value(), _productKey.value()))
    		_display.addLine(Message.notificationsOn(_clientKey.value(), _productKey.value()));
    	else
    		_display.addLine(Message.notificationsOff(_clientKey.value(), _productKey.value()));
    	_display.display();

    } catch (ClientNotFoundException e){
    	throw new UnknownClientKeyException(_clientKey.value());
    } catch (ProductKeyDoesNotExistException e) {
    	throw new UnknownProductKeyException(_productKey.value());
    }

  }

}
