package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TMechanics")
public class Mechanic extends BaseEntity {
	@Column(unique = true)
	private String dni;
	private String surname;
	private String name;

	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Certificate> certificates = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Enrollment> enrollments = new HashSet<>();

	Mechanic() {
	}

	public Mechanic(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Mechanic(String dni, String name, String surname) {
		this(dni);

		Argument.isNotEmpty(name);
		Argument.isNotEmpty(surname);

		this.name = name;
		this.surname = surname;

	}

	/* package */ Set<WorkOrder> _getAssigned() {
		return workOrders;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(workOrders);
	}

	public String getDni() {
		return dni;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	/* package */ Set<Intervention> _getInterventions() {
		return interventions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
				+ name + "]";
	}

	public void setName(String name2) {
		this.name = name2;
	}

	public void setSurname(String surname2) {
		this.surname = surname2;
	}

	public Set<Certificate> getCertificates() {
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	public Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}

	Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

	public boolean isCertifiedFor(VehicleType vt) {
		if (vt == null) {
			return false;
		}

		Set<Certificate> certificateAux = getCertificates();
		for (Certificate cert : certificateAux) {
			if (cert.getVehicleType().getName().equals(vt.getName())) {
				return true;
			}
		}
		return false;
	}

	public Set<Enrollment> getEnrollmentsFor(VehicleType vt) {
		Set<Enrollment> result = new HashSet<>();
		if (vt == null) {
			return result;
		}

		Set<Enrollment> enrollAux = getEnrollments();

		for (Enrollment enroll : enrollAux) {
			Set<Dedication> dedications = enroll.getCourse().getDedications();
			for (Dedication ded : dedications) {
				if (ded.getVehicleType().getName().equals(vt.getName())) {
					result.add(enroll);
				}
			}
		}

		return result;
	}

}
