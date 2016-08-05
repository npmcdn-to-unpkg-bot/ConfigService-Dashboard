package no.cantara.csdb.commands;

import java.net.URI;
import java.util.Base64;

import com.github.kevinsawicki.http.HttpRequest;

import no.cantara.csdb.config.ConfigValue;
import no.cantara.csdb.config.ConstantValue;
import no.cantara.csdb.util.BaseHttpGetHystrixCommand;
import no.cantara.csdb.util.BaseHttpPostHystrixCommand;
import no.cantara.csdb.util.HttpSender;

public abstract class BasePostCommand<T> extends BaseHttpPostHystrixCommand<T>{

	public BasePostCommand(String hystrixGroupKey) {
		super(URI.create(ConfigValue.CONFIGSERVICE_URL), hystrixGroupKey, ConstantValue.COMMAND_TIMEOUT);
	}
	
	public BasePostCommand() {
		super(URI.create(ConfigValue.CONFIGSERVICE_URL), "command_group", ConstantValue.COMMAND_TIMEOUT);
	}
	
	@Override
	protected HttpRequest dealWithRequestBeforeSend(HttpRequest request) {
		String usernameAndPassword = ConfigValue.CONFIGSERVICE_USERNAME + ":" + ConfigValue.CONFIGSERVICE_PASSWORD;
		String encoded = Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
		request.authorization("Basic " + encoded);
		request.contentType(HttpSender.APPLICATION_JSON);
		return super.dealWithRequestBeforeSend(request);
	}
}
