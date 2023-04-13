package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // final 필드를 대상으로 한 생성자를 자동으로 만들어 줌
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    /**
     * 추상화와 구체화 모두에게 의존하고 있음 (DiscountPolicy & FixDiscountPolicy)
     */
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    /**
     * 생성자를 통한 DI는 스프링 컨테이너가 빈을 등록하는 과정에서 이루어짐 (빈을 등록하기 위해 객체를 생성할 때 주입이 필요하기 때문)
     * setter를 통한 DI는 스프링 컨테이너가 의존관계를 주입하는 과정에서 이루어짐
     * 생성자를 통한 DI는 필드에 final 키워드를 사용할 수 있기 때문에 더 안전
     */

    /* -> 롬복으로 대체
    @Autowired // 생성자가 하나일 경우 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */

    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
     */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 단일 체계 원칙 (discount가 어떻게 작동하든 OrderService에서 알 필요 x)

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    /* 테스트 용도 */
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
