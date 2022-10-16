package com.im.service.util.elasticstack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.im.service.common.Config;
import com.im.service.common.Environment;
import com.im.service.common.ServiceGroup;
import com.im.service.rest.WebService;
import com.im.service.util.ws.Ws;

public class ElasticStackLog {
	
	private static final Logger LOG = LoggerFactory.getLogger(ElasticStackLog.class);

	public static void perform (Entry entry) {
		
		if (! Config.ELASTIC_STACK_LOGGING) {
			//LOG.info("Elastic Stack logging disabled..");
			return;
		}
		
		String body=null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		WebService rest = Ws.post(ServiceGroup.ELASTICSTACK, "addDocument", Environment.DEV, null, false, body, 1);		
		if (! ( (rest.getStatus() == 200) || (rest.getStatus() == 201) ) )
			LOG.info("Failed - Elastic stack logging..");
		else
			LOG.info("Success - Elastic stack logging..");

	}
	
}
