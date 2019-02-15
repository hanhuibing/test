package com.cn.mine.myflink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class HDFSDemo {
	public static String HDFD_PATH = "hdfs://cdh2:8020/spark/can/2019/02/14/part-000151550073600000";
	public static void main(String[] args) {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSet<String> data = env.readTextFile(HDFD_PATH);
		
		try {
			data.print();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
