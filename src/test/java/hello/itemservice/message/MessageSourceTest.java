package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage(){
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    /** 메시지가 없는 경우 NoSuchMessageException 발생 */
    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    /** 메시지가 없어도 기본 메시지(defaultMessage)를 사용하면 기본 메시지가 반환된다 */
    @Test
    void notFoundMessageCodeDefaultMessage(){
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /** 메시지의 {0} 부분은 매개변수를 전달해서 치환할 수 있다 */
    @Test
    void argumentMessage(){
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    /** 국제화 파일 선택 - 디폴트 */
    @Test
    void defaultLang(){
        assertThat(ms.getMessage("hello",null,null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello",null, Locale.KOREA)).isEqualTo("안녕");
    }

    /** 국제화 파일 선택 - 영어 */
    @Test
    void enLang(){
        assertThat(ms.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
    }
}
