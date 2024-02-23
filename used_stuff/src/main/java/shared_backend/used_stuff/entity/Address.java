package shared_backend.used_stuff.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	private String zipcode;
	private String address;
	private String addressDetail;
}
