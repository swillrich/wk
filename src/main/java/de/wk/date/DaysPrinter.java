package de.wk.date;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import de.wk.date.WKDateTime.KindOfDay;

/**
 * This class prints a given days object (which is a collection). <br>
 * A user-friendly output is provided.<br>
 * As a table the days getting printed month by month. The columns are the day
 * starting from 0 until 31.
 *
 */
public class DaysPrinter {

	private Days days;
	private int columnWidth = 6;
	private List<DayAppearanceModifier> dayAppearanceModifierList = new ArrayList<DayAppearanceModifier>();
	private boolean hideWeekdays = true;
	private boolean hideWeekendsOnly = true;

	/**
	 * @param days
	 *            The days collection containing all days.
	 */
	public DaysPrinter(Days days) {
		this.days = days;
	}

	/**
	 * @param holidays
	 *            All given days
	 * @param hideWeekdays
	 *            In the output, the several weekdays (Mon, Tue, Wed, Thu, Fri)
	 *            are omitted.
	 * @param hideWeekendsOnly
	 *            In the output, this weekdays are omitted whose are independent
	 *            of holiday partitions. That means, this days which are not
	 *            part of a holiday partition and which would be appear alone.
	 */
	public DaysPrinter(Days holidays, boolean hideWeekdays,
			boolean hideWeekendsOnly) {
		this.days = holidays;
		this.hideWeekdays = hideWeekdays;
		this.hideWeekendsOnly = hideWeekendsOnly;
	}

	/**
	 * This methods prints the holidays which are located within the given
	 * interval.
	 */
	public void print(Interval interval) {
		DateTime from = interval.getStart();
		DateTime to = interval.getEnd();
		setModifier();
		printInformation(from, to);
		Duration duration = new Duration(from, to);
		int diffDays = (int) duration.getStandardDays();
		StringBuilder format = new StringBuilder();
		String[] columns;
		printTableHeader(format);

		DateTime tmpDT = from;
		columns = null;
		for (int dayIndex = 0, arrIndex = 0; dayIndex <= diffDays; dayIndex++, arrIndex++) {
			if (columns == null) {
				columns = new String[32];
				columns[arrIndex] = DateTimeFormat.forPattern("MMM YY ").print(
						tmpDT);
				arrIndex = arrIndex + 1;
			}
			columns[tmpDT.getDayOfMonth()] = DateTimeFormat.forPattern("E")
					.print(tmpDT);

			applyModifier(columns, tmpDT);

			if (tmpDT.getMonthOfYear() - tmpDT.plusDays(1).getMonthOfYear() != 0
					|| dayIndex == diffDays) {
				arrIndex++;
				for (int i = 1; i < columns.length; i++) {
					if (columns[i] == null
							&& (i < from.getDayOfMonth() || i > to
									.getDayOfMonth())
							&& (tmpDT.getMonthOfYear() == to.getMonthOfYear() || tmpDT
									.getMonthOfYear() == from.getMonthOfYear())) {
						columns[i] = "";
					} else if (columns[i] == null) {
						columns[i] = "-";
					}
				}
				System.out.println(String.format(format.toString(),
						(Object[]) columns));
				columns = null;
				arrIndex = -1;
			}
			tmpDT = tmpDT.plusDays(1);
		}
	}

	private void applyModifier(String[] columns, DateTime tmpDT) {
		KindOfDay preDay = days.determineKindOf(tmpDT.minusDays(1));
		KindOfDay postDay = days.determineKindOf(tmpDT.plusDays(1));
		KindOfDay currentDay = days.determineKindOf(tmpDT);

		for (DayAppearanceModifier modifier : dayAppearanceModifierList) {
			columns[tmpDT.getDayOfMonth()] = modifier.returnAppearance(preDay,
					currentDay, postDay, columns[tmpDT.getDayOfMonth()]);
		}
	}

	private void printTableHeader(StringBuilder format) {
		format.append("%-10s");
		String[] columns = new String[32];
		columns[0] = "month";
		for (int i = 1; i <= 31; i++) {
			format.append("%-" + columnWidth + "s");
			columns[i] = String.valueOf(i);
		}
		String titleBar = String.format(format.toString(), (Object[]) columns);
		System.out.println(titleBar);
	}

