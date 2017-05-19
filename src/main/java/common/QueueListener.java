package common;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

public class QueueListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			String body = message instanceof TextMessage ? ((TextMessage) message).getText() : null;
			if (message instanceof BytesMessage) {
				int length = (int) ((BytesMessage) message).getBodyLength();
				byte[] bytes = new byte[length];
				((BytesMessage) message).readBytes(bytes);
				body = new String(bytes, StandardCharsets.UTF_8);
			}
			System.out.println("received: " + body);

		} catch (Exception e) {
			System.out.println("Can't process message: " + e);
			throw new IllegalArgumentException("Can't process message: " + e, e);
		}
	}

}
