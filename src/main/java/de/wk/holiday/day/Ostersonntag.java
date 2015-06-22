package de.wk.holiday.day;

import org.joda.time.DateTime;

public class Ostersonntag extends Holiday {

	public Ostersonntag() {
		//pass null as second param, because there is not dependency
		//in contrast to Ostermontag, who is one day after Ostersonntag: super("Ostermontag", ostersonntag);
		super(Ostersonntag.class.getSimpleName(), null);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		// sample
		return new DateTime();
	}

}
