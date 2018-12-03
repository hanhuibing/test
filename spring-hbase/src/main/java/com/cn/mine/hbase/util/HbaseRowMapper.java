package com.cn.mine.hbase.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;

public class HbaseRowMapper implements RowMapper<Map<String,Object>>{
	public static String rowkey = "rowkey";
	
	@Override
	public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
		Map<String,Object> row = new HashMap<>();
		
		row.put(rowkey, new String(result.getRow()));
		
		if(row.get(rowkey) == null){
			return null;
		}
		
		for (Cell cell : result.rawCells()) {//循环所有列
            String family = Bytes.toString(CellUtil.cloneFamily(cell));// 获取当前列的familyName
            String column = Bytes.toString(CellUtil.cloneQualifier(cell));//列名
            String value = Bytes.toString(CellUtil.cloneValue(cell));//列值
            Map familyMap = (Map) row.get(family);
            if (familyMap == null) {
                row.put(family, new HashMap<>());
                familyMap = (Map) row.get(family);
            }
            familyMap.put(column, value);
        }
		return row;
	}

	public static Map<String, Object> mapRow(Result result, boolean keyOnlyFilter) throws Exception {
		Map<String,Object> row = new HashMap<>();
		
		row.put(rowkey, new String(result.getRow()));
		
		if(row.get(rowkey) == null){
			return null;
		}
		
		if(keyOnlyFilter){
			return row;
		}
		
		for (Cell cell : result.rawCells()) {//循环所有列
            String family = Bytes.toString(CellUtil.cloneFamily(cell));// 获取当前列的familyName
            String column = Bytes.toString(CellUtil.cloneQualifier(cell));//列名
            String value = Bytes.toString(CellUtil.cloneValue(cell));//列值
            Map familyMap = (Map) row.get(family);
            if (familyMap == null) {
                row.put(family, new HashMap<>());
                familyMap = (Map) row.get(family);
            }
            familyMap.put(column, value);
        }
		return row;
	}

}
