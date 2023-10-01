package woo;

import java.io.Serializable;

public abstract class ClientState implements Serializable {

	/** Serial number for serialization. */
  private static final long serialVersionUID = 202001239212L;
	
	private Client _client;
	private int _points;

	public ClientState(Client client, int points){
		_client = client;
		_points = points;
	}

	public Client getClient(){
		return _client;
	}

	public int getPoints(){
		return _points;
	}

	public void addPoints(int points){
		_points += points;
	}

	public abstract void upgrade();
	public abstract void downgrade(int delayedDays);
	public abstract void updateTransactionToPayValue(Sale t, int currentDate, int paymentPeriod);
}