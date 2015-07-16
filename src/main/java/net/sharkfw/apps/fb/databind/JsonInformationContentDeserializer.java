package net.sharkfw.apps.fb.databind;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JsonInformationContentDeserializer<T> implements InformationContentDeserializer<T> {

	private static final Logger LOG = LoggerFactory.getLogger(JsonInformationContentDeserializer.class);
	private ObjectMapper objectMapper;

	@Autowired
	public JsonInformationContentDeserializer() {
		objectMapper = new ObjectMapper();
	}

	public JsonInformationContentDeserializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public T deserialize(Information information, Class<T> type) throws IOException, SharkKBException {
		byte[] buffer = information.getContentAsByte();
		return objectMapper.readValue(buffer, type);
	}

	@Override
	public List<T> deserializeList(Information information, Class<T> type) throws IOException, SharkKBException {
		byte[] buffer = information.getContentAsByte();
		return objectMapper.readValue(buffer, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
	}

	@Override
	public boolean isDeserializeAble(Class<T> type, Information information) throws IOException, SharkKBException {
		return true;
	}

}
