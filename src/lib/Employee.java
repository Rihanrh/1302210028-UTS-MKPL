package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {
	public enum Gender {
		MALE,
		FEMALE
	}
	
	private String employeeId;
    private PersonalInfo personalInfo;

	private LocalDate joiningDate;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private Gender gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, PersonalInfo personalInfo, LocalDate joiningDate, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.personalInfo = personalInfo;
        this.joiningDate = joiningDate;
        this.isForeigner = isForeigner;
        this.gender = gender;

        childNames = new LinkedList<String>();
        childIdNumbers = new LinkedList<String>();
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	private int getSalary(int grade) {
		switch (grade) {
			case 1:
				return 3000000;
			case 2:
				return 5000000;
			case 3:
				return 7000000;
			default:
                throw new IllegalArgumentException("Grade is invalid");
		}
	}

	public void setMonthlySalary(int grade) {
		int monthlySalary = getSalary(grade);
		if (isForeigner) {
			monthlySalary = (int) (monthlySalary * 1.5);
		}
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = personalInfo.getIdNumber();
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		LocalDate currentDate = LocalDate.now();
		
		if (joiningDate.getYear() == currentDate.getYear()) {
			monthWorkingInYear = currentDate.getMonthValue() - joiningDate.getMonthValue();
		}else {
			monthWorkingInYear = 12;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
	}
}
