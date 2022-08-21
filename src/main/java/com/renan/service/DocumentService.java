package com.renan.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renan.DocumentsFetchException;
import com.renan.data.Document;
import com.renan.data.SortBy;
import com.renan.data.enums.TypeEnum;
import com.renan.filters.DocumentComparator;
import com.renan.filters.DocumentFilters;
import com.renan.repository.DocumentRepository;

@Service
public class DocumentService
{
	private final DocumentRepository documentRepository;

	@Autowired
	public DocumentService(DocumentRepository documentRepository)
	{
		this.documentRepository = documentRepository;
	}

	public int countDocuments(final DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return getFilteredDocuments(documentFilters).size();
	}

	public long sizeOfDocuments(final DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return getSizeOfDocuments(documentFilters);
	}

	public double averageSizeOfDocuments(final DocumentFilters documentFilters) throws DocumentsFetchException
	{
		List<Document> filteredDocuments = getFilteredDocuments(documentFilters);
		if (filteredDocuments.isEmpty()) {
			return 0;
		}
		int documentsSize = getSizeOfDocuments(filteredDocuments);
		return (double) documentsSize / filteredDocuments.size();

	}

	private long getSizeOfDocuments(final DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return getSizeOfDocuments(getFilteredDocuments(documentFilters));
	}

	private int getSizeOfDocuments(final List<Document> documents) {
		return documents.stream()
			.filter(Objects::nonNull)
			.map(Document::getSize)
			.map(Math::toIntExact)
			.reduce(0, Integer::sum);
	}

	public List<Document> getFilteredDocuments(final DocumentFilters documentFilters, final String sortBy) throws DocumentsFetchException
	{
		List<Document> documents = applyFilters(documentRepository.getDocuments(), documentFilters);
		if (sortBy == null || sortBy.isEmpty()) {
			return documents;
		}
		documents.sort(new DocumentComparator(new SortBy(sortBy))::sort);
		return documents;
	}

	public List<Document> getFilteredDocuments(final DocumentFilters documentFilters) throws DocumentsFetchException
	{
		List<Document> documents = documentRepository.getDocuments();

		return applyFilters(documents, documentFilters != null ? documentFilters : new DocumentFilters());
	}

	private List<Document> applyFilters(final List<Document> documents, final DocumentFilters documentFilters)
	{
		if (documents.isEmpty())
		{
			return documents;
		}
		Boolean deleted = documentFilters.getDeleted();
		List<String> categories = documentFilters.getCategories();
		String typeString = documentFilters.getType();

		Predicate<Document> predicate = Objects::nonNull;
		if (deleted != null)
		{
			predicate = predicate.and(document -> document.getDeleted().equals(deleted));
		}
		if (categories != null && !categories.isEmpty())
		{
			predicate = predicate.and(document -> new HashSet<>(document.getCategories()).containsAll(categories));
		}

		if (typeString != null)
		{
			TypeEnum type = TypeEnum.valueOfOrDefault(typeString);
			predicate = predicate.and(document -> document.getType().equals(type));
		}
		return documents.stream().filter(predicate).collect(Collectors.toList());

	}

}
