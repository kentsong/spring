package com.kentsong.spring.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kentsong.spring.web.vo.Person;


@Controller
@RequestMapping(value="/rest")
public class RestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);
  
	
	@RequestMapping(value="/get/{cpdtNum}.json", method=RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public boolean getMethod(@PathVariable Long cpdtNum,  @RequestParam(value="param1", defaultValue="hello!!") String param1) {
		logger.info("cpdtNum:{}, param1:{}", cpdtNum, param1);
		return true;
	}
	
	
	@RequestMapping(value="/post/{key}", method=RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public boolean postMethod(@PathVariable String key, @RequestParam(value="param1", defaultValue="kentsong") String param1) {
		logger.info("param1:{}, param2:{}", param1, key);
		return true;
	}
	
	@RequestMapping(value="/person", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Person jsonResponse(@RequestParam(value="name", required=true) String name) {
		logger.info("person name:{}", name);
		Person person = new Person();
		person.setId(8891L);
		person.setName(name);
		return person;
	}
	
	@RequestMapping(value="/person/{peopleName}", method=RequestMethod.GET)
	public String findPerson(@PathVariable("peopleName") String name, Model model) {
	    model.addAttribute("name", name);
	    return "person";
	}
	
	@RequestMapping(value="/person/{peopleName}/pet/{petName}", method=RequestMethod.GET)
	public String findPet(@PathVariable("peopleName") String name, 
			@PathVariable("petName") String petName, Model model) {
	    model.addAttribute("name", name);
	    model.addAttribute("petName", petName);
	    return "pet";
	}
	
	@RequestMapping(value="/product/{cpdtNum}.json", method=RequestMethod.GET)
	public String findProduct(@PathVariable("cpdtNum") String cpdtNum,  Model model) {
	    model.addAttribute("cpdtNum", cpdtNum);
	    return "product";
	}
	
	/**
	 * 可使用httpClient post json 資料呼叫
	 * @param person
	 * @return
	 */
    @RequestMapping(value = "/jsonObj", method=RequestMethod.POST ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public Person person(@RequestBody Person person) {
		logger.info("person = {}", person);
		logger.info("person id"+ person.getId());
		logger.info("person Name"+ person.getName());
		person.setId(525L);
		person.setName("kimo");
		
		return person;
	}
	
  		
}

