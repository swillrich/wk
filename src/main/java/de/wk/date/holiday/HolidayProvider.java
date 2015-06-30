package de.wk.date.holiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.holiday.immutable.BussUndBettag;
import de.wk.date.holiday.immutable.ChristiHimmelfahrt;
import de.wk.date.holiday.immutable.FixHoliday;
import de.wk.date.holiday.immutable.Karfreitag;
import de.wk.date.holiday.immutable.Ostermontag;
import de.wk.date.holiday.immutable.Ostersonntag;
import de.wk.date.holiday.immutable.Pfingstmontag;

public class HolidayProvider {
	private Map<State, List<WKDateTime>> stateHolidayMapping = new HashMap<State, List<WKDateTime>>();
	private Days days;

	public enum State {
		BW, BY, BE, BB, HB, HH, HE, MV, NI, NW, RP, SL, SN, ST, SH, TH;
	}

	public Days provideBy(Interval interval) {
		this.days = new Days();
		DateTime start = interval.getStart();
		DateTime end = interval.getEnd();
		for (int year = start.getYear(); year <= end.getYear(); year++) {
			initializeByYear(year);
		}
		Collections.sort(days);
		return days;
	}

	public Days provideBy(int year) {
		WKDateTime wkDateTime = new WKDateTime(year, 1, 1);
		return provideBy(new Interval(wkDateTime, wkDateTime.getJodaDateTime()
				.plusYears(1).minusDays(1)));
	}

	private void initializeByYear(int year) {
		FixHoliday neujahr = new FixHoliday("Neujahr", year, 1, 1);
		FixHoliday firstCMD = new FixHoliday("Erster Weihnachtstag", year, 12,
				25);
		FixHoliday secondCMD = new FixHoliday("Zweiter Weihnachtstag", year,
				12, 26);
		FixHoliday maifeiertag = new FixHoliday("Maifeiertag", year, 5, 1);
		FixHoliday tdde = new FixHoliday("Tag der Deutschen Einheit", year, 10,
				3);
		BussUndBettag bussUndBettag = new BussUndBettag("Buss und Bettag",
				firstCMD);
		Ostersonntag ostersonntag = new Ostersonntag(year);
		Karfreitag karfreitag = new Karfreitag(ostersonntag);
		Ostermontag ostermontag = new Ostermontag(ostersonntag);
		ChristiHimmelfahrt christiHimmelfahrt = new ChristiHimmelfahrt(
				"Christi Himmelfahrt", ostersonntag);
		Pfingstmontag pfingstmontag = new Pfingstmontag(ostersonntag);

		// TODO: Add missing holidays
		ArrayList<WKDateTime> commonHolidays = new ArrayList<WKDateTime>();
		commonHolidays.add(neujahr);
		commonHolidays.add(firstCMD);
		commonHolidays.add(secondCMD);
		commonHolidays.add(maifeiertag);
		commonHolidays.add(tdde);
		commonHolidays.add(karfreitag);
		commonHolidays.add(ostersonntag);
		commonHolidays.add(ostermontag);
		commonHolidays.add(christiHimmelfahrt);
		commonHolidays.add(pfingstmontag);

		for (State state : State.values()) {
			ArrayList<WKDateTime> tmp = new ArrayList<WKDateTime>();
			tmp.addAll(commonHolidays);
			switch (state) {
			case BW:
			case BY:
				/* Heiligen drei Könige */
				/* Fronleichnam */
				/* Allerheiligen */
				break;
			case BB:
			case MV:
			case TH:
				/* Reformationstag */
				break;
			case NW:
			case RP:
				/* Fronleichnam */
				/* Allerheiligen */
				break;
			case HE:
				/* Fronleichnam */
				break;
			case SL:
				/* Fronleichnam */
				/* Mariä Himmelfahrt */
				/* Allerheiligen */
				break;
			case SN:
				/* Reformationstag */
				tmp.add(bussUndBettag);
				break;
			case ST:
				/* Reformationstag */
				/* Heiligen drei Könige */
				break;
			default:
				break;
			}
			this.stateHolidayMapping.put(state, tmp);
		}

		// FIXME: may obsolete when states are fully implemented
		days.addAll(commonHolidays);
		days.add(bussUndBettag);
	}
}
