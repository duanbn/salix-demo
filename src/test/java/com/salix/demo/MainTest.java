package com.salix.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.salix.server.Shutdown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MainTest {

	@Resource
	private IEchoService echoService;

	@Test
	public void testShutdown() throws Exception {
		Shutdown sd = new Shutdown("localhost", 9999);
		sd.doShutdown();
	}

	@Test
	public void test() throws Exception {
		for (;;) {
			String value = echoService.echo("hello salix");
			System.out.println(value);
			echoService.discard();
			echoService.discard("discard salix");
			
//			Thread.sleep(2000);
		}
	}

	@Test
	public void testConcurrent() throws Exception {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			Thread t = new EchoThread(echoService);
			t.start();
			list.add(t);
		}

		for (Thread t : list) {
			t.join();
		}
	}

	class EchoThread extends Thread {
		private IEchoService echoService;

		public EchoThread(IEchoService echoService) {
			this.echoService = echoService;
		}

		public void run() {
			for (int i = 0; i < 1; i++) {
				System.out.println(this.echoService.echo("salix concurrent " + i));
			}
		}
	}

}
