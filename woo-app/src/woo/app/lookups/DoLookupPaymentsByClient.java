package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.ClientNotFoundException;
import woo.app.exceptions.UnknownClientKeyException;
/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  private Input<String> _clientKey;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.showClientPaidTransactions(_clientKey.value()));
      _display.display();
    } catch (ClientNotFoundException e){
      throw new UnknownClientKeyException(_clientKey.value());
    }
  }

}
