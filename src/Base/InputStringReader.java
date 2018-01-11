package Base;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class InputStringReader {

	public InputStringReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static String readFileAsString(String path) throws IOException
	{
		return readFileAsString(new File(path));
	}
		
	public static String readFileAsString(File file) throws IOException
	{
		return readFileAsString(new FileReader(file));
	}
	
	public static String readFileAsString(InputStream in) throws IOException
	{
		return readFileAsString(new InputStreamReader(in));
	}
	
	public static  String readFileAsString(InputStreamReader in) throws IOException
	{
		// do something
		String str = "";
		String line = "";
		try{
			BufferedReader bf = new BufferedReader(in);

			while ((line = bf.readLine()) !=null)
			{
				str +=line;
			}
			bf.close();
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}
		
		return str;
	}
	
	public static String[] readFileAsArray(String path) throws IOException
	{
		return readFileAsArray(new File(path));
	}
		
	public static String[] readFileAsArray(File file) throws IOException
	{
		return readFileAsArray(new FileReader(file));
	}
	
	public static String[] readFileAsArray(InputStream in) throws IOException
	{
		return readFileAsArray(new InputStreamReader(in));
	}
	
	public static String[] readFileAsArray(InputStreamReader in) throws IOException
	{
		// do something
		Vector<String> str = new Vector<String>();
		String line = "";
		try{
			BufferedReader bf = new BufferedReader(in);

			while ((line = bf.readLine()) !=null)
			{
				str.add(line.trim());
			}
			
			bf.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return str.toArray(new String[str.size()]);
	}
	
	public static String readFromConsole(String out) throws IOException
	{
		 Console c = System.console();
	        if (c == null) {
	            System.err.println("No console.");
				throw new IOException("Attempt to write and read from non-existing console");
	        }

	        return c.readLine(out);
	}

	
	public static String readFromStdIn(String out) throws IOException
	{

		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        
			System.out.print(out);
	     
			return br.readLine();
	     //return c.readLine(out);
		}
		catch(IOException ioe)
		{
			System.err.println("Bad input!");
			throw ioe;
		}
	}
}
