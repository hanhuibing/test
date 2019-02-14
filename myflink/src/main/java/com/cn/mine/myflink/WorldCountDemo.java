package com.cn.mine.myflink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class WorldCountDemo 
{
	public static Logger log = LoggerFactory.getLogger(WorldCountDemo.class);
    public static void main( String[] args ) throws Exception
    {
    	ExecutionEnvironment evn = ExecutionEnvironment.getExecutionEnvironment();
    	
    	ParameterTool params = ParameterTool.fromArgs(args);
    	
    	DataSet<String> ds;
    	if(params.has("input")){
    		ds = evn.readTextFile(params.get("input"));
    	}else{
    		ds = evn.readTextFile("D:\\flink\\test.txt");
    	}
    	
    	DataSet<Tuple2<String, Integer>> counts = ds.flatMap(new Tokenizer()).groupBy(new int[]{0}).sum(1);
    	//counts.print();
    	System.out.println("----------reduce-----------");
    	log.warn("----------reduce-----------");
    	ds.flatMap(new Tokenizer()).groupBy(0).reduce(new ReduceF()).print();
    	
    	log.warn("----------aggregate-----------");
    	System.out.println("----------aggregate-----------");
    	ds.flatMap(new Tokenizer()).groupBy(0).aggregate(Aggregations.SUM, 1).print();
    	
    	if(params.has("output")){
       		counts.writeAsText("file:///" + params.get("output")).setParallelism(1);  //setparallelism(1) 生成单个文件
    		evn.execute("test example");
    	}
    }
    
    
    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String,Integer>>{

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
			
			String[] tokens = value.toLowerCase().split("\\W+");
			for(String tk:tokens){
				if(tk.length() > 0){
					out.collect(new Tuple2<String,Integer>(tk, 1));
				}
			}
		}
    }
    
    public static final class ReduceF implements ReduceFunction<Tuple2<String, Integer>>{

    	@Override
		public Tuple2<String, Integer> reduce(Tuple2<String, Integer> t1, Tuple2<String, Integer> t2)
				throws Exception {				
			return new Tuple2<String, Integer>(t1.f0, t1.f1 + t2.f1);
		}
    	
    }
}
