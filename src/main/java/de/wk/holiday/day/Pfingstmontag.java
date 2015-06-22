package de.wk.holiday.day;

import org.joda.time.DateTime;

public class Pfingstmontag extends Holiday<Ostersonntag> {

	public Pfingstmontag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(50);
	}

}
