package de.wk.holiday.day;

import org.joda.time.DateTime;


public class ChristiHimmelfahrt extends Holiday<Ostersonntag> {


	public ChristiHimmelfahrt(String name, Ostersonntag holiday) {
		super(name, holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(39);
	}

}
