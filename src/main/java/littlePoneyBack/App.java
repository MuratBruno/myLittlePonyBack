package littlePoneyBack;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import littlePoneyBack.DAO.PonyDAO;
import littlePoneyBack.model.Pony;

//@Configuration
//@ComponentScan(basePackages= {"littlePoneyBack","littlePoneyBack.DAO","littlePoneyBack.controller","littlePoneyBack.model","littlePoneyBack.exception","littlePoneyBack.config"})
@SpringBootApplication
public class App {

	
	public static void main(String[] args) {
		
		/*AbstractApplicationContext context = new AnnotationConfigApplicationContext(App.class);
		PonyDAO ponyDAO = context.getBean(PonyDAO.class);
		
		Pony p=new Pony();
		p.setName("taata");
		System.out.println(p.toString());
		
		//insertion
		ponyDAO.save(p);
		
		ArrayList<Integer> li = new ArrayList<>();
		li.add(1);
		Iterable<Pony> lg =ponyDAO.findAllById(li);
		for(Pony g:lg) {
			System.out.println(g.toString());
		}
		 context.close();*/
		SpringApplication.run(App.class, args);
		
		
	}
}
