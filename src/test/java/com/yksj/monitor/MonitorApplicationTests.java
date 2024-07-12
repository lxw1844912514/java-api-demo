package com.yksj.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class MonitorApplicationTests {

	@Test
	void contextLoads() {
//		test();
		test2();
	}

	public static void test() {
		// 获取当前日期和时间
		LocalDateTime datetime = LocalDateTime.now();
		System.out.println("当前日期和时间: " + datetime);

		// 创建指定日期和时间的DateTime对象
		LocalDateTime specificDatetime = LocalDateTime.of(2021, 3, 15, 10, 30, 0);
		System.out.println("指定日期和时间: " + specificDatetime);
	}

	public static void test2() {
		Entity entity = new Entity();
		System.out.println("Create Time: " + entity.getCreateTime());
		System.out.println("Update Time: " + entity.getUpdateTime());

		entity.update();
		System.out.println("Update Time after update: " + entity.getUpdateTime());
	}


}
