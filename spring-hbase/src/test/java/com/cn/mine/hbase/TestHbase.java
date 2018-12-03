package com.cn.mine.hbase;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.cn.mine.hbase.util.HbaseRowMapper;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHbase {
	@Resource(name="hbaseTemplate")
	HbaseTemplate hbaseTemplate;
	
	String tname = "ne_can";
	String fname = "can";
	
	@Test
	public void test(){
		System.out.println(1);
		Put put = new Put("aa_test".getBytes());
		put.addColumn(fname.getBytes(), "address".getBytes(), Bytes.toBytes("上地创新大厦 "));
		put.addColumn(fname.getBytes(), "GPSLat".getBytes(), "129.364578".getBytes());
		put.addColumn(fname.getBytes(), "GPSLon".getBytes(), "39.364578".getBytes());
		hbaseTemplate.execute(tname, conn ->{
			conn.put(put);
			return null;
		});
		System.out.println(2);
	}
	
	@Test
	public void TestGet(){
		Map<String,Object> map = hbaseTemplate.get(tname, "aa_test", new HbaseRowMapper());
		System.out.println(JSONObject.toJSONString(map));
	}
	
	@Test
	public void TestDel(){
		hbaseTemplate.delete(tname, "aa_test", fname);
	}
	
}
