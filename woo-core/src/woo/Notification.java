package woo;

import java.io.Serializable;

public class Notification implements Serializable {

	 /** Serial number for serialization. */
    private static final long serialVersionUID = 1234237L;

    private String _notificationType;
    private Product _product;
    private NotificationsDeliveryType _deliveryType = new AppDelivery();

    public Notification(String notificationType, Product product){
        _notificationType = notificationType;
    	_product = product;
    }

    public String getNotificationType(){
        return _notificationType;
    }

    public Product getProduct(){
        return _product;
    }

    public String send(){
        return _deliveryType.send(this);
    }
}