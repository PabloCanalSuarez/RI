package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "TCashes")
public class Cash extends PaymentMean {
	// Constraint one per client

	Cash() {
	}

	public Cash(Client client) {
		Argument.isNotNull(client);
		Associations.Pay.link(this, client);
	}

	@Override
	public String toString() {
		return "Cash [toString()=" + super.toString() + "]";
	}

}
