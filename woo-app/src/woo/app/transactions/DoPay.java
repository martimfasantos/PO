package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.TransactionKeyDoesNotExistException;
import woo.app.exceptions.UnknownTransactionKeyException;
/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  private Input<Integer> _transactionKey;
  
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _transactionKey = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
    	_receiver.payTransaction(_transactionKey.value());

    } catch ( TransactionKeyDoesNotExistException e){
    	throw new UnknownTransactionKeyException(_transactionKey.value());
    }
  }

}
