package no.cantara.csdb.commands;

public class CommandGetClientAppConfig extends BaseGetCommand<String> {

	private String clientId;
	public CommandGetClientAppConfig(String clientId){
		this.clientId = clientId;
	}
	
	@Override
	protected String getTargetPath() {
		return "client/" + clientId + "/config";
	}

	
}