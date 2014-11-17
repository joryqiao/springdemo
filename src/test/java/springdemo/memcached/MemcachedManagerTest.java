package springdemo.memcached;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemcachedManagerTest {

    private static MemcachedManager mcManager;
    private static String springConfigPath = "classpath:/spring/application-config.xml";
   // @BeforeClass
    public static void setup(){
	mcManager = MemcachedManager.getInstance();
    }
    
   // @Test
    public void testMemcached(){
	try {
//	    mcManager.set("key1", 3600, "key 1 in value.");
//	    assertEquals("key 1 in value.", mcManager.get("key1"));
	    
//	    System.out.println("look : " + mcManager.get("key1"));
//	    mcManager.add("key1", 3600, "key 1 in value. some");
//	    assertEquals("key 1 in value. some", mcManager.get("key1"));
	    
	    mcManager.replace("key1", 3600, "value of key 1.");
	    assertEquals("value of key 1.", mcManager.get("key1"));
	    
	} catch (TimeoutException e) {
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (MemcachedException e) {
	    e.printStackTrace();
	}
    }
    
    //@Test
    public void testAdd() throws TimeoutException, InterruptedException, MemcachedException{
	assertNull(mcManager.get("key2"));
	mcManager.add("key2", 3600, "Hello");
	assertEquals("Hello", mcManager.get("key2"));
    }
    
   // @Test
    public void testSpringIntegration() throws TimeoutException, InterruptedException, MemcachedException{
	
	ApplicationContext appContext = new ClassPathXmlApplicationContext(springConfigPath);
	
	MemcachedClient mcClient = (MemcachedClient) appContext.getBean("memcachedClient");
	assertNotNull(mcClient);
	
	String key = "key1";
	String value = "value1";
	int exp = 3600;
	
	List<String> inputs = new ArrayList<String>();
	for (int i = 0; i < 1000; i++) {
	    inputs.add("fake string " + i);
	}

	for (int i = 0; i < 1000; i++) {
	    mcClient.set("key" + i, 3600, inputs.get(i));
	}

	for (int i = 0; i < 1000; i += 2) {
	    System.out.println(mcClient.get("key" + i, 200));
	}
	
//	try {
////	    mcClient.set(key, exp, value);
//	    assertEquals("value1", mcClient.get(key));
//	} catch (TimeoutException | InterruptedException | MemcachedException e) {
//	    e.printStackTrace();
//	}
    }
    
    @Test
    public void testXmemcachedSimple() throws Exception {
//	MemcachedClient mcClient = new XMemcachedClient("192.168.234.81", 11212);
//	ApplicationContext appContext = new ClassPathXmlApplicationContext(springConfigPath);
//	MemcachedClient mcClient = (MemcachedClient) appContext.getBean("memcachedClient");
	
	MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.234.81:11212"));
	builder.setConnectionPoolSize(5); // set connection pool size to five
	
	builder.setConnectTimeout(20000);
	builder.setCommandFactory(new BinaryCommandFactory());
	MemcachedClient mcClient = builder.build();
	
	for (int i = 0; i < 1000; i++) {
	    System.out.println(mcClient.get("key" + i, 200));
	}
    }
}
