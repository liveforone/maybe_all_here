package maybe_all_here.itemservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
}
