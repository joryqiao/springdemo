package springdemo.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcachedManager {

    // 创建全局的唯一实例
    protected static MemcachedClient memcachedClient;

    protected static MemcachedManager memcachedManager = new MemcachedManager();

    // 设置与缓存服务器的连接池
    static {
	// 服务器列表和其权重
	String[] servers = { "127.0.0.1:11211" };
	Integer[] weights = { 3 };

	MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11212"),
		new int[] { 1 });
	builder.setCommandFactory(new BinaryCommandFactory());
	builder.setConnectionPoolSize(5); // set connection pool size to five
	builder.setConnectTimeout(10000);

	try {
	    memcachedClient = builder.build();
	    System.out.println("Init Memcached Client");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    protected MemcachedManager() {

    }

    public static MemcachedManager getInstance() {
	return memcachedManager;
    }
    
    public boolean set(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
	return memcachedClient.set(key, exp, value);
    }

    public boolean add(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
	return memcachedClient.add(key, exp, value);
    }
    
    public boolean replace(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
	return memcachedClient.replace(key, exp, value);
    }
    
    public Object get(String key) throws TimeoutException, InterruptedException, MemcachedException {
	return memcachedClient.get(key);
    }
    
    
    /**
    public static void main(String[] args) {
	MemcachedManager cache = MemcachedManager.getInstance();
	long startDate = System.currentTimeMillis();
	for (int i = 0; i < 10000 * 1000; i++) {
	    cache.add("test" + i, "中国");
	}
	long endDate = System.currentTimeMillis();

	long nowDate = (endDate - startDate) / 1000;
	System.out.println(nowDate);
	System.out.print(" get value : " + cache.get("test"));
    }
*/
}
