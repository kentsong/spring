package com.kentsong.spring.mybatis.main;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kentsong.spring.mybatis.mapper.Mapper;
import com.kentsong.spring.mybatis.service.SqlSessionService;
import com.kentsong.spring.mybatis.vo.Person;


public class App 
{
  private static Log log = LogFactory.getLog(App.class);

    public static void main( String[] args )
    {

      ApplicationContext cxt = new ClassPathXmlApplicationContext("spring-config.xml");
      SqlSessionService sqlSessionService = (SqlSessionService) cxt.getBean("sqlSessionService");
      Mapper mapper = sqlSessionService.getMapper(Mapper.class);
      log.info("Running App...");

        System.out.println("List<Person> persons = service.selectAllPerson()");
        List<Person> persons = mapper.selectAllPerson();
        System.out.println("-> "+persons+"\n");
                          
        System.out.println("Person person = service.selectPerson(2)");
        Person person = mapper.selectPerson(2);
        System.out.println("-> "+person+"\n");
                                                  
        System.out.println("service.insertPerson(person)");
        person.setName("Inserted person");
        mapper.insertPerson(person);
        System.out.println("-> inserted..."+"\n");
                                                                                
        System.out.println("List<Person> persons = service.selectAllPerson()");
        persons = mapper.selectAllPerson();
        System.out.println("-> "+persons+"\n");

    }
}
