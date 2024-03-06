package shared_backend.used_stuff.service;

import java.util.Objects;

public class PasswordCheck {
	public void checkPW(String Pw, String C_Pw){
		if (!Objects.equals(Pw, C_Pw)) {
			throw new IllegalArgumentException("password not equal");
		}
	}
}
