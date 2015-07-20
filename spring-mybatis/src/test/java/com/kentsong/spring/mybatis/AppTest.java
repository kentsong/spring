package com.kentsong.spring.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kentsong.spring.mybatis.mapper.Mapper;
import com.kentsong.spring.mybatis.service.SqlSessionService;
import com.kentsong.spring.mybatis.vo.Person;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config-test.xml")
public class AppTest {

	private static Log log = LogFactory.getLog(AppTest.class);
	
	@Autowired 
	private SqlSessionService sqlSessionService;
	private Mapper mapper;
	
	@Before
	public void setup(){
		mapper = sqlSessionService.getMapper(Mapper.class);
	}


	@Test
	public void testSelectAllParent(){
		log.info("testing selectAllParent()...");
		
		List<Person> persons = mapper.selectAllPerson();
		
		assertNotNull("failure - parents is null", persons);
		assertTrue("failure - expected 3 parents", persons.size() == 6);

		log.info(persons);
	}
	
}
