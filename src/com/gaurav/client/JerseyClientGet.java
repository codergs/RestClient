package com.gaurav.client;
 
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
 
public class JerseyClientGet {
 
	  // Main method starts here
	  public static void main(String[] args) {
		  
			JerseyClientGet client = new JerseyClientGet();
			client.getResponse();
	} // main ends here
	 
	//
	private void getResponse() {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource("http://date.jsontest.com");
			ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			String output = response.getEntity(String.class);
			System.out.println("============getResponse============");
			System.out.println(output);
	
		} catch (Exception e) {
			e.printStackTrace();
			}
	} // getResponse ends here

}
