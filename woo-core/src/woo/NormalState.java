package woo;

public class NormalState extends ClientState {

	/** Serial number for serialization. */
  private static final long serialVersionUID = 20200125612L;
	
	public NormalState(Client client, int points){
		super(client, points);
	}

	@Override
	public void addPoints(int points){
		super.addPoints(points);
		if (getPoints() > 2000)
			upgrade();
	}

	@Override
	public void upgrade(){
		if (getPoints() > 25000)
			getClient().setState(new EliteState(getClient(), getPoints()));
		else
			getClient().setState(new SelectionState(getClient(), getPoints()));
	}

	@Override
	public void downgrade(int delayedDays){
		// empty on purpose (lowest state possible)
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
			s.updateToPayValue(baseValue);
		}

		else if (currentDate > deadLine && currentDate <= deadLine + paymentPeriod){
			s.updateToPayValue(baseValue + (currentDate - deadLine)*0.05*baseValue);
		}

		else if (currentDate > deadLine + paymentPeriod){
			s.updateToPayValue(baseValue + (paymentPeriod*0.05 + 
									(currentDate - (deadLine + paymentPeriod))*0.1)*baseValue);
		}
	}

	@Override
	public String toString(){
		return "NORMAL";
	}
}