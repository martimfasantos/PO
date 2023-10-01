package woo.app.transactions;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.exceptions.TransactionKeyDoesNotExistException;
import woo.app.exceptions.UnknownTransactionKeyException;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  private Input<Integer> _transactionKey;

  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _transactionKey = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
    	_display.addLine(_receiver.showTransaction(_transactionKey.value()));
    	_display.display();
    } catch (TransactionKeyDoesNotExistException e) {
    	throw new UnknownTransactionKeyException(_transactionKey.value());
    }
  }

}
