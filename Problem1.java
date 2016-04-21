package Problem1;

import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.util.*;

public class Problem1 {
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>{
		String ws; //="1,1,10000,10000";
		public void configure(JobConf job){ 
			
			ws = job.get("Window");
			if(ws == null){
				ws = "1,1,10000,10000";
			}
			
		}
		public void map(LongWritable key, Text value, OutputCollector<Text, Text>output, Reporter reporter)throws IOException{
			Text W = new Text();
			W.set(ws);
			String win = W.toString();

			float x1 = Float.parseFloat(win.split(",")[0]);
			float y1 = Float.parseFloat(win.split(",")[1]);
			float x2 = Float.parseFloat(win.split(",")[2]);
			float y2 = Float.parseFloat(win.split(",")[3]);
			Text square = new Text();
			String line = value.toString();
			String[] splits = line.split(",");
			//the records from the rectangles.txt file
			if (splits.length!=2){
				float rx1 = Float.parseFloat(splits[1]);
				float ry1 = Float.parseFloat(splits[2]) - Float.parseFloat(splits[3]);
				float rx2 = Float.parseFloat(splits[1]) + Float.parseFloat(splits[4]);
				float ry2 = Float.parseFloat(splits[2]);
				//split the whole dataset into 100 squares with 100*100 areas
				if (!(ry1>y2||ry2<y1||rx1>x2||rx2<x1)){
					square.set(String.valueOf((int)rx1/100)+","+String.valueOf((int)ry1/100));
					output.collect(square, value);
					if(((int)rx1/100)!=((int)rx2/100)){
						if(((int)ry1/100)!=((int)ry2/100)){
							square.set(String.valueOf((int)rx2/100)+","+String.valueOf((int)ry2/100));
							output.collect(square, value);
						}
						square.set(String.valueOf((int)rx2/100)+","+String.valueOf((int)ry1/100));
						output.collect(square, value);
					}
					if(((int)ry1/100)!=((int)ry2/100)){
						square.set(String.valueOf((int)rx1/100)+","+String.valueOf((int)ry2/100));
						output.collect(square, value);
					}
				}
			}else{
				//data from the points.txt file
				float px =Float.parseFloat(splits[0]);
				float py =Float.parseFloat(splits[1]);
				if(px>=x1 &px<=x2 &py>=y1 &py<=y2){
					square.set(String.valueOf((int)px/100)+","+String.valueOf((int)py/100));
					output.collect(square, value);
				}
			}
		}
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text,Text,Text,Text>{
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text,Text> output, Reporter reporter)throws IOException{
			List<String> points = new ArrayList<String>();
			List<String> rects = new ArrayList<String>();
			while(values.hasNext()){
				String value = values.next().toString();
				if(value.split(",").length==2){
					points.add(value);
				}else{
					rects.add(value);
				}
			}
			for (String tmp1:rects){
				String[] rect = tmp1.split(",");
				
				String name = rect[0];
				float rx1 = Float.parseFloat(rect[1]);
				float ry1 = Float.parseFloat(rect[2]) - Float.parseFloat(rect[3]);
				float rx2 = Float.parseFloat(rect[1]) + Float.parseFloat(rect[4]);
				float ry2 = Float.parseFloat(rect[2]);
				
				for(String tmp2:points){
					float px = Float.parseFloat(tmp2.split(",")[0]);
					float py = Float.parseFloat(tmp2.split(",")[1]);
					if(px>=rx1 & px<=rx2 & py>=ry1 & py<=ry2){
						Text join = new Text();
						join.set("<"+name+",("+String.valueOf(px)+","+String.valueOf(py)+")>");
						key.set(name);
						output.collect(join, new Text());
					}
					
				}
				
			}
		}
	}
	public static void main(String[] args) throws IOException{
		JobConf conf = new JobConf(Problem1.class);
		conf.set("Window", "1,3,30,20");
		conf.setJobName("Problem1");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
	}

}
