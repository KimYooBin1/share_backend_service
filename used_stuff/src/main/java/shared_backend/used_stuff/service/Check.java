package shared_backend.used_stuff.service;

import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.Objects;

import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.exception.AlreadyDeletedException;
import shared_backend.used_stuff.exception.NotEqualPassword;

public class Check {
	public void checkPW(String Pw, String C_Pw){
		if (!Objects.equals(Pw, C_Pw)) {
			throw new NotEqualPassword("password not equal");
		}
	}

	public void checkState(Status status) {
		if (status == delete) {
			throw new AlreadyDeletedException("cannot any action an already deleted post");
		}
	}
}
