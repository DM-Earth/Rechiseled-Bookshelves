package com.dm.earth.rechiseled_bookshelves;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RechiseledBookshelves {
	public static final String MODID = "rechiseled_bookshelves";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier asIdentifier(String id) {
		return new Identifier(MODID, id);
	}
}
