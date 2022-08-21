package com.renan.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.renan.data.enums.TypeEnum;

public class Document
{
	private String id;
	private String name;
	private Long size;
	private TypeEnum type;
	private List<String> categories;
	private Boolean deleted;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime modifiedAt;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getSize()
	{
		return size;
	}

	public void setSize(Long size)
	{
		this.size = size;
	}

	public TypeEnum getType()
	{
		return type;
	}

	public void setType(TypeEnum type)
	{
		this.type = type;
	}

	public List<String> getCategories()
	{
		return categories;
	}

	public void setCategories(List<String> categories)
	{
		this.categories = categories;
	}

	public Boolean getDeleted()
	{
		return deleted;
	}

	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public LocalDateTime getModifiedAt()
	{
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt)
	{
		this.modifiedAt = modifiedAt;
	}
}
