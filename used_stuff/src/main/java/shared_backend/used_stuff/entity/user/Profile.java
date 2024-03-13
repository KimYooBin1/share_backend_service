package shared_backend.used_stuff.entity.user;

import static jakarta.persistence.EnumType.*;
import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shared_backend.used_stuff.base.BaseTimeEntity;
import shared_backend.used_stuff.dto.UpdateUserRequest;
import shared_backend.used_stuff.entity.Address;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Profile extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String name;
	private int age;
	@Enumerated(STRING)
	private Gender gender;
	@Embedded
	private Address address;

	public Profile(String name, int age, Gender gender, Address address) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = new Address(address);
	}

	public void updateProfile(UpdateUserRequest request) {
		if(request.getName() != null) this.name = request.getName();
		if(request.getGender() != null) this.gender = request.getGender();
		if(request.getAge() != 0) this.age = request.getAge();
		if(request.getAddress() != null) this.address = new Address(request.getAddress());
	}
}
