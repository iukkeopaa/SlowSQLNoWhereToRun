package org.wqz.analysis.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.Properties;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
/**
 * @Author huhaitao21
 * @Description jmq相关配置
 * @Date 18:20 2023/2/9
 **/
public class JmqConfig {
    private static Logger logger = LoggerFactory.getLogger(JmqConfig.class);
    /**
     * 应用
     */
    private static String app;

    /**
     * 用户
     */
    private static String user;

    /**
     * 密码
     */
    private static String password;

    /**
     * mq地址
     */
    private static String address;

    /**
     * 发送topic
     */
    private static String topic;

    /**
     * 应用名称配置key
     */
    private static String MQ_APP = "mqApp";

    /**
     * 用户配置key
     */
    private static String MQ_USER = "mqUser";

    /**
     * 密码配置key
     */
    private static String MQ_PASSWORD = "mqPassword";

    /**
     * mq地址配置key
     */
    private static String MQ_ADDRESS = "mqAddress";

    /**
     * topic
     */
    private static String MQ_TOPIC = "mqTopic";

    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Session session;
    private static MessageProducer producer;

    /**
     * 初始化配置
     * @param properties
     */
    public static void initConfig(Properties properties) {
        //检查参数 初始化参数
        boolean result = checkConfig(properties);
        if(result){
            app = properties.getProperty(MQ_APP);
            user = properties.getProperty(MQ_USER);
            password = properties.getProperty(MQ_PASSWORD);
            address = properties.getProperty(MQ_ADDRESS);
            topic = properties.getProperty(MQ_TOPIC);
        }
    }

    private static boolean checkConfig(Properties properties) {
        if(properties==null){
            return false;
        }
        if(StringUtils.isBlank(properties.getProperty(MQ_APP)) || StringUtils.isBlank(properties.getProperty(MQ_USER)) ||
                StringUtils.isBlank(properties.getProperty(MQ_PASSWORD)) || StringUtils.isBlank(properties.getProperty(MQ_ADDRESS))
                || StringUtils.isBlank(properties.getProperty(MQ_TOPIC))){
            return false;
        }

        return true;
    }

    /**
     * 启动生产者
     */
    public static boolean initMqProducer(){
        try{
            // 创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(user, password, address);
            // 创建连接
            connection = connectionFactory.createConnection();
            // 启动连接
            connection.start();
            // 创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建目标
            Destination destination = session.createTopic(topic);
            // 创建生产者
            producer = session.createProducer(destination);
            return true;
            // // 初始化 RabbitMQ 生产者
            //            return initRabbitMQProducer();
        }catch (Exception e){
            logger.error("sql analysis mq config init error", e);
            return false;
        }
    }


//    private static boolean initRabbitMQProducer() {
//        try {
//            rabbitmqConnectionFactory = new ConnectionFactory();
//            rabbitmqConnectionFactory.setHost(rabbitmqHost);
//            rabbitmqConnectionFactory.setPort(rabbitmqPort);
//            rabbitmqConnectionFactory.setUsername(rabbitmqUser);
//            rabbitmqConnectionFactory.setPassword(rabbitmqPassword);
//
//            rabbitmqConnection = rabbitmqConnectionFactory.newConnection();
//            rabbitmqChannel = rabbitmqConnection.createChannel();
//
//            // 声明交换器
//            rabbitmqChannel.exchangeDeclare(rabbitmqExchange, "topic");
//
//            return true;
//        } catch (IOException | TimeoutException e) {
//            logger.error("RabbitMQ producer init error", e);
//            return false;
//        }
//    }
    public static String getApp() {
        return app;
    }

    public static void setApp(String app) {
        JmqConfig.app = app;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        JmqConfig.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        JmqConfig.password = password;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        JmqConfig.address = address;
    }

    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        JmqConfig.topic = topic;
    }
}