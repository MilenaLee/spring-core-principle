package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

//        find prototypeBean1
//        PrototypeBean.init
//        find prototypeBean2
//        PrototypeBean.init

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isNotSameAs(bean2);

        bean2.destroy();

        ac.close();
    }

    // @Component를 안붙여놓아도,
    // AnnotationConfigApplicationContext()의 대상이 되면 자동으로 Bean 컴포넌트스캔 대상이 된다
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
