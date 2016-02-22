package de.wk.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.WKInterval;
import de.wk.user.User;

public class StillNamelessAlgorithm implements HolidayCalculatorAlgorithm {

	private List<Gap> gaps = new ArrayList<Gap>();
	private List<WKInterval> partition = new ArrayList<WKInterval>();
	int[] value;
	int[] weight;
	private int numberOfItems = 0;
	private int maxWeight = 0;

	@Override
	public void calculate(User user) throws Exception {
		// calculate all intervals and gaps available within the given scope
		initializeIntervals(user.getDays());
		// calculate the value of every gap within the scope
		calculateValue();
		
		this.numberOfItems = gaps.size();
		this.maxWeight = user.getNumberOfHolidays();

		value = new int[numberOfItems + 1];
		weight = new int[numberOfItems + 1];

		// initialize value and weight list
		for (int i = 0; i < numberOfItems; i++) {
			this.value[i] = gaps.get(i).getValue();
			this.weight[i] = gaps.get(i).getSize();
		}

		// max profit of packing items 1..numberOfItems with weight limit
		// maxWeight
		int[][] optimum = new int[numberOfItems + 1][maxWeight + 1];
		
		// does opt solution to pack items 1..numberOfItems with weight limit
		// maxWeight include item numberOfItems?
		boolean[][] isIncluded = new boolean[numberOfItems + 1][maxWeight + 1];

		for (int n = 1; n <= numberOfItems; n++) {
			for (int w = 1; w <= maxWeight; w++) {

				// don't take current item n
				int option1 = optimum[n - 1][w];

				// take current item n
				int option2 = Integer.MIN_VALUE;
				if (weight[n] <= w)
					option2 = value[n] + optimum[n - 1][w - weight[n]];

				// select better of two options
				optimum[n][w] = Math.max(option1, option2);
				isIncluded[n][w] = (option2 > option1);
			}
		}

		// determine which items to take
		boolean[] take = new boolean[numberOfItems + 1];
		for (int n = numberOfItems, w = maxWeight; n > 0; n--) {
			if (isIncluded[n][w]) {
				take[n] = true;
				w = w - weight[n];
				user.getRemainingHolidays().decrementBy(weight[n]);
			} else {
				take[n] = false;
			}
		}

		// print results
		System.out.println("Gap" + "\t" + "Value" + "\t" + "Weight" + "\t" + "Take?");
		for (int n = 1; n <= numberOfItems; n++) {
			System.out.println(n + "\t" + value[n] + "\t" + weight[n] + "\t" + take[n]);
		}

		// System.out.println(user.getDays().toString());

		// FIXME: Normal weekends and weekdays are not overwritten by holidays
		// FIXME: Result of getSize of an interval/gap does not correspond to
		// 	actual length of the interval/gap
		// See following output for the sake of clarity.
		
		// for (Gap gap : this.gaps) {
		// System.out.println(gap.getPrevInterval().getDaysBetween().toString()
		// + "(" + gap.getPrevInterval().getSize() +")");
		// System.out.println(gap.getDaysBetween().toString() + "(" +
		// gap.getSize() + ")");
		// System.out.println(gap.getNextInterval().getDaysBetween().toString()
		// + "(" + gap.getNextInterval().getSize() +")");
		// System.out.println("####################");
		// }
	}

	private void initializeIntervals(Days days) throws Exception {
		WKDateTime start = null;
		boolean isWorkDay = false;
		for (int i = 0; i < days.size(); i++) {
			WKDateTime dateTime = days.get(i);
			isWorkDay = i + 1 < days.size() ? days.determineKindOf(days.get(i + 1).getDateTime()) == KindOfDay.WORKDAY
					: !isWorkDay;
			if (start == null) {
				start = dateTime;
			}
			if (isWorkDay != (days.determineKindOf(start.getDateTime()) == KindOfDay.WORKDAY)) {
				dateTime = days.get(i);
				if (!isWorkDay) {
					Gap gap = new Gap(start, dateTime);
					gaps.add(gap);
					if (partition.size() > 0) {
						gap.setPrevInterval(partition.get(partition.size() - 1));
					}
				} else {
					WKInterval wkInterval = new WKInterval(start, dateTime);
					if (gaps.size() > 0) {
						gaps.get(gaps.size() - 1).setNextInterval(wkInterval);
					}
					partition.add(wkInterval);
				}
				start = null;
			}
		}

	}

	private void calculateValue() {
		int newValue;
		for (Gap gap : this.gaps) {
			newValue = gap.getSize() + gap.getPrevInterval().getSize() + gap.getNextInterval().getSize();
			gap.setValue(newValue);
		}
	}

}
