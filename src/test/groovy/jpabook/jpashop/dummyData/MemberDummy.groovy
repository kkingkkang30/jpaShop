package jpabook.jpashop.dummyData

import jpabook.jpashop.domain.Member

import static jpabook.jpashop.dummyData.ConstantValueDummy.MEMBER_NAME

class MemberDummy {

    static Member FINE_MEMBER(memberId) {
        def member = new Member()

        member.id = memberId
        member.name = MEMBER_NAME

        return member
    }

}
