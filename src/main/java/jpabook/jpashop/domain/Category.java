package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name= "category_id"), // 중간 테이블에 있는 아이디 말하는 것
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블에 item 쪽으로 들어가는 것
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // 부모 카테고리는 하나
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 자식 카테고리는 많을 수 있음
    private List<Category>  child = new ArrayList<>();

    /*연관관계 method*/
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
