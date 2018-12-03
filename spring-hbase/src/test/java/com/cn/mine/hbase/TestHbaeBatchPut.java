package com.cn.mine.hbase;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHbaeBatchPut {
	@Resource(name="hbaseTemplate")
	HbaseTemplate hbaseTemplate;
	
	String tname = "ne_can";
	String fname = "can";
	
	FastDateFormat format = FastDateFormat.getInstance("yyyyMMddHHmmss");
	
	@Test
	public void TestBatchPut(){
		List<Put> list = new ArrayList<>();
		Long index = 20181203130001l;
		for(int i=0; i< 1000; i++){
			Put put = new Put(Bytes.toBytes("test_" + index));
			put.addColumn(Bytes.toBytes(fname), Bytes.toBytes("TIME"), Bytes.toBytes(index +""));
			put.addColumn(Bytes.toBytes(fname), Bytes.toBytes("INDEX"), Bytes.toBytes(i +""));
			list.add(put);
			index++;
		}
		System.out.println("开始保存");
		hbaseTemplate.execute(tname, conn ->{
			conn.batch(list);
			return null;
		});
		System.out.println("保存完成");
	}
}
