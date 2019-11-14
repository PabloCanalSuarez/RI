package uo.ri.cws.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "MECHANIC_ID",
		"COURSE_ID" }) }, name = "TEnrollments")
public class Enrollment extends BaseEntity {
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Course course;
	private int attendance;
	private boolean passed;

	Enrollment() {
	}

	public Enrollment(Mechanic m, Course c, int attendance, boolean passed) {
		checkEnoughToPass(attendance);
		this.passed = passed;
		this.attendance = attendance;

		Argument.isNotNull(m);
		Argument.isNotNull(c);

		Associations.Enroll.link(m, this, c);
	}

	private void checkEnoughToPass(int attendance) {
		if (attendance < 85) {
			throw new IllegalArgumentException(
					"The attendance must be over 85");
		}
	}

	public Course getCourse() {
		return course;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic m) {
		this.mechanic = m;
	}

	void _setCourse(Course c) {
		this.course = c;
	}

	public int getAttendance() {
		return attendance;
	}

	public boolean isPassed() {
		return passed;
	}

	public int getAttendedHoursFor(VehicleType vt) {
		int result = 0;

		Set<Dedication> dedications = course.getDedications();
		for (Dedication ded : dedications) {
			if (ded.getVehicleType().getName().equals(vt.getName())) {
				result += ded.getPercentage() * attendance / 100;
			}
		}

		return result;
	}

}
