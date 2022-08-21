package com.renan.filters;

import java.lang.reflect.Field;

import org.springframework.util.comparator.ComparableComparator;

import com.renan.data.Document;
import com.renan.data.SortBy;

public class DocumentComparator
{
	private final String fieldName;
	private final String direction;

	public DocumentComparator(SortBy sortCriteria)
	{
		this.fieldName = sortCriteria.getField();
		this.direction = sortCriteria.getDirection();
	}

	public int sort(Document one, Document two)
	{
		Comparable<?> fieldValueOne = getFieldValue(fieldName, one);
		Comparable<?> fieldValueTwo = getFieldValue(fieldName, two);

		if (fieldValueOne == null || fieldValueTwo == null)
		{
			return 0;
		}

		if (direction.equalsIgnoreCase("DESC"))
		{
			return compare(fieldValueTwo, fieldValueOne);
		}
		return compare(fieldValueOne, fieldValueTwo);
	}

	private int compare(Comparable<?> valueOne, Comparable<?> valueTwo)
	{
		return ComparableComparator.INSTANCE.compare(valueOne, valueTwo);
	}

	private static Comparable<?> getFieldValue(String fieldName, Document document)
	{
		Field field;
		try
		{
			field = document.getClass().getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e)
		{
			return null;
		}
		field.setAccessible(true);
		try
		{
			return (Comparable<?>) field.get(document);
		}
		catch (IllegalAccessException e)
		{
			return null;
		}
	}
}



