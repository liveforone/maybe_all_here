package maybe_all_here.itemservice.service;

import jakarta.persistence.EntityManager;
import maybe_all_here.itemservice.dto.item.ItemRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemService itemService;

    @Test
    @Transactional
    void createItemTest() {
        //given
        String title = "test1";
        String content = "test_content1";
        long price = 10000;
        long remaining = 500;
        Long shopId = 100L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle(title);
        itemRequest.setContent(content);
        itemRequest.setPrice(price);
        itemRequest.setRemaining(remaining);

        //when
        Long itemId = itemService.createItem(itemRequest, shopId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(itemService.getItemById(itemId).getTitle())
                .isEqualTo(title);
    }

    @Test
    void editTitleById() {
    }

    @Test
    void editContentById() {
    }

    @Test
    void editPriceById() {
    }

    @Test
    void editRemainingById() {
    }

    @Test
    void deleteItemById() {
    }
}