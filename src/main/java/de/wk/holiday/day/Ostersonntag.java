package de.wk.holiday.day;

import org.joda.time.DateTime;

public class Ostersonntag extends Holiday {

	DateTime osterdate;

	public Ostersonntag(int year) {
		super(Ostersonntag.class.getSimpleName(), null);
		int ostertag = new Osterformel(year).ostertag();
		int month = 3;
		if (ostertag >= 31) {
			month += 1;
			ostertag -= 31;
		}
		osterdate = new DateTime(year, month, ostertag, 1, 0, 0);
	}

	@Override
	public DateTime dependsOn(Holiday day) {
		return osterdate;
	}

	class Osterformel {
		private int jahr;
		int a, b, c, k, p, q, M, N, d, e, ostern;

		public Osterformel(int jahr) {
			this.jahr = jahr;
		}

		public int ostertag() {
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
				return ostern = 50;
			else if (d == 28 && e == 6 && (11 * M + 11) % 30 < 19)
				return ostern = 49;
			else
				return ostern = 22 + d + e;
		}

	}

}
