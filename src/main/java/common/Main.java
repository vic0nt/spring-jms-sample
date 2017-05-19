package common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        SimpleMessageProducer producer = (SimpleMessageProducer) ctx.getBean("messageProducer");
        producer.sendMessages();
    }
}
