package de.wk.holiday.day;

import org.joda.time.DateTime;

public class Ostermontag extends Holiday<Ostersonntag> {

	public Ostermontag(Ostersonntag holiday) {
		super(holiday);
	}

	@Override
	public DateTime dependsOn(Ostersonntag day) {
		return day.getDate().plusDays(1);
	}

}
