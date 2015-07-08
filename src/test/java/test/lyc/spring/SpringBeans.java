package test.lyc.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeans {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		System.out.println("-----spirng------------");
		TestBean bean = (TestBean) context.getBean("test");
		System.out.println(bean + "\t" + bean.getName() + "\t" + bean.getAge());
		System.out.println();
		TestBean bean1 = (TestBean) context.getBean("test");
		System.out.println(bean1 + "\t" + bean1.getName() + "\t" + bean1.getAge());
		System.out.println();
		TestBean bean2 = (TestBean) context.getBean("test1");
		System.out.println(bean2 + "\t" + bean2.getName() + "\t" + bean2.getAge());
	}
}
