package com.salix.test;

import org.junit.*;

import com.salix.client.connection.*;

public class PingTest {

    private int port = 9999;

    private ConnectionPool cp;

    @Before
    public void before() {
        this.cp = new SimpleConnectionPool("127.0.0.1", port);
        this.cp.startup();
    }

    @After
    public void after() {
        this.cp.shutdown();
    }

    @Test
    public void testPing() throws Exception {
        Connection c = this.cp.getConnection();
        System.out.println(c.ping());
        c.close();
    }

}
