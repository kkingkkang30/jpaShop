package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false) //<- 커밋 하고 테스트 끝남
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memB");
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);

        // 같은 트랜잭션 내 . 같은 영속성 안에서 같은 id를 사용하면 같다. 영속성 컨텍스트
        System.out.println("findMember== member: " + (findMember==member));

    }
}