package woo;

public class EliteState extends ClientState {

	/** Serial number for serialization. */
  private static final long serialVersionUID = 2009212L;

  	public EliteState(Client client, int points){
  		super(client, points);
  	}

  	@Override
	public void addPoints(int points){
		super.addPoints(points);
	}

	@Override
	public void upgrade(){
		// empty on purpose (highest state possible)
	}

	@Override
	public void downgrade(int delayedDays){
		if (delayedDays > 15){
			getClient().setState(new SelectionState(getClient(), (int)(0.25*getPoints())));
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
			s.updateToPayValue(0.9*baseValue);
		}

		else if (currentDate > deadLine && currentDate <= deadLine + paymentPeriod){
			s.updateToPayValue(0.95*baseValue);
		}

		else if (currentDate > deadLine + paymentPeriod){
			s.updateToPayValue(0.9*baseValue);
		}



	}

	@Override
	public String toString(){
		return "ELITE";
	}
	
}