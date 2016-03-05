package de.wk.algorithms;

import java.util.ArrayList;
import java.util.List;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.WKDateTime.KindOfDay;
import de.wk.date.WKInterval;
import de.wk.user.User;

public class DPSolve01KPAlgorithm implements HolidayCalculatorAlgorithm {

	private List<Gap> gaps = new ArrayList<Gap>();
	private List<WKInterval> partition = new ArrayList<WKInterval>();
	public boolean verbose = true;

	@Override
	public void calculate(User user) throws Exception {
		// calculate all intervals and gaps available within the given scope
		initializeIntervals(user.getDays());
		// calculate the value of every gap within the scope
		calculateValue();

		// solve the problem as 0-1 knapsack problem by means of Dynamic
		// Programming approach
		solveByDynamicProgrammingAsKnapsackProblem(user);
	}

	private void solveByDynamicProgrammingAsKnapsackProblem(User user) {
		int[] value;
		int[] weight;
		int numberOfItems = 0;
		int maxWeight = 0;

		numberOfItems = gaps.size();
		maxWeight = user.getNumberOfHolidays();

		value = new int[numberOfItems + 1];
		weight = new int[numberOfItems + 1];

		// initialize value and weight list
		for (int i = 0; i < numberOfItems; i++) {
			value[i] = gaps.get(i).getValue();
			weight[i] = gaps.get(i).getSize();
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
		if (verbose) {
			System.out.println("Gap" + "\t" + "Value" + "\t" + "Weight" + "\t" + "Take?");
			for (int n = 1; n <= numberOfItems; n++) {
				System.out.println(n + "\t" + value[n] + "\t" + weight[n] + "\t" + take[n]);
			}
		}
	}

	/**
	 * initializes gaps and non-gaps (the letter one means partitions being
	 * filled by days being non-holidays, especially working days).
	 */
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
					if (partition.size() > 0) {
						gap.setPrevInterval(partition.get(partition.size() - 1));
					}
					gaps.add(gap);
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

		if (verbose) {
			printGaps();
		}
	}

	/**
	 * Prints out all gaps including all preceding and proceeding intervals
	 * being non-gaps.
	 */
	private void printGaps() {
		for (int i = 0; i < this.gaps.size(); i++) {
			Gap gap = this.gaps.get(i);
			List<WKInterval> l = new ArrayList<WKInterval>();
			if (gap.getPrevInterval() != null) {
				l.add(gap.getPrevInterval());
			}
			l.add(gap);
			if (gap.getNextInterval() != null && i + 1 == this.gaps.size()) {
				l.add(gap.getNextInterval());
			}
			for (WKInterval wki : l) {
				System.out.println(wki.toString());
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
