package com.nee.demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQConsumer {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.4:61616");

        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TestQueue");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            System.out.println(textMessage.getText());
            session.commit();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
