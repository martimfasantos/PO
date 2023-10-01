package woo.app.suppliers;

import pt.tecnico.po.ui.Command;        
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;  
import woo.exceptions.SupplierKeyAlreadyExistsException;
import woo.app.exceptions.DuplicateSupplierKeyException;
/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {

  private Input<String> _supplierKey;
  private Input<String> _supplierName;
  private Input<String> _supplierAddress;

  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _supplierName = _form.addStringInput(Message.requestSupplierName());
    _supplierAddress = _form.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
    	_receiver.registerSupplier(_supplierKey.value(), _supplierName.value(), _supplierAddress.value());
    } catch (SupplierKeyAlreadyExistsException e){
    	throw new DuplicateSupplierKeyException(_supplierKey.value());
    }
  }

}
