package de.wk.date;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import de.wk.Log;
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
	public DaysPrinter(Days holidays, boolean hideWeekdays, boolean hideWeekendsOnly) {
		this.days = holidays;
		this.hideWeekdays = hideWeekdays;
		this.hideWeekendsOnly = hideWeekendsOnly;
	}

	/**
	 * This methods prints the holidays which are located within the given
	 * interval.
	 * 
	 * @throws Exception
	 */
	public void print(Interval interval) throws Exception {
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
				columns[arrIndex] = DateTimeFormat.forPattern("MMM YY ").print(tmpDT);
				arrIndex = arrIndex + 1;
			}
			columns[tmpDT.getDayOfMonth()] = DateTimeFormat.forPattern("E").print(tmpDT);

			applyModifier(columns, tmpDT);

			if (tmpDT.getMonthOfYear() - tmpDT.plusDays(1).getMonthOfYear() != 0 || dayIndex == diffDays) {
				arrIndex++;
				for (int i = 1; i < columns.length; i++) {
					if (columns[i] == null && (i < from.getDayOfMonth() || i > to.getDayOfMonth())
							&& (tmpDT.getMonthOfYear() == to.getMonthOfYear()
									|| tmpDT.getMonthOfYear() == from.getMonthOfYear())) {
						columns[i] = "";
					} else if (columns[i] == null) {
						columns[i] = "-";
					}
				}
				Log.out(String.format(format.toString(), (Object[]) columns));
				columns = null;
				arrIndex = -1;
			}
			tmpDT = tmpDT.plusDays(1);
		}
		printLegend();
	}

	private void printLegend() {
		Log.out("\n>Legend:");
		Log.out(String.format("%-4s%-20s", "[*]", "Holiday"));
		Log.out(String.format("%-4s%-20s", "[#]", "Vacation day"));
		Log.out(String.format("%-4s%-20s", "[ ]", "non-holiday/-vacation day"));
	}

	private void applyModifier(String[] columns, DateTime tmpDT) throws Exception {
		KindOfDay preDay = days.determineKindOf(tmpDT.minusDays(1));
		KindOfDay postDay = days.determineKindOf(tmpDT.plusDays(1));
		KindOfDay currentDay = days.determineKindOf(tmpDT);

		for (DayAppearanceModifier modifier : dayAppearanceModifierList) {
			columns[tmpDT.getDayOfMonth()] = modifier.returnAppearance(tmpDT, preDay, currentDay, postDay,
					columns[tmpDT.getDayOfMonth()]);
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
		Log.out(titleBar);
	}

	private void printInformation(DateTime from, DateTime to) {
		Log.out("from:\t" + DateTimeFormat.forPattern("dd. MMM yyyy").print(from));
		Log.out("to:\t" + DateTimeFormat.forPattern("dd. MMM yyyy").print(to));
		Log.newLine();
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
		 * @param tmpDT
		 *            the view being under consideration
		 * @param before
		 *            Is the day before the day which becomes modified
		 * @param current
		 *            Is the day which becomes modified
		 * @param next
		 *            Id the day after the day which becomes modified
		 * @param dayAsString
		 *            Is the day which becomes modified as string
		 * @return The modified string
		 * @throws Exception
		 */
		String returnAppearance(DateTime tmpDT, WKDateTime.KindOfDay before, WKDateTime.KindOfDay current,
				WKDateTime.KindOfDay next, String dayAsString) throws Exception;
	}

	/**
	 * This method adds those modifiers which should be apply.
	 */
	private void setModifier() {
		if (hideWeekdays) {
			dayAppearanceModifierList.add(new DayAppearanceModifier() {
				@Override
				public String returnAppearance(DateTime tmpDT, KindOfDay before, KindOfDay current, KindOfDay next,
						String dayAsString) {
					if (current == KindOfDay.WORKDAY) {
						return "";
					}
					return dayAsString;
				}

			});
		}
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(DateTime tmpDT, KindOfDay before, KindOfDay current, KindOfDay next,
					String dayAsString) {

				if (current == KindOfDay.HOLIDAY) {
					dayAsString = dayAsString + "*";
				}

				if (current == KindOfDay.WORKDAY) {
					dayAsString = dayAsString + "";
				}
				if (dayAsString.length() == 1) {
					return "";
				} else {
					return dayAsString;
				}
			}
		});
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(DateTime tmpDT, KindOfDay before, KindOfDay current, KindOfDay after,
					String dayAsString) {

				String prefix = "";
				String postfix = "";
				if (current != KindOfDay.WORKDAY) {
					boolean isPre = before == KindOfDay.WORKDAY;
					boolean isPost = after == KindOfDay.WORKDAY;

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
				public String returnAppearance(DateTime tmpDT, KindOfDay before, KindOfDay current, KindOfDay next,
						String dayAsString) throws Exception {
					if (current == KindOfDay.WEEKEND) {
						int dayOfWeek = tmpDT.getDayOfWeek();

						KindOfDay kindOfFr = dayOfWeek == 6 ? before : days.determineKindOf(tmpDT.minusDays(2));

						KindOfDay kindOfSa = dayOfWeek == 6 ? current : days.determineKindOf(tmpDT.minusDays(1));

						KindOfDay kindOfSu = dayOfWeek == 7 ? current : days.determineKindOf(tmpDT.plusDays(1));

						KindOfDay kindOfMo = dayOfWeek == 7 ? next : days.determineKindOf(tmpDT.plusDays(2));

						if (kindOfFr == KindOfDay.WORKDAY && kindOfMo == KindOfDay.WORKDAY
								&& kindOfSa == KindOfDay.WEEKEND && kindOfSu == KindOfDay.WEEKEND) {
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
