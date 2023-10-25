package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest  {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        // given
        Member member = CreateMember("sujin");

        Item book = CreateBook("JPA", 10000, 10);

        int orderCount = 2;
        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals(OrderStatus.ORDER,getOrder.getStatus());
        Assert.assertEquals(1,getOrder.getOrderItems().size());
        Assert.assertEquals(10000*orderCount,getOrder.getTotalPrice());
        Assert.assertEquals(10-orderCount, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {

        // given
        Member member = CreateMember("sujin");
        Album album = CreateAlbum("김성규", 15000, 10);

        int orderCount = 12;
        // when
        orderService.order(member.getId(), album.getId(), orderCount);

        // then
        fail("에러에요");

    }

    @Test
    public void 주문취소() throws Exception {

        // given
        Member member = CreateMember("sujin");
        Item book = CreateBook("JPA",10000,10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(),orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals(getOrder.getStatus(),OrderStatus.CANCEL);
        Assert.assertEquals(10,book.getStockQuantity());

    }

    private Item CreateBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member CreateMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울","양재로","101010"));
        em.persist(member);
        return member;
    }
    private Album CreateAlbum(String name, int price, int stockQuantity) {
        Album album = new Album();
        album.setName(name);
        album.setPrice(price);
        album.setStockQuantity(stockQuantity);
        em.persist(album);
        return album;
    }

}
