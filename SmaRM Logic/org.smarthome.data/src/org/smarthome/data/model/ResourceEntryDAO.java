package org.smarthome.data.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public enum ResourceEntryDAO {
	instance;

	private Map<String, ResourceTableEntry> contentProvider = new HashMap<String, ResourceTableEntry>();

	// Water and energy prices in EUR
	private final double WATER_PRICE_PER_LITER = 1.9;
	private final double ENERGY_PRICE_PER_KWH = 0.25;

	private ResourceEntryDAO() {

		ResourceTableEntry resourceEntry = new ResourceTableEntry();
		try {
			resourceEntry.setDateTime("2012-07-01 08:04:00");
			resourceEntry.setLocation("living room");
			resourceEntry.setActivityLabel("heating");
			resourceEntry.setDurationMin(120);
			resourceEntry.setEnergyConsumptionKwh(4);
			resourceEntry.setWaterConsumptionCm3(0);
			resourceEntry.calculateEnergyCostsEur(ENERGY_PRICE_PER_KWH, WATER_PRICE_PER_LITER);
			contentProvider.put("0", resourceEntry);

			resourceEntry = new ResourceTableEntry();
			resourceEntry.setDateTime("2012-07-01 09:20:00");
			resourceEntry.setLocation("bath");
			resourceEntry.setActivityLabel("showering");
			resourceEntry.setDurationMin(20);
			resourceEntry.setEnergyConsumptionKwh(0.5);
			resourceEntry.setWaterConsumptionCm3(0.04);
			resourceEntry.calculateEnergyCostsEur(ENERGY_PRICE_PER_KWH, WATER_PRICE_PER_LITER);
			contentProvider.put("1", resourceEntry);
			
			resourceEntry = new ResourceTableEntry();
			resourceEntry.setDateTime("2012-07-01 01:20:00");
			resourceEntry.setLocation("living room");
			resourceEntry.setActivityLabel("cooling");
			resourceEntry.setDurationMin(20);
			resourceEntry.setEnergyConsumptionKwh(2);
			resourceEntry.setWaterConsumptionCm3(0);
			resourceEntry.calculateEnergyCostsEur(ENERGY_PRICE_PER_KWH, WATER_PRICE_PER_LITER);
			contentProvider.put("2", resourceEntry);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Map<String, ResourceTableEntry> getModel() {
		return contentProvider;
	}

}