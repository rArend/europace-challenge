package com.renan.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.DocumentsFetchException;
import com.renan.data.Document;
import com.renan.data.enums.TypeEnum;
import com.renan.filters.DocumentFilters;
import com.renan.repository.DocumentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@ExtendWith(MockitoExtension.class)
class DocumentServiceTest
{

	@InjectMocks
	private DocumentService documentService;

	@Mock
	private DocumentRepository documentRepository;

	@BeforeEach
	public void before() throws DocumentsFetchException
	{
		Mockito.when(documentRepository.getDocuments()).thenReturn(readFromFile());
	}

	@Test
	public void documentsNullFilter() throws DocumentsFetchException
	{
		validateCount(null);
		validateSize(null);
		validateAverageSize(null);
	}

	@Test
	public void deletedDocuments() throws DocumentsFetchException
	{
		DocumentFilters filters = new DocumentFilters();
		filters.setDeleted(true);
		validateCount(filters);
		validateSize(filters);
		validateAverageSize(filters);
	}

	@Test
	public void categoryDocuments() throws DocumentsFetchException
	{
		DocumentFilters filters = new DocumentFilters();
		List<String> categories = new ArrayList<>();
		categories.add("cat_4");
		filters.setCategories(categories);
		validateCount(filters);
		validateSize(filters);
		validateAverageSize(filters);

	}

	@Test
	public void typeDocuments() throws DocumentsFetchException
	{
		DocumentFilters filters = new DocumentFilters();
		filters.setType(TypeEnum.PDF.name());
		validateCount(filters);
		validateSize(filters);
		validateAverageSize(filters);
	}

	@Test
	public void invalidCategoryDocuments() throws DocumentsFetchException
	{
		DocumentFilters filters = new DocumentFilters();
		List<String> categories = new ArrayList<>();
		categories.add("invalid_category");
		filters.setCategories(categories);
		validateCount(filters);
		validateSize(filters);
		validateAverageSize(filters);
	}

	@Test
	public void unknownTypeDocuments() throws DocumentsFetchException
	{
		DocumentFilters filters = new DocumentFilters();
		filters.setType(TypeEnum.UNKNOWN.name());
		validateCount(filters);
		validateSize(filters);
		validateAverageSize(filters);
	}

	private void validateCount(DocumentFilters filters) throws DocumentsFetchException
	{
		List<Document> documents = documentService.getFilteredDocuments(filters);
		int count = documentService.countDocuments(filters);
		assertEquals(documents.size(), count);
	}

	private void validateSize(DocumentFilters filters) throws DocumentsFetchException
	{
		List<Document> documents = documentService.getFilteredDocuments(filters);
		long size = documentService.sizeOfDocuments(filters);
		if (!documents.isEmpty()) {
			assertNotEquals(0, size);
		} else {
			assertEquals(0, size);
		}
	}

	private void validateAverageSize(DocumentFilters filters) throws DocumentsFetchException
	{
		List<Document> documents = documentService.getFilteredDocuments(filters);
		double size = documentService.averageSizeOfDocuments(filters);
		if (!documents.isEmpty()) {
			assertNotEquals(0.0, size);
		} else {
			assertEquals(0.0, size);
		}
	}

	private List<Document> readFromFile()
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			URL url = Thread.currentThread().getContextClassLoader().getResource("documents-mock.json");
			assert url != null;
			return objectMapper.readValue(new File(url.toURI()),  new TypeReference<>(){});
		} catch (IOException ioe) {
			return new ArrayList<>();
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}
}
