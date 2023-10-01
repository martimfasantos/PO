package woo;

public class SelectionState extends ClientState {

	/** Serial number for serialization. */
  private static final long serialVersionUID = 2965239212L;

  	public SelectionState(Client client, int points){
  		super(client, points);
  	}

  	@Override
	public void addPoints(int points){
		super.addPoints(points);
		if (getPoints() > 25000)
			upgrade();
	}

	@Override
	public void upgrade(){
		getClient().setState(new EliteState(getClient(), getPoints()));
	}

	@Override
	public void downgrade(int delayedDays){
		if (delayedDays > 2){
			getClient().setState(new NormalState(getClient(), (int)(0.1*getPoints())));
		}
	}

	@Override
	public void updateTransactionToPayValue(Sale s, int currentDate, int paymentPeriod){
		int deadLine = s.getDeadLine();
		int baseValue = s.getBaseValue();

		if (s.isPaid())
			return;

		else if (currentDate <= deadLine - paymentPeriod){
			s.updateToPayValue(0.9*baseValue);
		}

		else if (currentDate > deadLine - paymentPeriod && currentDate <= deadLine){
			if (currentDate <= deadLine - 2)
				s.updateToPayValue(0.95*baseValue);
			else
				s.updateToPayValue(baseValue);
		}

		else if (currentDate > deadLine  && currentDate <= deadLine + paymentPeriod){
			if (currentDate > deadLine + 1)
				s.updateToPayValue(baseValue + (currentDate - deadLine)*0.02*baseValue);
			else 
				s.updateToPayValue(baseValue);
		
		}

		else if (currentDate > deadLine + paymentPeriod){
			s.updateToPayValue(baseValue + (paymentPeriod*0.05 + 
									(currentDate - (deadLine + paymentPeriod))*0.1)*baseValue);
		}

	}

	@Override
	public String toString(){
		return "SELECTION";
	}
	
}