package woo;

public class AppDelivery extends NotificationsDeliveryType {

	 /** Serial number for serialization. */
    private static final long serialVersionUID = 12009237L;

    @Override
    public String send(Notification n){
        return String.format("%s|%s|%d\n", n.getNotificationType(), n.getProduct().getProductKey(), n.getProduct().getPrice());
    }


}