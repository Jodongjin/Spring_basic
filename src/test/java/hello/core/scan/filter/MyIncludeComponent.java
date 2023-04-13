package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // TYPE: class level을 target으로
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
