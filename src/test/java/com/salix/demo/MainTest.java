package com.salix.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.salix.demo.entity.TestEntity;
import com.salix.demo.service.IEchoService;
import com.salix.server.Shutdown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MainTest extends BaseTest {

    public static final Logger LOG = Logger.getLogger(MainTest.class);

	@Autowired
	private IEchoService echoService;

    private Random r = new Random();

	@Test
	public void testAvailable() throws Exception {
		for (;;) {
            
        }
	}

	@Test
	public void testConcurrent() throws Exception {
        int threadNum = 10;

		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < threadNum; i++) {
			Thread t = new EchoThread(echoService);
			t.start();
			Thread.sleep(1000);
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
                    Map<String, Map<Integer, List<TestEntity>>> map = new HashMap<String, Map<Integer, List<TestEntity>>>();
                    List<TestEntity> list = new ArrayList<TestEntity>();
                    for (int i = 0; i < 12; i++)
                        list.add(createEntity());
                    Map<Integer, List<TestEntity>> map1 = new HashMap<Integer, List<TestEntity>>();
                    map1.put(99, list);
                    map1.put(101, list);
                    map.put("echomap", map1);
                    echoService.echo(map);

                    echoService.echo(getContent(1000));
                    Thread.sleep(r.nextInt(1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
