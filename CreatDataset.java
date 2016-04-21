import java.io.*;
import java.util.*;

public class CreatDataset {
	public static void main(String[] args){
		//the point dataset P
		try{
			FileWriter points = new FileWriter("points.txt");
			int num_point = 7000000;
		
	        for (int i = 1; i<num_point+1; i++){
	        	float x = new Random().nextFloat()*10000 + 1;
	        	float y = new Random().nextFloat()*10000 + 1;
	        	String point = String.valueOf(x) + "," + String.valueOf(y) +"\r\n";
	        	points.write(point);
	        }
	        points.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//the rectangles dataset R
		try{
			FileWriter rects = new FileWriter("rectangles.txt");
			int num_rect = 3000000;
			
			for (int i = 1; i<num_rect+1; i++){
				float x = new Random().nextFloat()*9995+1;
				float y = new Random().nextFloat()*9980+20;
				float height = new Random().nextFloat()*19+1;
				float width = new Random().nextFloat()*4+1;
				String rect = "r" + String.valueOf(i) + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(height) + "," + String.valueOf(width)+"\r\n";
				rects.write(rect);
			}
			rects.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
