package com.renan.repository;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.renan.DocumentsFetchException;
import com.renan.data.Document;

@Repository
public class DocumentRepository
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRepository.class);
	private static final String TARGET = "http://localhost:8080/v1/documents";

	public List<Document> getDocuments() throws DocumentsFetchException
	{

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target(TARGET);

		Invocation.Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		try
		{
			Response response = request.get();
			if (Response.Status.Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily()))
			{
				LOGGER.debug("Success Document request! " + response.getStatus());
				return response.readEntity(new GenericType<>()
				{
				});
			}
			else
			{
				LOGGER.debug("ERROR! " + response.getStatus());
				throw new Exception("Error getting documents from repository" + response.getEntity());
			}
		}
		catch (Exception e)
		{
			throw new DocumentsFetchException(e.getMessage());
		}

	}

}
