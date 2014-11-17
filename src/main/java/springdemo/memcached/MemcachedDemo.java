package springdemo.memcached;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * link: https://code.google.com/p/xmemcached/wiki/Getting_Started#
 * Use_binary_protocol
 * 
 * @author jqiao@coupang.com
 *
 */
public class MemcachedDemo {

    public static void main(String[] args) throws Exception {
	MemcachedDemo demo = new MemcachedDemo();
	// demo.testXmemcached();
	demo.testXmemcachedUseBinaryProtocal();

    }

    public void testXmemcachedUseBinaryProtocal() throws IOException, TimeoutException, InterruptedException,
	    MemcachedException {
	MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11212"),
		new int[] { 1 });
	builder.setCommandFactory(new BinaryCommandFactory());// use binary
							      // protocol
	MemcachedClient memcachedClient = builder.build();

	testFakeData(memcachedClient);
    }

    public void testXmemcachedPool() {
	MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11212"));
	builder.setConnectionPoolSize(5); // set connection pool size to five
	
	builder.setConnectTimeout(10000);
	builder.setCommandFactory(new BinaryCommandFactory());
    }

    public void testXmemcachedSimple() throws Exception {
	MemcachedClient client = new XMemcachedClient("localhost", 11212);

	String inputString = "Hello Memcached!";
	// store a value for one hour(synchronously).
	client.set("key", 3600, inputString);
	// Retrieve a value.(synchronously).
	Object someObject = client.get("key");
	// Retrieve a value.(synchronously),operation timeout two seconds.
	someObject = client.get("key", 2000);

	System.out.println("someObject : " + someObject);

	// Touch cache item ,update it's expire time to 10 seconds.
	boolean success = client.touch("key", 10);
	System.out.println("success : " + success);

	// delete value
	client.delete("key");
    }
    
    public void testFakeData(MemcachedClient client) throws TimeoutException, InterruptedException, MemcachedException {
	List<String> inputs = new ArrayList<String>();
	for (int i = 0; i < 1000; i++) {
	    inputs.add("fake string " + i);
	}

	for (int i = 0; i < 1000; i++) {
	    client.set("key" + i, 3600, inputs.get(i));
	}

	for (int i = 0; i < 1000; i += 2) {
	    System.out.println(client.get("key" + i, 200));
	}

    }

}
