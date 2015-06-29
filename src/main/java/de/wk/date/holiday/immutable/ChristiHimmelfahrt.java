package de.wk.date.holiday.immutable;

import org.joda.time.DateTime;

import de.wk.date.holiday.Holiday;


public class ChristiHimmelfahrt extends Holiday<Ostersonntag> {


	public ChristiHimmelfahrt(String name, Ostersonntag holiday) {
		super(name, holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(39);
	}

}
