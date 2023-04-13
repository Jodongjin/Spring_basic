package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        /**
         * ClientBean이 초기화될 때, PrototypeBean이 한 번 주입된 이후로 같은 걸 사용
         */
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
//    @RequiredArgsConstructor
    static class ClientBean {
//        private final PrototypeBean prototypeBean;

//        ApplicationContext applicationContext;

        /**
         * ObjectFactory > ObjectProvider
         * ObjectProvider - 몇 가지 편의기능 더 제공
         */
        // private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // 자바 표준이지만 라이브러리 써야함
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
//            prototypeBean.addCount();
            // 매번 logic을 호출할 때마다 getBean을 하면 항상 새로운 빈을 생성함 -> 안 좋은 방법
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);

            /* ApplicationContext를 통해서 찾는 그 기능만 별도로 제공하여 간단하게 prototype bean을 생성 가능 */
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        // 호출 안 됨
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
