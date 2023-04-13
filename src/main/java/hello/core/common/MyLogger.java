package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * proxyMode -> DI 시에 프록시(가짜)를 넣어줌
 * CGLIB 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 주입하는 것
 * 실제 사용하는 시점에서 진짜 객체의 메서드를 호출
 */
@Component
//@Scope("request")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // Interface -> TARGET_INTERFACE
public class MyLogger {

    /**
     * uuid는 http request 당 하나가 할당되므로 각 요청을 구분할 수 있음
     */
    private String uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestUrl + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}

