package com.renan.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renan.validation.DocumentsFetchException;
import com.renan.data.Document;
import com.renan.filters.DocumentFilters;
import com.renan.service.DocumentService;

/**
 * @author renan.arend@visual-meta.com
 * @since 20.08.2022
 */
@RestController
@RequestMapping("/v1/document")
public class DocumentsController
{
	private final DocumentService documentService;

	@Autowired
	public DocumentsController(DocumentService documentService)
	{
		this.documentService = documentService;
	}

	/**
	 * --- Documents ---
	 * --- Filter by Deleted documents (deleted = true) ---
	 * --- Filter by Categories ---
	 * --- Filter by Type ---
	 * --- Sort by any Document field ASC or DESC ex: (name,ASC) = default (id,ASC)
	 * @param documentFilters filter of documents search
	 * @param sortBy sortBy field and direction
	 * @return List of Documents
	 */
	@GetMapping("/")
	public ResponseEntity<List<Document>> getDocuments(@Validated DocumentFilters documentFilters,
		@RequestParam(value = "sort", defaultValue = "id,ASC", required = false) String sortBy) throws DocumentsFetchException
	{
		return ResponseEntity.ok(documentService.getFilteredDocuments(documentFilters, sortBy));
	}

	/**
	 * Return count of found documents based on filters
	 * --- Total number of found documents ---
	 * --- Filter by Deleted documents (deleted = true) ---
	 * --- Filter by Categories ---
	 * --- Filter by Type ---
	 *
	 * @param documentFilters filter of documents search
	 * @return Count of Documents
	 */
	@GetMapping("/count")
	public ResponseEntity<Integer> numberOfDocuments(@Validated DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return ResponseEntity.ok(documentService.countDocuments(documentFilters));
	}

	/**
	 * --- Total size (sum) of found documents ---
	 * --- Filter by Deleted documents (deleted = true) ---
	 * --- Filter by Categories ---
	 * --- Filter by Type ---
	 * @param documentFilters
	 * @return Size of documents
	 * @throws DocumentsFetchException
	 */
	@GetMapping("/size")
	public ResponseEntity<Long> sizeOfDocuments(@Validated DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return ResponseEntity.ok(documentService.sizeOfDocuments(documentFilters));
	}

	/**
	 * --- Average size (size-sum/count) of found documents ---
	 * --- Filter by Deleted documents (deleted = true/false/null) ---
	 * --- Filter by Categories ---
	 * --- Filter by Type ---
	 * @param documentFilters
	 * @return Average size of Documents
	 * @throws DocumentsFetchException
	 */
	@GetMapping("/average-size")
	public ResponseEntity<Double> averageSizeOfDocuments(@Validated DocumentFilters documentFilters) throws DocumentsFetchException
	{
		return ResponseEntity.ok(documentService.averageSizeOfDocuments(documentFilters));
	}

}


