package com.abc.replay;

import com.abc.replay.dao.ReplayDao;
import com.abc.replay.kafka.ReplayReachProducer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class ReplayMain {
    private static final Logger logger = LoggerFactory.getLogger(ReplayMain.class);

    private static String zkHostString;
    private static String jaasConfig;
    private static String brokerList;
    private static String topic;
    private static String fileName;
    private static String fromDate;
    private static String toDate;
    private static boolean isSingleValueReplay;

    public static void main(String args[]) {
        try{
            if(args.length<5){
                System.out.println("Usage <zkHostString> <jaasConfig> <brokerlist> <topic> <replayFileName>");
                System.out.println("OR");
                System.out.println("Usage <zkHostString> <jaasConfig> <brokerlist> <topic> <fromDate(yyyymmdd)> <todate(yyyymmdd)");
                System.exit(1);
            }
            if(args.length==5){
                zkHostString = args[0];
                jaasConfig = args[1];
                brokerList = args[2];
                topic = args[3];
                fileName = args[4];
                isSingleValueReplay=true;
            } else {
                zkHostString = args[0];
                jaasConfig = args[1];
                brokerList = args[2];
                topic = args[3];
                fromDate = args[4];
                toDate = args[5];
            }

            validateInputs();
            System.setProperty("java.security.auth.login.config", jaasConfig);

            try (InputStream input = ReplayMain.class.getClassLoader().getResourceAsStream("replay.properties")) {

                Properties prop = new Properties();

                if (input == null) {
                    System.out.println("Sorry, unable to find config.properties");
                    return;
                }
                //load a properties file from class path, inside static method
                prop.load(input);

                //get the property value and print it out
                logger.info(prop.getProperty("jdbc.connection.url"));
                logger.info(prop.getProperty("hbase.replay.table"));

                ReplayDao replayDao = new ReplayDao(prop);
                ReplayReachProducer replayReachProducer = new ReplayReachProducer();
                if(isSingleValueReplay) {
                    Pair<String, String> kafkaMessageToReplay = replayDao.getKafkaMessageToReplay(fileName);
                    logger.info("KafkaMessage to Replay::"+kafkaMessageToReplay.getKey()+"--"+kafkaMessageToReplay.getValue());
                    replayReachProducer.submit(brokerList, topic, UUID.nameUUIDFromBytes(kafkaMessageToReplay.getKey().getBytes()).toString(), kafkaMessageToReplay.getValue());
                } else {
                    List<Pair<String, String>> kafkaMessagesToReplay = replayDao.getKafkaMessagesToReplay(fromDate, toDate);
                    logger.info("Kafka Messages::"+kafkaMessagesToReplay.size());
                    kafkaMessagesToReplay.parallelStream().forEach(kafkaMsgPair->{
                        logger.info("KafkaMessage to Replay::"+kafkaMsgPair.getKey()+"--"+kafkaMsgPair.getValue());
                        replayReachProducer.submit(brokerList, topic, UUID.nameUUIDFromBytes(kafkaMsgPair.getKey().getBytes()).toString(), kafkaMsgPair.getValue());
                    });
                }
            } catch (IOException| SQLException ex) {
                logger.error("Failed to run replay", ex);
           }

        } catch (Exception ex) {
            logger.error("Failed to run replay", ex);
            System.exit(1);
        }
    }

    private static void validateInputs() {
        assert StringUtils.isNotEmpty(zkHostString);
        assert StringUtils.isNotEmpty(jaasConfig);
        assert StringUtils.isNotEmpty(brokerList);
        assert StringUtils.isNotEmpty(topic);
        if(isSingleValueReplay){
            assert StringUtils.isNotEmpty(fileName);
        } else {
            assert StringUtils.isNotEmpty(fromDate);
            assert StringUtils.isNotEmpty(toDate);
        }

    }
}
