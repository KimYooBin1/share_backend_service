package shared_backend.used_stuff.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.repository.ShopBoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopBoardService {
	private final ShopBoardRepository shopBoardRepository;

	
}
