package shared_backend.used_stuff.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Address {
	private String zipcode;
	private String address;
	private String addressDetail;

	public Address(String zipcode, String address, String addressDetail) {
		this.zipcode = zipcode;
		this.address = address;
		this.addressDetail = addressDetail;
	}

	public Address(Address address) {
		this.address = address.getAddress();
		this.zipcode = address.getZipcode();
		this.addressDetail = address.getAddressDetail();
	}
}
