package com.cn.mine.hbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.cn.mine.hbase.util.AsciiUtil;
import com.cn.mine.hbase.util.HbaseRowMapper;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHbaseScan {
	@Resource(name="hbaseTemplate")
	HbaseTemplate hbaseTemplate;
	
	String tname = "ne_can";
	String fname = "can";
	
	@Test
	public void TestScan(){
		String startTime = "20181203130001";
		String endTime = "20181203130007";
		boolean reversed = false;
		int pageSize = 10;
		String startRow = "test_" + startTime;
		String stopRow = "test_" + endTime;
		Scan scan = new Scan(Bytes.toBytes(fname));
		scan.setReversed(reversed);
		if(reversed){
			scan.setStartRow(Bytes.toBytes(AsciiUtil.modifyAdd1(stopRow)));
			scan.setStopRow(Bytes.toBytes(AsciiUtil.modifySub1(startRow)));
		}else{
			scan.setStartRow(Bytes.toBytes(AsciiUtil.modifySub1(startRow)));
			scan.setStopRow(Bytes.toBytes(AsciiUtil.modifyAdd1(stopRow)));
		}
			
		List<Map<String, Object>> l = hbaseTemplate.execute(tname, table ->{
			Result[] results = table.getScanner(scan).next(pageSize);
			List<Map<String,Object>> list = new ArrayList<>();
			for(Result res : results){
				Map<String, Object> row = HbaseRowMapper.mapRow(res, false);
                if (row != null && row.size() > 0) {
                	list.add(row);
                }
			}
			
			return list;
		});
		
		l.forEach(m ->{
			System.out.println(JSONObject.toJSONString(m));
		});
	}
}
