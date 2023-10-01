package woo.app.clients;

import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import woo.Storefront;
import woo.exceptions.ClientAlreadyExistsException;
import woo.app.exceptions.DuplicateClientKeyException;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {

  private Input<String> _clientKey;
  private Input<String> _name;
  private Input<String> _address;

  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _name = _form.addStringInput(Message.requestClientName());
    _address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerClient(_clientKey.value(), _name.value(), _address.value());
    } catch (ClientAlreadyExistsException e){
      throw new DuplicateClientKeyException(_clientKey.value());
    }
  }

}
