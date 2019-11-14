package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TClients")
public class Client extends BaseEntity {
	// NATURAL ATRIBUTES -> the ones in the toString
	@Column(unique = true)
	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	// NOT NATURAL
	// is in the one side relation

	@OneToMany(mappedBy = "client")
	private Set<Vehicle> vehicles = new HashSet<>();// ALWAYS INIT IN THE SAME
													// LINE
	@OneToMany(mappedBy = "client")
	private Set<PaymentMean> paymentMeans = new HashSet<>();

	Client() {
	}

	public Client(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Client(String dni, String name, String surname) {
		this(dni);

		Argument.isNotEmpty(name);
		Argument.isNotEmpty(surname);

		this.name = name;
		this.surname = surname;
	}

	// need 2 getters
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	/* package */ Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	/* package */ Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	public void setAddress(Address address2) {
		this.address = address2;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
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
		Client other = (Client) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}
}
