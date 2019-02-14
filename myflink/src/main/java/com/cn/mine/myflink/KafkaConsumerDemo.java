package com.cn.mine.myflink;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.util.Collector;

public class KafkaConsumerDemo {
	public static String BOOTSTRAP_SERVERS="cdh-kafka1:9092,cdh-kafka2:9092,cdh-kafka3:9092";
	//public static String ZOOKEEPER_CONNECT="cdh0:2181,cdh1:2181,cdh2:2181";
	public static String GROUP_ID = "flink-group";
	
	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment evn = StreamExecutionEnvironment.getExecutionEnvironment();
		evn.enableCheckpointing(5000);
		
		evn.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		
		evn.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		
		List<String> topics = new ArrayList<>();
		
		topics.add("test");
		
		Properties props = new Properties();
		
		props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
		//props.setProperty("zookeeper.connect", ZOOKEEPER_CONNECT); //cdh0:2181,cdh1:2181,cdh2:2181
        props.setProperty("group.id", GROUP_ID);
        
		FlinkKafkaConsumer09<String> consumer = new FlinkKafkaConsumer09<>(topics, new SimpleStringSchema(), props);
				
		//consumer.setStartFromEarliest(); //读取位置
		consumer.setStartFromGroupOffsets();
		consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());
		
		DataStream<String> keyedStream = evn.addSource(consumer);
		keyedStream.print();
		
		evn.execute("Flink-Kafka demo");
		
	}
}

class MessageSplitter implements FlatMapFunction<String, Tuple2<String, Long>> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void flatMap(String value, Collector<Tuple2<String, Long>> out) throws Exception {
        if (value != null && value.contains(",")) {
            String[] parts = value.split(",");
            out.collect(new Tuple2<>(value, 1l));
        }
    }
}
