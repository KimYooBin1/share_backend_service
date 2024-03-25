package shared_backend.used_stuff.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.oauth2.CustomOAuth2User;
import shared_backend.used_stuff.dto.oauth2.GoogleResponse;
import shared_backend.used_stuff.dto.oauth2.NaverResponse;
import shared_backend.used_stuff.dto.oauth2.OAuth2Response;
import shared_backend.used_stuff.dto.oauth2.UserDTO;
import shared_backend.used_stuff.entity.user.OAuth2UserEntity;
import shared_backend.used_stuff.repository.OAuth2UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final OAuth2UserRepository oAuth2UserRepository;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("oAuth2User = {}", oAuth2User);

		//google 에서 온 요청인지 naver 에서 온 요청인지 판별
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;
		if (registrationId.equals("naver")) {
			oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
		} else if (registrationId.equals("google")) {
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
		} else {
			return null;
		}
		String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
		OAuth2UserEntity existData = oAuth2UserRepository.findByUsername(username);
		if(existData == null){
			// TODO : 생성자 사용해서 넣기
			OAuth2UserEntity userEntity = new OAuth2UserEntity(username, oAuth2Response.getName(), oAuth2Response.getEmail(),
				"ROLE_USER");

			oAuth2UserRepository.save(userEntity);

			UserDTO userDto = new UserDTO();
			userDto.setUsername(username);
			userDto.setName(oAuth2Response.getName());
			userDto.setRole("ROLE_USER");

			return new CustomOAuth2User(userDto);
		}
		else{
			// TODO : setter 사용하지 않기
			existData.setEmail(oAuth2Response.getEmail());
			existData.setName(oAuth2Response.getName());

			UserDTO userDto = new UserDTO();
			// TODO : email 추가?
			userDto.setUsername(existData.getUsername());
			userDto.setName(oAuth2Response.getName());
			userDto.setRole(existData.getRole());

			return new CustomOAuth2User(userDto);
		}
	}
}
