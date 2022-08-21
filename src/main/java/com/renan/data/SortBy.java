package com.renan.data;

/**
 * @author renan.arend@visual-meta.com
 * @since 21.08.2022
 */
public class SortBy
{
	private String field;
	private String direction;

	public SortBy(String sortBy)
	{
		String[] sortBySplit = sortBy.split(",");
		this.field = sortBySplit[0];
		this.direction = sortBySplit.length > 1 ? sortBySplit[1] : "ASC";
	}

	public SortBy(String field, String direction)
	{
		this.field = field;
		this.direction = direction;
	}

	public String getField()
	{
		return field;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}
}
