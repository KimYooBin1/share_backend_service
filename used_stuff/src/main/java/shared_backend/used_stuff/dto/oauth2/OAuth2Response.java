package shared_backend.used_stuff.dto.oauth2;

public interface OAuth2Response {
	String getProvider();
	String getProviderId();
	String getEmail();
	String getName();
}
