package cn.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.jt.feign.EurekaServiceFeign;
import cn.jt.feign.SidecarService;

@RestController		//对外暴漏，和提供者对应方法一致
public class HelloController {
	
	@Autowired
	private EurekaServiceFeign feign;
	@Autowired
	private SidecarService sidecar;
	
	@HystrixCommand(fallbackMethod="fallBackHello")
	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable String name) {
		//调用提供者
		return feign.hello(name);
	}
	
	public String fallBackHello(String name){
		return "hystrix:"+name;
	}
	
	//实现sideCar对nodeJs的封装,返回欢迎页面
	//访问sidecar,调用node.js欢迎页面
	@RequestMapping("/node")
	public String Node(){
		return sidecar.node();			//feign接口定义
	}
}
