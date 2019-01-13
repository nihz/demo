package com.nee.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQListenerConsumer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.4:61616");

        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TestQueue");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            MessageListener messageListener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println(((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };
            messageConsumer.setMessageListener(messageListener);
            session.commit();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
