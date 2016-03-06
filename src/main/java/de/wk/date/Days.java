package de.wk.date;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.holiday.Holiday;
import de.wk.date.holiday.VacationDay;

/**
 * This class is a WKDateTime ArrayList (and therefore implicit a collection)
 * and represents a list of days.
 */
@SuppressWarnings("serial")
public class Days extends ArrayList<WKDateTime> {

	public Days(Interval interval) {
		for (DateTime d = interval.getStart(); interval.getEnd().compareTo(d) >= 0; d = d.plusDays(1)) {
			add(new WKDateTime(d));
		}
	}

	public Days() {

	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Days :\n");
		for (WKDateTime h : this) {
			try {
				b.append(h + " TYPE: " + determineKindOf(h.getDateTime()).name().toString() + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b.toString();
	}

	/**
	 * This method returns a WkDateTime object (specific date object) containing
	 * by this object. The methods iterates all objects the collection contains
	 * and returns the object whose date is equal to the date of the given
	 * DateTime object.
	 * 
	 * @param d
	 *            The DateTime object which contains the date this method is
	 *            searching for.
	 * @return The WKDateTime object, if found. Null otherwise.
	 * @throws Exception
	 */
	public WKDateTime findByDate(DateTime d) throws Exception {
		for (WKDateTime h : this) {
			if (h.getJodaDateTime().withTimeAtStartOfDay().isEqual(d.withTimeAtStartOfDay())) {
				return h;
			}
		}
		throw new Exception("The given day " + d.toString() + " is not contained.");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object wkDateTime : c) {
			if (!(wkDateTime instanceof WKDateTime)) {
				throw new ClassCastException(
						"The elements being contained in the given collection may only be of the type WKDateTime.");
			}
			try {
				findByDate(((WKDateTime) wkDateTime).getJodaDateTime());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * This method provides the kind of day of a specific day.
	 * 
	 * @param dateTime
	 *            The DateTime object which contains the date this method is
	 *            searching for.
	 * @return The kind of the day
	 */
	public KindOfDay determineKindOf(DateTime dateTime) {
		try {
			WKDateTime day = findByDate(dateTime);
			if (day instanceof Holiday<?>) {
				return KindOfDay.HOLIDAY;
			} else if (day instanceof VacationDay) {
				return KindOfDay.VACATIONDAY;
			}
		} catch (Exception e) {
			// not needed
		}
		if (dateTime.getDayOfWeek() == 6 || dateTime.getDayOfWeek() == 7) {
			return KindOfDay.WEEKEND;
		} else {
			return KindOfDay.WORKDAY;
		}
	}

	/**
	 * This method returns a specific holiday by name.
	 * 
	 * @param name
	 *            The name which is equal to the holiday name which should be
	 *            searching for
	 * @return The holiday object if a holiday with this name is found. Null
	 *         otherwise.
	 * @throws Exception
	 */
	public WKDateTime findByName(String name) throws Exception {
		for (WKDateTime e : this) {
			if (e.getName().equalsIgnoreCase(name)) {
				return e;
			}
		}
		throw new Exception("Could not found any object where the name is equal to " + name);
	}

	public void mergeDays(Days days) throws Exception {
		for (WKDateTime wkDateTime : days) {
			replaceDay(wkDateTime);
		}
	}

	public void replaceDay(WKDateTime dateTime) throws Exception {
		WKDateTime date = findByDate(dateTime.getDateTime());
		int toReplaceIndex = indexOf(date);
		remove(toReplaceIndex);
		add(toReplaceIndex, dateTime);
	}
}
