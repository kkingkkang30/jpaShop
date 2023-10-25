package jpabook.jpashop.dummyData

import jpabook.jpashop.domain.Member

import java.util.function.Supplier

import static jpabook.jpashop.dummyData.ConstantValueDummy.MEMBER_NAME
import static jpabook.jpashop.dummyData.ConstantValueDummy.MEMBER_ID

class MemberDummy {

    static Supplier<Member> FINE_MEMBER ={
        def member = new Member()

        member.id = MEMBER_ID
        member.name = MEMBER_NAME

        return member
    }


}
