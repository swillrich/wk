package de.wk.holiday;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

import de.wk.holiday.day.Holidays;
import de.wk.holiday.day.Holidays.KindOf;

public class HolidayPrinter {

	private Holidays holidays;
	private int columnWidth = 6;
	private List<DayAppearanceModifier> dayAppearanceModifierList = new ArrayList<DayAppearanceModifier>();
	private boolean hideWeekdays = true;
	private boolean hideWeekendsOnly = true;

	public HolidayPrinter(Holidays holidays) {
		this.holidays = holidays;
	}

	public HolidayPrinter(Holidays holidays, boolean hideWeekdays,
			boolean hideWeekendsOnly) {
		this.holidays = holidays;
		this.hideWeekdays = hideWeekdays;
		this.hideWeekendsOnly = hideWeekendsOnly;
	}

	public void print(DateTime from, DateTime to) {
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
		KindOf preDay = holidays.determineKindOf(tmpDT.minusDays(1));
		KindOf postDay = holidays.determineKindOf(tmpDT.plusDays(1));
		KindOf currentDay = holidays.determineKindOf(tmpDT);

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

	private static interface DayAppearanceModifier {
		String returnAppearance(Holidays.KindOf before,
				Holidays.KindOf current, Holidays.KindOf next,
				String dayAsString);
	}

	private void setModifier() {
		if (hideWeekdays) {
			dayAppearanceModifierList.add(new DayAppearanceModifier() {
				@Override
				public String returnAppearance(KindOf before, KindOf current,
						KindOf next, String dayAsString) {
					if (current == KindOf.WEEK) {
						return "";
					}
					return dayAsString;
				}

			});
		}
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(Holidays.KindOf before,
					Holidays.KindOf current, Holidays.KindOf next,
					String dayAsString) {

				if (current == KindOf.HOLIDAY) {
					dayAsString = dayAsString + "*";
				}

				if (current == KindOf.VACATIONDAY) {
					dayAsString = dayAsString + "#";
				}
				return dayAsString;
			}
		});
		dayAppearanceModifierList.add(new DayAppearanceModifier() {

			@Override
			public String returnAppearance(Holidays.KindOf before,
					Holidays.KindOf current, Holidays.KindOf next,
					String dayAsString) {

				String prefix = "";
				String postfix = "";
				if (current != KindOf.WEEK) {
					boolean isPre = before == KindOf.WEEK;
					boolean isPost = next == KindOf.WEEK;

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
				public String returnAppearance(KindOf before, KindOf current,
						KindOf next, String dayAsString) {
					if (current == KindOf.WEEKEND) {
						int dayOfWeek = current.getDateTime().getDayOfWeek();

						KindOf kindOfFr = dayOfWeek == 6 ? before : holidays
								.determineKindOf(current.getDateTime()
										.minusDays(2));

						KindOf kindOfSa = dayOfWeek == 6 ? current : holidays
								.determineKindOf(current.getDateTime()
										.minusDays(1));

						KindOf kindOfSu = dayOfWeek == 7 ? current : holidays
								.determineKindOf(current.getDateTime()
										.plusDays(1));

						KindOf kindOfMo = dayOfWeek == 7 ? next : holidays
								.determineKindOf(kindOfSu.getDateTime()
										.plusDays(1));

						if (kindOfFr == KindOf.WEEK && kindOfMo == KindOf.WEEK
								&& kindOfSa == KindOf.WEEKEND
								&& kindOfSu == KindOf.WEEKEND) {
							return "";
						}
					}
					return dayAsString;
				}
			});
		}
	}

	public HolidayPrinter setHideWeekdays(boolean hideWeekdays) {
		this.hideWeekdays = hideWeekdays;
		return this;
	}

	public HolidayPrinter setHideWeekendsOnly(boolean hideWeekendsOnly) {
		this.hideWeekendsOnly = hideWeekendsOnly;
		return this;
	}

}
