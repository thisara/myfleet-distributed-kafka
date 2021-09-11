package com.thisara.connectors;

import java.time.LocalDateTime;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-08-30
 */
@Repository
public class JavaMongoConnect {
	
	private Logger logger = LoggerFactory.getLogger(JavaMongoConnect.class);
	
	private static JavaMongoConnect javaMongoConnect;
	
	@Autowired
	private Environment env;
	
	private JavaMongoConnect() {
		
	}
	
	@Bean //To set configuration by spring
	public static final synchronized JavaMongoConnect getJavaMongoConnect() {
		if(javaMongoConnect == null) {
			javaMongoConnect = new JavaMongoConnect();
		}
		return javaMongoConnect;
	}

	public boolean saveMessage(String key, String message, LocalDateTime localDateTime, String creator, JSONObject topicInfo) {
		
		boolean saveStatus = false;
		
		MongoClient mongoClient = new MongoClient(env.getProperty("db.host"),Integer.parseInt(env.getProperty("db.port")));
		
		try {
		
			ObjectMapper objectMapper = new ObjectMapper();
			
			int defaultStatus = 1;
			
			JsonNode jsonNode = objectMapper.readTree(message);
			
			JsonNode serverInfo = jsonNode.get("server-info");
			JsonNode exceptionDetail = jsonNode.get("exception-detail");
			
			JsonNode methodSignature = exceptionDetail.get("method-signature");
			JsonNode apiResponse = exceptionDetail.get("api-response");
			JsonNode stacktrace = exceptionDetail.get("stacktrace");
			
			MongoCollection<Document> errorLogCollection = mongoClient.getDatabase(env.getProperty("db.name")).getCollection(env.getProperty("db.collection"));
	        
			Document errorLog = new Document("_id", new ObjectId());
			
			errorLog.append("error_id", key)
					.append("method_signature", methodSignature.asText())
					.append("api_response", apiResponse.asText())
					.append("stacktrace", stacktrace.asText())
					.append("service_id", "CAR")
					.append("topic_info", topicInfo.toString())
					.append("server-info", serverInfo.asText())
					.append("status", defaultStatus)
					.append("reported_date", localDateTime)
					.append("reported_by", creator)
					.append("updated_date", "")
					.append("updated_by", "");
					
			logger.info("Inserting " + errorLog);
			
			errorLogCollection.insertOne(errorLog);
			
			mongoClient.close();
			
	        saveStatus = true;
        
		}catch(Exception e) {
			logger.error("Something went wrong : " + e.getLocalizedMessage());
		}finally {
			mongoClient.close();
		}
        
		return saveStatus;
	}
}
