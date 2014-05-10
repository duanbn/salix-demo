package com.salix.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MainTest {

	@Resource(name = "echo")
	private IEchoService echoService;

	@Test
	public void test() throws Exception {
		String value = echoService.echo("hello salix");
		System.out.println(value);
		echoService.discard();
		echoService.discard("discard salix");

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
			for (int i = 0; i < 100; i++) {
				System.out.println(this.echoService.echo("salix concurrent " + i));
			}
		}
	}

}
