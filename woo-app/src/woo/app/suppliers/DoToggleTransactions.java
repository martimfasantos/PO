package woo.app.suppliers;

import pt.tecnico.po.ui.Command;        
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;  
import woo.exceptions.SupplierDoesNotExistException;
import woo.app.exceptions.UnknownSupplierKeyException;
/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  private Input<String> _supplierKey;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
   	try {
   		if (_receiver.toggleSupplierTransactions(_supplierKey.value()) == true)
   			_display.addLine(Message.transactionsOn(_supplierKey.value()));
   		else
   			_display.addLine(Message.transactionsOff(_supplierKey.value()));
   		_display.display();
   	} catch ( SupplierDoesNotExistException e){
   		throw new UnknownSupplierKeyException(_supplierKey.value());
   	}
  }

}
