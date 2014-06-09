package com.salix.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.salix.demo.entity.TestEntity;
import com.salix.demo.service.IEchoService;
import com.salix.server.Shutdown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MainTest extends BaseTest {

	@Resource
	private IEchoService echoService;

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		byte[] data = new byte[1024 * 1024 * 5];
		Object value = echoService.echo(data);
		System.out.println(((byte[]) value).length);

		Map<String, Map<Integer, List<TestEntity>>> map = new HashMap<String, Map<Integer, List<TestEntity>>>();
		List<TestEntity> list = new ArrayList<TestEntity>();
		for (int i = 0; i < 12; i++)
			list.add(createEntity());
		Map<Integer, List<TestEntity>> map1 = new HashMap<Integer, List<TestEntity>>();
		map1.put(99, list);
		map1.put(101, list);
		map.put("echomap", map1);
		map = (Map<String, Map<Integer, List<TestEntity>>>) echoService.echo(map);
		for (Map.Entry<String, Map<Integer, List<TestEntity>>> entry : map.entrySet()) {
			System.out.println(entry.getKey());
			for (Map.Entry<Integer, List<TestEntity>> entry1 : entry.getValue().entrySet()) {
				System.out.println(entry1.getKey());
				System.out.println(entry1.getValue());
			}
		}

		echoService.discard();
		echoService.discard("discard salix");
	}

	@Test
	public void testConcurrent() throws Exception {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 200; i++) {
			Thread t = new EchoThread(echoService);
			t.start();
			Thread.sleep(500);
			list.add(t);
		}

		for (Thread t : list) {
			t.join();
		}
	}

	@Test
	public void testShutdown() throws Exception {
		Shutdown sd = new Shutdown("localhost", 9999);
		sd.doShutdown();
	}

	class EchoThread extends Thread {
		private IEchoService echoService;

		public EchoThread(IEchoService echoService) {
			this.echoService = echoService;
		}

		public void run() {
			for (;;) {
				try {
					System.out.println(this.getName() + "-" + this.echoService.echo(getContent(1000)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
