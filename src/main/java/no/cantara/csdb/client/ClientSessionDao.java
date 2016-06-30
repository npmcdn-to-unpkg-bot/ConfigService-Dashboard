package no.cantara.csdb.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.cantara.cs.dto.Client;
import no.cantara.cs.dto.ClientStatus;
import no.cantara.csdb.commands.CommandGetAllClients;
import no.cantara.csdb.commands.CommandGetClientStatus;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public enum ClientSessionDao {

	instance;
	private ClientSessionDao(){
		
	}
	
	public String getAllClientStatuses(){
		String clientsJson = new CommandGetAllClients().execute();
		if(clientsJson!=null){
			ObjectMapper mapper = new ObjectMapper();
			List<ClientStatus> clientStatusList = new ArrayList<ClientStatus>();
			try {
				List<Client> clients = Arrays.asList(mapper.readValue(clientsJson, Client[].class));
				for(Client client: clients){
					String clientStatusJson = new CommandGetClientStatus(client.clientId).execute();
					if(clientStatusJson!=null){
						ClientStatus clientStatus = mapper.readValue(clientStatusJson, ClientStatus.class);
						clientStatusList.add(clientStatus);
					}
				}
				return  mapper.writeValueAsString(clientStatusList);

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	
}