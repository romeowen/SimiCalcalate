package com.tencent.main;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import com.tencent.main.SimiCompute;
import java.util.Random;

public  class FirstMap
extends Mapper<Object, Text, Object, Text> {
	
    public void map(Object key,Text value, Context context) 
     throws IOException,InterruptedException {   	

    	setup(context);  
    	int expect_length = Integer.parseInt(context.getConfiguration().get("array_len"));
    	int topN = Integer.parseInt(context.getConfiguration().get("topN"));
    	double second_sample_ratio = Double.parseDouble(context.getConfiguration().get("second_sample_ratio"));
    	int label = Integer.parseInt(context.getConfiguration().get("label_index"));
    	
    	Random rand = new Random(System.currentTimeMillis());
    	
    	String [] TempValue=value.toString().split("#",-1);
    	int array_len = TempValue.length;
    	int target = Integer.parseInt(TempValue[label-1]);
    	if (rand.nextDouble() <= second_sample_ratio || target == 1 )
	    	if (array_len == expect_length) {
	    		
	        	String dimension_1 = "", dimension_2 = "";     
	        	String simi_str = null;
	        	String final_result = "";
	        	
	        	dimension_1 = TempValue[array_len-2];
	        	dimension_2 = TempValue[array_len-1];
	        	SimiCompute score = new SimiCompute(dimension_1, dimension_2, topN);
	        	double simi = score.getSimiScore();
	        	if (simi == -1) 
	        		 simi_str="";
	        	else
	        		 simi_str = String.valueOf(simi);        	
	        	for(int i=0; i< TempValue.length-2; i++) { 
	            	final_result += TempValue[i] + ",";        		
	        	}
	        	final_result += simi_str; 
	        	Text value_write = new Text(final_result);
	        	context.write("", value_write);  
	    	}
  	
    }
	
}