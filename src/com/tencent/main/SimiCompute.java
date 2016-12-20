package com.tencent.main;

import java.util.ArrayList;
import java.lang.Math;

public class SimiCompute {
	String dimension_1= null;
	String dimension_2 = null;
	int topN = 0;
	
	public SimiCompute(String dimension_1, String dimension_2, int topN) {
		this.dimension_1 = dimension_1;
		this.dimension_2 = dimension_2;		
		this.topN = topN;
	}
	
	public double getSimiScore() {

		double score = 0;
		if (this.dimension_1.isEmpty() || this.dimension_2.isEmpty()) 
			return -1;
		score = compute(this.dimension_1, this.dimension_2);
	
		return score;	
	}
	
	
	private double compute(String item_1, String item_2) {
		
		ArrayList<String> item_1_id = new ArrayList<String>();
		ArrayList<Double> item_1_weight = new ArrayList<Double>();
		ArrayList<String> item_2_id = new ArrayList<String>();
		ArrayList<Double> item_2_weight = new ArrayList<Double>();
		
		String [] item_1_list = item_1.split(",", -1);
		String [] item_2_list = item_2.split(",", -1);
		
		int cut_num = item_1_list.length > this.topN ? this.topN : item_1_list.length;		
		for (int i =0; i< cut_num; i++) {
			String [] item = item_1_list[i].split(":", -1);
			item_1_id.add(item[0]);
			item_1_weight.add(Double.parseDouble(item[1]));			
		}
		
		cut_num = item_2_list.length > this.topN ? this.topN : item_2_list.length;
		for (int i =0; i< cut_num; i++) {
			String [] item = item_2_list[i].split(":", -1);
			item_2_id.add(item[0]);
			item_2_weight.add(Double.parseDouble(item[1]));			
		}
		
		item_1_weight = normalization(item_1_weight);
		item_2_weight = normalization(item_2_weight);
		double simi_value = 0;
		for (int i=0; i< item_1_id.size(); i++) {
			if (item_2_id.contains(item_1_id.get(i))) {
				int index = item_2_id.indexOf(item_1_id.get(i));
				simi_value += item_2_weight.get(index) * item_1_weight.get(i);
			}
		}

		return simi_value;
	}
	
	private ArrayList<Double> normalization( ArrayList<Double> weight ) {
		ArrayList<Double> vector = new ArrayList<Double>();
		double square_sum = 0;
		for (int i=0; i< weight.size(); i++) {
			square_sum += Math.pow(weight.get(i), 2); 
		}
		square_sum = Math.pow(square_sum, 0.5);
		for (int i=0; i< weight.size(); i++) {
			vector.add(weight.get(i)/square_sum); 
		}		
		return vector;		
	}

}
