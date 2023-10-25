package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{

        // given
                                   Member member = new Member();
        member.setName("kim");

        // when
        Long memberId = memberService.join(member);

        // then
        em.flush();  ///  DB 에 쿼리 날리는걸 보고싶을때 / 혹은 Rollback(false) 로 하면 됨
        assertEquals(member, memberRepository.findOne(memberId));
    }


    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{

        //given

        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외");
    }

}