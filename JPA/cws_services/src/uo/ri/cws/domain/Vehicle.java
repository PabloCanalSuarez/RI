package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TVehicles")
public class Vehicle extends BaseEntity {
	// NATURAL ATRIBUTES
	@Column(unique = true)
	private String plateNumber;
	private String make;
	private String model;

	// NOT
	@ManyToOne
	private Client client;
	@ManyToOne
	private VehicleType vehicleType;
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<>();

	Vehicle() {
	}

	public Vehicle(String plateNumber) {
		Argument.isNotEmpty(plateNumber);
		this.plateNumber = plateNumber;
	}

	public Vehicle(String plateNumber, String make, String model) {
		this(plateNumber);

		Argument.isNotEmpty(make);
		Argument.isNotEmpty(model);

		this.make = make;
		this.model = model;
	}

	// TO DO THE LINK
	public Client getClient() {
		return client;
	}

	/* package */ void _setClient(Client client) {
		this.client = client;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/* package */ void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	// need 2 getters
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	/* package */ Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((plateNumber == null) ? 0 : plateNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

}
