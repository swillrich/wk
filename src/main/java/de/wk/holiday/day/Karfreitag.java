package de.wk.holiday.day;

import org.joda.time.DateTime;


public class Karfreitag extends Holiday<Ostersonntag> {

	public Karfreitag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().minusDays(2);
	}

}
