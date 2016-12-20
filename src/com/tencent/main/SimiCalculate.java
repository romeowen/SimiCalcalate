package com.tencent.main;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class SimiCalculate extends Configured implements Tool {	
	
	public int run(String[] args) throws Exception {
		
		Configuration conf = getConf();		
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs(); 
		MapReduceConf MapReduce =new MapReduceConf(otherArgs, conf);
		MapReduce.FirstMapReduceConf();	
				
		return 0;
	}		

	public static void main(String [] args) throws Exception {		
		
		int res = ToolRunner.run(new Configuration(),new SimiCalculate(),args);
		System.exit(res);
	}
}