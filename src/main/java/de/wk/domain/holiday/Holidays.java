package de.wk.domain.holiday;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;

import de.wk.domain.WKDateTime;
import de.wk.domain.WKDateTime.KindOfDay;

/**
 * This class is a WKDateTime ArrayList (and therefore implicit a collection)
 * and represents a list of days. The day can be each kind of day. <br>
 * Please note that <b>not</b> all gaps between arbitrary (holy-)days has been
 * filled. Only days being holidays are containing. So it could be that one
 * specific day being a non-holiday is embedded between two arbitrary holidays,
 * whereby the non-holiday is not placed explicitly.
 */
@SuppressWarnings("serial")
public class Holidays extends ArrayList<WKDateTime> {

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Days :\n");
		for (WKDateTime h : this) {
			b.append(h + "\n");
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
	 */
	public WKDateTime findByDate(DateTime d) {
		for (WKDateTime h : this) {
			if (h.getJodaDateTime().withTimeAtStartOfDay()
					.isEqual(d.withTimeAtStartOfDay())) {
				return h;
			}
		}
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object wkDateTime : c) {
			if (!(wkDateTime instanceof WKDateTime)) {
				throw new ClassCastException(
						"The elements being contained in the given collection may only be of the type WKDateTime.");
			}
			WKDateTime dateTime = findByDate(((WKDateTime) wkDateTime)
					.getJodaDateTime());
			if (dateTime == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method provides the kind of day of a specific day. <br>
	 * If the day is a vacation day or a holiday, this information will
	 * provided. <br>
	 * If the day is not a vacation day and not a holiday, this method will
	 * return the information, whether the day is a week oder weekend day.
	 * 
	 * @param dateTime
	 *            The DateTime object which contains the date this method is
	 *            searching for.
	 * @return The kind of the day
	 */
	public KindOfDay determineKindOf(DateTime dateTime) {
		KindOfDay kindOf = null;
		WKDateTime holiday = findByDate(dateTime);
		if (holiday != null) {
			if (holiday instanceof VariableHoliday) {
				kindOf = KindOfDay.VACATIONDAY;
			} else {
				kindOf = KindOfDay.HOLIDAY;
			}
		} else if (dateTime.getDayOfWeek() == 7 || dateTime.getDayOfWeek() == 6) {
			kindOf = KindOfDay.WEEKEND;
		} else {
			kindOf = KindOfDay.WEEK;
		}
		return kindOf.setDateTime(dateTime);
	}

	/**
	 * This method returns a specific holiday by name.
	 * 
	 * @param name
	 *            The name which is equal to the holiday name which should be
	 *            searching for
	 * @return The holiday object if a holiday with this name is found. Null
	 *         otherwise.
	 */
	public Holiday<?> findHolidayByName(String name) {
		for (WKDateTime e : this) {
			if (e instanceof Holiday<?>) {
				if (((Holiday<?>) e).getName().equalsIgnoreCase(name)) {
					return (Holiday<?>) e;
				}
			}
		}
		return null;
	}
}