	private void printInformation(DateTime from, DateTime to) {
		System.out.println("from:\t"
				+ DateTimeFormat.forPattern("dd. MMM yyyy").print(from));
		System.out.println("to:\t"
				+ DateTimeFormat.forPattern("dd. MMM yyyy").print(to));
		System.out.println();
	}

	/**
	 * A DayAppearanceModifier is a modifier, which modifies the appearance of
	 * the day which is printed out within a cell from the table. There can be
	 * come modifier: * # ] [
	 */
	private static interface DayAppearanceModifier {
		/**
		 * Returns the modified string.
		 * 
		 * @param before
		 *            Is the day before the day which becomes modified
		 * @param current
		 *            Is the day which becomes modified
		 * @param next
		 *            Id the day after the day which becomes modified
		 * @param dayAsString
		 *            Is the day which becomes modified as string
		 * @return The modified string
		 */
		String returnAppearance(WKDateTime.KindOfDay before,
				WKDateTime.KindOfDay current, WKDateTime.KindOfDay next,
				String dayAsString);
	}

	/**
	 * This method adds these modifiers which should be apply.
	 */
	private void setModifier() {
		if (hideWeekdays) {
			dayAppearanceModifierList.add(new DayAppearanceModifier() {
				@Override
				public String returnAppearance(KindOfDay before,
						KindOfDay current, KindOfDay next, String dayAsString) {
					if (current == KindOfDay.WEEK) {
						return "";
					}
					return dayAsString;
				}

			});
		}
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(WKDateTime.KindOfDay before,
					WKDateTime.KindOfDay current, WKDateTime.KindOfDay next,
					String dayAsString) {

				if (current == KindOfDay.HOLIDAY) {
					dayAsString = dayAsString + "*";
				}

				if (current == KindOfDay.VACATIONDAY) {
					dayAsString = dayAsString + "#";
				}
				return dayAsString;
			}
		});
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(WKDateTime.KindOfDay before,
					WKDateTime.KindOfDay current, WKDateTime.KindOfDay next,
					String dayAsString) {

				String prefix = "";
				String postfix = "";
				if (current != KindOfDay.WEEK) {
					boolean isPre = before == KindOfDay.WEEK;
					boolean isPost = next == KindOfDay.WEEK;

					if (isPre && isPost) {
						prefix = "[";
						postfix = "]";
					} else if (isPre) {
						prefix = "[";
						postfix = "";
					} else if (isPost) {
						prefix = "";
						postfix = "]";
					}
				}
				return prefix + dayAsString + postfix;
			}
		});
		if (hideWeekendsOnly) {
			dayAppearanceModifierList.add(new DayAppearanceModifier() {
				@Override
				public String returnAppearance(KindOfDay before,
						KindOfDay current, KindOfDay next, String dayAsString) {
					if (current == KindOfDay.WEEKEND) {
						int dayOfWeek = current.getDateTime().getDayOfWeek();

						KindOfDay kindOfFr = dayOfWeek == 6 ? before : days
								.determineKindOf(current.getDateTime()
										.minusDays(2));

						KindOfDay kindOfSa = dayOfWeek == 6 ? current : days
								.determineKindOf(current.getDateTime()
										.minusDays(1));

						KindOfDay kindOfSu = dayOfWeek == 7 ? current : days
								.determineKindOf(current.getDateTime()
										.plusDays(1));

						KindOfDay kindOfMo = dayOfWeek == 7 ? next : days
								.determineKindOf(kindOfSu.getDateTime()
										.plusDays(1));

						if (kindOfFr == KindOfDay.WEEK
								&& kindOfMo == KindOfDay.WEEK
								&& kindOfSa == KindOfDay.WEEKEND
								&& kindOfSu == KindOfDay.WEEKEND) {
							return "";
						}
					}
					return dayAsString;
				}
			});
		}
	}

	public DaysPrinter setHideWeekdays(boolean hideWeekdays) {
		this.hideWeekdays = hideWeekdays;
		return this;
	}

	public DaysPrinter setHideWeekendsOnly(boolean hideWeekendsOnly) {
		this.hideWeekendsOnly = hideWeekendsOnly;
		return this;
	}

}
