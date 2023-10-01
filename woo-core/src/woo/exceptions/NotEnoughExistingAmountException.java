package woo.exceptions;

public class NotEnoughExistingAmountException extends Exception {

	/** Class serial number. */
  private static final long serialVersionUID = 010L;

  private int _existingAmount;

  public NotEnoughExistingAmountException(int existingAmount){
  	_existingAmount = existingAmount;
  }

  public int getExistingAmount(){
  	return _existingAmount;
  }

}