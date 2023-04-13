package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * MyLogger의 scope가 request인데, LogDemoController와 LogDemoService에서 DI를 요청하고 있음
 * 아직 request가 들어오기 전이므로 빈이 활성화되지 않았음
 * Provider를 사용하여 DI 시점을 지연해야 함
 */

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    /* DL기능을 제공하는 객체가 주입 */
//    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("logtest")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestUrl = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");
        Thread.sleep(1000);

        logDemoService.logic("testId");
        return "OK";
    }
}

/**
 * HttpServletRequest: 자바에서 제공하는 표준 servlet 규약으로 http request에 대한 정보를 받을 수 있음
 */