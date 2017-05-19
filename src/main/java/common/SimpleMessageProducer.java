package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

@Component
public class SimpleMessageProducer {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.messageText}")
    private String messageText;

    void sendMessages() throws JMSException {
        jmsTemplate.send(session -> session.createTextMessage(messageText));
    }


    /**
     * Alternative version of send method, it doesn't use lambdas and illustrate more flexible way of
     * handling messages
     *
     * @throws JMSException
     */
    void sendMessagesExtendedLegacyEdition() throws JMSException {
        StringBuilder buffer = new StringBuilder();

        int numberOfMessages = 100;
        for (int i = 0; i < numberOfMessages; ++i) {
            buffer.setLength(0);
            buffer.append("Message '").append(i).append("' sent at: ").append(new Date());

            int count = i;
            String payload = buffer.toString();

            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage(payload);
                    message.setIntProperty("messageCount", count);
                    LOG.info("Sending message number '{}'", count);
                    return message;
                }
            });
        }
    }
}
