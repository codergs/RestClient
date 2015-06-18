package com.gaurav.client;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVReader;

 
public class JerseyClientGet {
 
	  // Main method starts here
	  public static void main(String[] args) {
		  
			JerseyClientGet client = new JerseyClientGet();
			/**
			 * Open and read a file, and return the lines in the file as a list
			 * of Strings.
			 * (Demonstrates Java FileReader, BufferedReader, and Java5.)
			 */
			  String pathToCsv="/Users/ltadmin/Downloads/sections1.csv";
			  List<String> records = new ArrayList<String>();
			  try
			  {
			    BufferedReader reader = new BufferedReader(new FileReader("/Users/ltadmin/Desktop/token.txt"));
			    String line;
			    while ((line = reader.readLine()) != null)
			    {
			      records.add(line);
			    }
			    reader.close();
			   /* 
			    for (String s : records) {
			        System.out.println(s);
			    }
			   */
			    client.getResponse(records.get(0),pathToCsv,records.get(1));
			  }
			  catch (Exception e)
			  {
			    System.err.format("Exception occurred trying to read '%s'.", "/Users/ltadmin/Desktop/token.txt");
			    e.printStackTrace();
			  }
			  
			
	} // main ends here
	 
	// function to parse the workflow_state from the Json object
	private void jsonParse(String jsonResponse){
		
		JSONParser jsonParser = new JSONParser();
	    try{
	    	
	    	JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonResponse);	  
	    	// get a String from the JSON object
	    	
	    	String status = (String) jsonObject.get("workflow_state");
	    	System.out.println("The state is: " + status);
	      
	    }catch(ParseException pe){
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	      }
	}// jsonParse function ends here 

	
	//Get JSON response from Web
	private void getResponse(String token, String pathToCsv, String link) {
		
		ArrayList<String> sisIds = new ArrayList<String>();

		 CSVReader reader;
		try {
			
			//Build reader instance
		      reader = new CSVReader(new FileReader(pathToCsv));
		       
		      //Read all rows at once
		      List<String[]> allRows = reader.readAll();
		       
		      //Read CSV line by line and inserting sis id into a list 
		     for(String[] row : allRows){
		    	 String[] entries = Arrays.toString(row).split(",");
				 sisIds.add(entries[1]);
		     }
		     
		     for(int i=1; i<sisIds.size();i++){
		    	 System.out.println(sisIds.get(i).trim());
		    	 try {
						Client client = Client.create();
						WebResource webResource = client.resource(link+sisIds.get(i).trim()+"?access_token="+token.trim());
						ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
						if (response.getStatus() != 200) {
							throw new Exception("Failed : HTTP error code : " + response.getStatus());
						}
						
						String output = response.getEntity(String.class);
						System.out.println("============getResponse============");
						//System.out.println(output);
						jsonParse(output);
				
					} catch (Exception e) {
						e.printStackTrace();
						}
		    	 System.out.println();
		     }
		     
		     
		        
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2){
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
				
	} // getResponse ends here

}
