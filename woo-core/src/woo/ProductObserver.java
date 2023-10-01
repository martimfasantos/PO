package woo;

import java.io.Serializable;

public abstract class ProductObserver implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 12021L;

    public abstract void receiveNotification(Notification n);

}