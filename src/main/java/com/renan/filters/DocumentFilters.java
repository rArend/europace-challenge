package com.renan.filters;

import java.util.List;

public class DocumentFilters
{
	private Boolean deleted;
	private List<String> categories;
	private String type;

	public Boolean getDeleted()
	{
		return deleted;
	}

	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	public List<String> getCategories()
	{
		return categories;
	}

	public void setCategories(List<String> categories)
	{
		this.categories = categories;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
