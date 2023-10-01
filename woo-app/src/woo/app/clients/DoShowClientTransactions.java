package woo.app.clients;


import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.exceptions.ClientNotFoundException;
import woo.app.exceptions.UnknownClientKeyException;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  private Input<String> _clientKey;  

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
	    _display.addLine(_receiver.showClientTransactions(_clientKey.value()));
	    _display.display();
	} catch (ClientNotFoundException e){
		throw new UnknownClientKeyException(_clientKey.value());
    }
  }
}
