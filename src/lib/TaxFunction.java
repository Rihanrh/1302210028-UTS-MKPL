package lib;

public class TaxFunction {

	private static final int BASE_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_NON_TAXABLE_INCOME_ADDITION = 4500000;
	private static final int CHILD_NON_TAXABLE_INCOME_ADDITION = 1500000;
	private static final double TAX_RATE = 0.05;

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
		int tax = 0;

		validateInput(numberOfMonthWorking, numberOfChildren);
		int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
		int annualIncome = calculateAnnualIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking);
		int taxableIncome = annualIncome - deductible - nonTaxableIncome;

		tax = (int) Math.round(TAX_RATE * taxableIncome);
		
		if (tax < 0) {
			return 0;
		}else {
			return tax;
		}
	}

	private static void validateInput(int numberOfMonthWorking, int numberOfChildren) {
        if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}
		
		if (numberOfChildren > 3) {
			numberOfChildren = 3;
		}
    }

	private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int nonTaxableIncome = BASE_NON_TAXABLE_INCOME;
        if (isMarried) {
            nonTaxableIncome += MARRIED_NON_TAXABLE_INCOME_ADDITION;
        }
        nonTaxableIncome += numberOfChildren * CHILD_NON_TAXABLE_INCOME_ADDITION;
        return nonTaxableIncome;
    }

	private static int calculateAnnualIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking) {
        return (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
    }
}
