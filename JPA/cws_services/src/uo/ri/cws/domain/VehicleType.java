package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVehicleTypes")
public class VehicleType extends BaseEntity {
	@Column(unique = true)
	private String name;
	private double pricePerHour;
	private int minTrainingHours;

	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<>();

	@OneToMany(mappedBy = "vehicleType")
	private Set<Certificate> certificates = new HashSet<>();

	@OneToMany(mappedBy = "vehicleType")
	private Set<Dedication> dedications = new HashSet<>();

	VehicleType() {
	}

	public VehicleType(String name) {
		Argument.isNotEmpty(name);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		this(name);
		Argument.isNotNull(pricePerHour);
		this.pricePerHour = pricePerHour;
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	// need 2 gettters
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	/* package */ Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
				+ "]";
	}

	public int getMinTrainingHours() {
		return minTrainingHours;
	}

	public Set<Certificate> getCertificates() {
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	public Set<Dedication> getDedications() {
		return new HashSet<>(dedications);
	}

	Set<Dedication> _getDedications() {
		return dedications;
	}

}
