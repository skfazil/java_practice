package factorypattern;

public class NotificationFactory {
	
	public static void main(String[] args) {
		String channel = "Push";
		Notification notification = createNotification(channel);
		notification.notifyUser();
	}

	//Factory pattern allows the subclasses to determine the instantiation of the objects.
	public static Notification createNotification(String channel) {
		Notification notification = null;
		switch (channel) {
		case "SMS":
			notification=new SMSNotification();
			break;
		case "Email":
			notification=new EmailNotification();
			break;
		case "Push":
			notification=new PushNotification();
			break;

		default:
			break;
		}
		return notification;
	}

}
