package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
        /**
         * Configuration은 Component를 내장하고 있기에 ComponentScan에 잡히므로 제외
         * AppConfig.class 제외됨 (수동으로 설정하는 파일이기 때문)
         * basePackages default: ComponentScan이 붙은 클래스의 위치
         */
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
    /**
     * 자동 빈 등록, 수동 빈 등록 충돌 수동 빈 등록이 우선순위가 높아 Spring에서는 자동으로 오버라이딩
     * SpringBoot는 자동 오버라이딩을 방지하기 위해 에러 던져줌
     */
}
