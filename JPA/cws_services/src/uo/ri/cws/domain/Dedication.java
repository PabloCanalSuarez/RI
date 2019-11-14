package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "VEHICLETYPE_ID",
		"COURSE_ID" }) }, name = "TDedications")
public class Dedication extends BaseEntity {

	private int percentage;

	@ManyToOne
	private Course course;

	@ManyToOne
	private VehicleType vehicleType;

	Dedication() {
	}

	Dedication(Course c, VehicleType vt, int percentage) {
		Argument.isNotNull(percentage);
		Argument.isNotNull(c);
		Argument.isNotNull(vt);

		this.percentage = percentage;

		Associations.Dedicate.link(vt, this, c);
	}

	public int getPercentage() {
		return percentage;
	}

	public Course getCourse() {
		return course;
	}

	/* package */ void _setCourse(Course course) {
		this.course = course;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/* package */ void _setVehicleType(VehicleType vt) {
		this.vehicleType = vt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result
				+ ((vehicleType == null) ? 0 : vehicleType.hashCode());
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
		Dedication other = (Dedication) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (vehicleType == null) {
			if (other.vehicleType != null)
				return false;
		} else if (!vehicleType.equals(other.vehicleType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dedication [percentage=" + percentage + ", course=" + course
				+ ", vehicleTypes=" + vehicleType + "]";
	}

}
