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

    private Long createItem(
            String title, String content,
            long price, long remaining, Long shopId
    ) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle(title);
        itemRequest.setContent(content);
        itemRequest.setPrice(price);
        itemRequest.setRemaining(remaining);
        return itemService.createItem(itemRequest, shopId);
    }

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
    @Transactional
    void editTitleByIdTest() {
        //given
        String title = "test2";
        String content = "test_content2";
        long price = 10000;
        long remaining = 500;
        Long shopId = 101L;
        Long itemId = createItem(title, content, price, remaining, shopId);

        //when
        String updatedTitle = "updated_test2";
        itemService.editTitleById(updatedTitle, itemId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(itemService.getItemById(itemId).getTitle())
                .isEqualTo(updatedTitle);
    }

    @Test
    @Transactional
    void editContentByIdTest() {
        //given
        String title = "test3";
        String content = "test_content3";
        long price = 10000;
        long remaining = 500;
        Long shopId = 102L;
        Long itemId = createItem(title, content, price, remaining, shopId);

        //when
        String updatedContent = "updated_content3";
        itemService.editContentById(updatedContent, itemId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(itemService.getItemById(itemId).getContent())
                .isEqualTo(updatedContent);
    }

    @Test
    @Transactional
    void editPriceByIdTest() {
        //given
        String title = "test4";
        String content = "test_content4";
        long price = 10000;
        long remaining = 500;
        Long shopId = 103L;
        Long itemId = createItem(title, content, price, remaining, shopId);

        //when
        long updatedPrice = 20000;
        itemService.editPriceById(updatedPrice, itemId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(itemService.getItemById(itemId).getPrice())
                .isEqualTo(updatedPrice);
    }

    @Test
    @Transactional
    void editRemainingByIdTest() {
        //given
        String title = "test5";
        String content = "test_content5";
        long price = 10000;
        long remaining = 500;
        Long shopId = 104L;
        Long itemId = createItem(title, content, price, remaining, shopId);

        //when
        long updatedRemaining = 1000;
        itemService.editRemainingById(updatedRemaining, itemId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(itemService.getItemById(itemId).getRemaining())
                .isEqualTo(updatedRemaining);
    }
}