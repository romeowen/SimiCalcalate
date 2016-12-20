package com.tencent.main;

import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapReduceConf {
	
	private Configuration conf =new Configuration();
	private ArrayList<String> args = new ArrayList<String>() ;
	private int NumReduce = 0;
	
	public  MapReduceConf(String [] args, Configuration conf )  throws Exception {
		
		this.conf = conf;	
		for(String arg: args) {
			this.args.add(arg);
		}
		conf.set("topN", args[args.length-2]);
		conf.set("array_len", args[args.length-1]);
	}	

	
	public void FirstMapReduceConf() throws Exception {
		
	     	Job job1 = new Job(conf,"Job1");
			job1.setJarByClass(SimiCalculate.class);		
			job1.setMapperClass(FirstMap.class);		
			job1.setOutputKeyClass(Object.class);
			job1.setOutputValueClass(Text.class);
			job1.setNumReduceTasks(NumReduce);
			// the location of data sets
			String InputPath [] = args.get(0).split(",");
		    for(int i=0; i< InputPath.length ; i++ ) {	
				FileInputFormat.addInputPath(job1, new Path( InputPath[i]));			
		    }				
			FileOutputFormat.setOutputPath(job1,new Path(args.get(1)));
			job1.waitForCompletion(true);	
	}
	


}

