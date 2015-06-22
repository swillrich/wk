package de.wk.holiday.day;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import de.wk.holiday.Month;

public class Ostersonntag extends Holiday {

	public Ostersonntag(int year) {
		super(new FixHoliday(null, year, Month.January, 1));
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return ostertag(day.getDate().get(DateTimeFieldType.year()));
	}

	public DateTime ostertag(int jahr) {
		int a, b, c, k, p, q, M, N, d, e, ostern;
		a = jahr % 19;
		b = jahr % 4;
		c = jahr % 7;
		k = jahr / 100;
		p = (8 * k + 13) / 25;
		q = k / 4;
		M = (15 + k - p - q) % 30;
		N = (4 + k - q) % 7;
		d = (19 * a + M) % 30;
		e = (2 * b + 4 * c + 6 * d + N) % 7;
		if (d + e == 35)
			ostern = 50;
		else if (d == 28 && e == 6 && (11 * M + 11) % 30 < 19)
			ostern = 49;
		else
			ostern = 22 + d + e;
		
		if (ostern < 31) {
			return new DateTime(jahr, 3, ostern, 0, 0, 0);
		} else {
			return new DateTime(jahr, 4, ostern - 31, 0, 0, 0);
		}
	}

}
