package may_all_here.shopservice.service;

import lombok.RequiredArgsConstructor;
import may_all_here.shopservice.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
}
