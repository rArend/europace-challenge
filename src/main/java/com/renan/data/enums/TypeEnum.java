package com.renan.data.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author renan.arend@visual-meta.com
 * @since 20.08.2022
 */
public enum TypeEnum
{
	PDF,
	IMAGE,
	UNKNOWN;

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeEnum.class);
	public static TypeEnum valueOfOrDefault(String type) {
		try {
			return valueOf(type);
		} catch (Exception e) {
			LOGGER.debug("Invalid type filter: {}", type);
			return UNKNOWN;
		}
	}
}
