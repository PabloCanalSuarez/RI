package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCourses")
public class Course extends BaseEntity {

	@Column(unique = true)
	private String code;

	private String name;
	private String description;

	private Date startDate;
	private Date endDate;
	private int hours;

	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments = new HashSet<>();

	@OneToMany(mappedBy = "course")
	private Set<Dedication> dedications = new HashSet<>();

	Course() {
	}

	public Course(String code) {
		Argument.isNotEmpty(code);

		this.code = code;
	}

	public Course(String code, String name, String description) {
		this(code);
		checkStrings(name, description);

		this.name = name;
		this.description = description;
	}

	public Course(String code, String name, String description, Date startDate,
			Date endDate) {
		this(code, name, description);
		checkDates(startDate, endDate);

		this.startDate = new Date(startDate.getTime());
		this.endDate = new Date(endDate.getTime());
	}

	public Course(String code, String name, String description, Date startDate,
			Date endDate, int hours) {
		this(code, name, description, startDate, endDate);

		Argument.isTrue(hours > 0);
		Argument.isTrue(endDate.getTime() > startDate.getTime());
		this.hours = hours;
	}

	/**
	 * Checks that all the strings passed are not empty
	 */
	private void checkStrings(String name, String description) {
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(description);
	}

	/**
	 * Checks that all the dates passed as parameters are not null
	 */
	private void checkDates(Date datStart, Date datEnd) {
		Argument.isNotNull(datStart);
		Argument.isNotNull(datEnd);
	}

	public Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}

	/* package */ Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

	public Set<Dedication> getDedications() {
		return new HashSet<>(dedications);
	}

	/* package */ Set<Dedication> _getDedications() {
		return dedications;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return new Date(startDate.getTime());
	}

	public Date getEndDate() {
		return new Date(endDate.getTime());
	}

	public int getHours() {
		return hours;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", description="
				+ description + ", startDate=" + startDate + ", endDate="
				+ endDate + ", hours=" + hours + ", enrollments=" + enrollments
				+ ", dedications=" + dedications + "]";
	}

	public void addDedications(Map<VehicleType, Integer> percentages) {
		checkDedicationsIsEmpty();
		checkPercentages(percentages);

		for (Map.Entry<VehicleType, Integer> entry : percentages.entrySet()) {
			new Dedication(this, entry.getKey(), entry.getValue());
		}
	}

	private void checkDedicationsIsEmpty() {
		if (!dedications.isEmpty()) {
			throw new IllegalStateException(
					"This course has dedications already");
		}
	}

	private void checkPercentages(Map<VehicleType, Integer> percentages) {
		int counterPercentage = 0;
		for (Map.Entry<VehicleType, Integer> entry : percentages.entrySet()) {
			counterPercentage += entry.getValue();
		}

		if (counterPercentage < 100 || counterPercentage > 100) {
			throw new IllegalArgumentException(
					"Percentage must be 100 to create the dedication");
		}
	}

	/**
	 * clearDedications() unlinks dedications
	 */
	public void clearDedications() {
		Set<Dedication> ded = getDedications();
		for (Dedication d : ded) {
			Associations.Dedicate.unlink(d);
		}
	}

}
