package com.cn.mine.hbase;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Put;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
	@Resource(name="hbaseTemplate")
	HbaseTemplate hbaseTemplate;
	
	String tname = "ne_can";
	String fname = "can";
	
	@org.junit.Test
	public void test(){
		System.out.println(1);
		Put put = new Put("aa_test".getBytes());
		put.addColumn(fname.getBytes(), "address".getBytes(), "上地创新大厦 ".getBytes());
		put.addColumn(fname.getBytes(), "GPSLat".getBytes(), "129.364578".getBytes());
		put.addColumn(fname.getBytes(), "GPSLon".getBytes(), "39.364578".getBytes());
		hbaseTemplate.execute(tname, conn ->{
			conn.put(put);
			return null;
		});
		System.out.println(2);
	}
}
