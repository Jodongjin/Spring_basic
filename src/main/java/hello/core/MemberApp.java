package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        /* 스프링 컨테이너 (Bean 관리) -> key(메서드 이름) : value(메서드에서 생성하는 객체) */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        /* 해당 이름을 가진 Bean을 get (Bean name은 기본적으로 메서드 이름, @Bean(name = )으로 설정 가능 */
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);

        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println("memberA = " + memberA.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
