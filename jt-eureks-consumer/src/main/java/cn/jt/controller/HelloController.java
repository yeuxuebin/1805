package cn.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController		//对外暴漏，和提供者对应方法一致
public class HelloController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable String name) {
		//调用提供者
		String url = "http://PROVIDER-USER/hello/"+name;
		return restTemplate.getForObject(url, String.class);
	}
}
