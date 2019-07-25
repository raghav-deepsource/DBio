package main.java.net.preibisch.distribution.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class GsonIO {
//	public static void toJson(Object obj, File file)  {
//		try (Writer writer = new FileWriter(file)){
//			Gson gson = new Gson();
//			gson.toJson(obj, writer);
//			System.out.println("File saved: "+file.getAbsolutePath()+" | Size: "+file.length());
//			System.out.println(); 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
	
	public static void toJson(Object obj, File file)  {
		try (PrintWriter out = new PrintWriter(file)){
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			out.print(json);
			out.flush();
			out.close();
			System.out.println("File saved: "+file.getAbsolutePath()+" | Size: "+file.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
