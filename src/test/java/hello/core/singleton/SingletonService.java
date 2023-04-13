package hello.core.singleton;

public class SingletonService {

    /* 자바가 실행되면서 static 영역의 객체를 초기화하여 하나만 공유 */
    private static final SingletonService instance = new SingletonService();

    /* private 생성자로 생성 제한을 둘 수도 있음 */
    private SingletonService() {

    }

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
