package com.abc.replay.kafka;


import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;

public class ReplayReachProducer {



	public void submit(String brokerlist,String topic, String key,String message)
	{

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerlist);// "alphd1dx002:6667");

		// specify the protocol for SSL Encryption
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
		props.put("sasl.kerberos.service.name","kafka");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		TestCallback callback = new TestCallback();
		Random rnd = new Random();


		ProducerRecord<String, String> data = new ProducerRecord<String, String>(
				topic, key,message );
		producer.send(data, callback);


		producer.close();

	}


	private static class TestCallback implements Callback {
		@Override
		public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			if (e != null) {
				System.out.println("Error while producing message to topic :" + recordMetadata);
				e.printStackTrace();
			} else {
				String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
				System.out.println(message);
			}
		}
	}


	public void submitlocal(String brokerlist, String topic, String key, String message) {
		// TODO Auto-generated method stub

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerlist);

		// specify the protocol for SSL Encryption
		//		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
		//		props.put("sasl.kerberos.service.name","kafka");


		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer(props);
		TestCallback callback = new TestCallback();

		ProducerRecord<String, String> data = new ProducerRecord<String, String>(
				topic, key,message );
		producer.send(data, callback);


		producer.close();

	}


//	public int replay(long startTime, long endTime, String hdfsPath,boolean secure,String brokerlist, String topic,String kafkapluginUrl) {
//		// TODO Auto-generated method stub
//
//
//		Configuration conf = new Configuration();
//		int counter = 0;
//
//		if(secure)
//			conf.set("hadoop.security.authentication", "kerberos");
//		conf.set("fs.default.name", hdfsPath);
//
//		try {
//			FileSystem fs = FileSystem.get(conf);
//
//
//
//			//the second boolean parameter here sets the recursion to true
//
//			FileStatus[] filestatuses =	fs.listStatus(new Path("/reach"));
//
//
//			for(FileStatus stat: filestatuses)
//			{
//				//System.out.println("Processing path " + stat.getPath() + "   ---  " + stat.getModificationTime());
//
//				long fileModificationTimeR = stat.getModificationTime();
//				String pathR  = stat.getPath().toString();
//
//				if((fileModificationTimeR >= startTime && fileModificationTimeR <= endTime) && !pathR.contains("multipartreach"))
//				{
//					if(stat.isFile())
//					{
//						System.out.println("File found   " + pathR + "   ---  " + fileModificationTimeR);
//						if(replayForKafkaTopic(stat,brokerlist,secure,topic,hdfsPath,kafkapluginUrl)!=null)
//							counter++;
//					}
//
//					else if (stat.isDirectory())
//					{
//						RemoteIterator<LocatedFileStatus> fileStatusListIteratorR = fs.listFiles(
//								stat.getPath(), true);
//
//
//						while(fileStatusListIteratorR.hasNext()){
//							LocatedFileStatus fileStatusR = fileStatusListIteratorR.next();
//
//							long fileModificationTime = fileStatusR.getModificationTime();
//							String path = fileStatusR.getPath().toString();
//
//							if((fileModificationTime >= startTime && fileModificationTime <= endTime) && !path.contains("multipartreach"))
//							{
//
//								System.out.println("Recursive File found   " + path + "   ---  " + fileModificationTime);
//								if(replayForKafkaTopic(fileStatusR,brokerlist,secure,topic,hdfsPath,kafkapluginUrl)!=null)
//									counter++;
//							}
//
//
//
//						}
//					}
//				}
//
//			}
//
//
//			fs.close();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		return counter;
//
//
//
//	}
//
//
//	private String replayForKafkaTopic(FileStatus fileStatus, String brokerlist,boolean secure,String topic,String hdfsPath,String kafkapluginUrl) {
//		// TODO Auto-generated method stub
//
//
//		//hdfs://localhost:8020/reach/010cfcbba5ba81764c16b11cf0ea8cbe.19-05-30-09-58-54_B20-1PC052819R--multipartreach212of1556.zip
//		//hdfs://localhost:8020/reach/200094418eb3bc89b1f48a96b54f1a95.Archive4 copy 31--multipartreach7of7.zip   ---  1564021869901
//		try {
//
//			String path = fileStatus.getPath().toString();
//
//			String hdfs_Location = StringUtils.substringAfterLast(path, hdfsPath);
//
//			String id = StringUtils.substringAfterLast(path, "reach/");
//
//			String [] destArray  = StringUtils.split(id,"\\.")  ;
//
//			String destination_Path  = id;
//
//
//
//			if (destArray.length >= 3)
//				destination_Path = destArray[1]+"."+destArray[2];
//
//
//
//			Head head = new Head();
//
//			head.setId(id);
//			head.setEventId(id);
//			head.setEventName("Reach-Replay-Event");
//			head.setTotalSize(fileStatus.getLen()+"");
//			head.setDestination("Kafka");
//			head.setDestinationPath(destination_Path);
//			head.setHdfsLocation(hdfs_Location);
//			Body body= new Body();
//			List<String> bodyList = new ArrayList<String>();
//			bodyList.add("replayMessage");
//
//			body.setMultiparts(bodyList);
//
//			JSONObj jsonObj = new JSONObj();
//			jsonObj.setHead(head);
//			jsonObj.setBody(body);
//
//			ObjectMapper mapper = new ObjectMapper();
//			String message = mapper.writeValueAsString(jsonObj);
//
//
//			RestTemplate restTemplate = new RestTemplate();
//			String kafkaUrl
//			= "http://"+kafkapluginUrl;
//			//localhost:8081/kafkalocal
//
//			Data data = new Data();
//			data.setBorkerlist(brokerlist);
//			data.setKey(id);
//			data.setTopic(topic);
//			data.setMessage(message);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<Data> request = new HttpEntity<>(data,headers);
//			//ResponseEntity<String> response = restTemplate.exchange(kafkaUrl, HttpMethod.POST, request, String.class);
//			String output = restTemplate.postForObject(kafkaUrl, request, String.class);
//			return output;
//
//
//
//
//
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//			return null;
//		}
//
//	}

}