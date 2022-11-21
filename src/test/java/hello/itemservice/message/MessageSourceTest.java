package hello.itemservice.message;

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
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);

        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);

        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);

        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        String message1 = ms.getMessage("hello", null, null);
        String message2 = ms.getMessage("hello", null, Locale.KOREA);

        assertThat(message1).isEqualTo("안녕");
        assertThat(message2).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        String message = ms.getMessage("hello", null, Locale.ENGLISH);

        assertThat(message).isEqualTo("hello");
    }
}
