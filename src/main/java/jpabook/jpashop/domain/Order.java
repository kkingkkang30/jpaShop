package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // fk
    private Member member;
    /*
    * 양방향 연관관계 이기 때문에 주인을 정해야한다.
    * 주인은 그대로 두면 되고 주인ㅇ ㅏ닌 애한테 mappedBy 추가
    * */

    //  cascade = CascadeType.ALL => orderItem 따로 저장안하고 order 저장할때 orderItem 한번에 저장해줌.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; // order, cancle


    // 연관 관계 편의 메서드 //
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);


        // 메서드 하나로 양방향 setting..?
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
