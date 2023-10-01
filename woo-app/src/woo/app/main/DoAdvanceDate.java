package woo.app.main;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;
import woo.Storefront;                                                                                                                     
import woo.exceptions.InvalidNumberOfDaysException;
import woo.app.exceptions.InvalidDateException;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  
  private Input<Integer> _days;

  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _days = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.advanceDate(_days.value());
    } catch (InvalidNumberOfDaysException e){
      throw new InvalidDateException(_days.value());
    }
  }
}
