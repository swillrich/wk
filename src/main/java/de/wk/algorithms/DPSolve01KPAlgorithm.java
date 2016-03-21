package de.wk.algorithms;

import de.wk.date.WKDateTime;
import de.wk.date.holiday.Holiday;
import de.wk.date.holiday.VacationDay;
import de.wk.user.User;

public class DPSolve01KPAlgorithm implements HolidayCalculatorAlgorithm {

	private boolean verbose = true;
	private Gaps gaps = null;

	@Override
	public void calculate(User user) throws Exception {
		gaps = new Gaps(user.getDays(), false);

		// solve the problem as 0-1 knapsack problem by means of Dynamic
		// Programming approach
		boolean[] take = solveByDynamicProgrammingAsKnapsackProblem(user);

		replaceDays(user, take);
	}

	private void replaceDays(User user, boolean[] take) throws Exception {
		for (int i = 0; i < gaps.size(); i++) {
			if (take[i]) {
				Gap gap = gaps.get(i);
				for (WKDateTime wkDateTime : gap.getDays()) {
					if (!(wkDateTime instanceof Holiday)) {
						VacationDay vacationDay = new VacationDay("calculated vacation day", wkDateTime.getDateTime());
						user.getDays().replaceDay(vacationDay);
					}
				}
			}
		}
	}

	private boolean[] solveByDynamicProgrammingAsKnapsackProblem(User user) {
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

		return take;
	}

}
