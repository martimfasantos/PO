package woo;

import java.io.Serializable;

public abstract class NotificationsDeliveryType implements Serializable {

	 /** Serial number for serialization. */
    private static final long serialVersionUID = 12342007L;

    public abstract String send(Notification n);

}