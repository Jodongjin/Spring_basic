package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {


    @Test
    void createOrder() {
        /**
         * 객체가 setter DI를 사용중일 경우
         * 테스트할 때 필요한 setter DI를 빼먹는 경우가 생김
         * 생성자 DI의 경우, 생성자 파라미터가 비었을 경우, 컴파일 에러를 띄워주기 때문에 이런 실수 줄임
         */
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);

        /**
         * 생성자 DI를 자바 코드를 통해 테스트
         */
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}