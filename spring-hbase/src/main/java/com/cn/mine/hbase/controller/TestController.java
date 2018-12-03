package com.cn.mine.hbase.controller;

import java.util.Date;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@RequestMapping("/")
	public String getDate(){
		return FastDateFormat.getInstance("yyyyMMdd HHmmss").format(new Date());
	}
}
