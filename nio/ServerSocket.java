/**
 * @Project: java-nio
 * @Author: Mr.Gao
 * @Date: 2017年7月22日 下午4:46:38
 * @Since: JDK1.5
 */
package nio;

import java.io.IOException;

/**
 * @ClassName: ServerSocket
 * @Description: NIO服务端
 * @Author Mr.Gao
 * @Date: 2017年7月22日 下午4:46:38
 */
public class ServerSocket {

	/**
	 * 监听端口号
	 */
	private static final int PORT = 9090;

	/**
	 * @Title: main
	 * @param @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		// 单线程处理所有I/O连接
		new Thread(new ServerHandleThread(PORT)).start();
	}

}

/**
 * @ClassName: ServerCmd
 * @Description: 服务端指令常量类
 * @Author Mr.Gao
 * @Date: 2017年7月21日 下午5:12:02
 */

final class ServerCmd {
	// 退出指令
	public static final String CMD_EXIT = "EXIT";
	// 查询服务器时间
	public static final String CMD_QUERY_TIME = "QUERY TIME";
}

class ServerHandleThread implements java.lang.Runnable {

	private java.nio.channels.ServerSocketChannel serverChannel = null;
	private java.nio.channels.Selector selector = null;
	private volatile boolean stop;

	public ServerHandleThread(final int port) {
		try {
			// 1. 创建java.nio.channels.ServerSocketChannel
			serverChannel = java.nio.channels.ServerSocketChannel.open();
			// 2. 设置ServerSocketChannel的TCP连接属性
			serverChannel.socket().bind(new java.net.InetSocketAddress(port));
			// 3. 设置ServerSocketChannel为非阻塞模式
			serverChannel.configureBlocking(false);
			// 4. 创建多路复用器Selector
			selector = java.nio.channels.Selector.open();
			// 5. 将ServerChannel注册到Selector上监听SelectionKey.OP_ACCEPT事件
			serverChannel.register(selector, java.nio.channels.SelectionKey.OP_ACCEPT);
			System.out.println("server is start on port : " + port);
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止服务器
	 */
	public void stop() {
		stop = true;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// 6. while循环处理SelectionKey事件
		while (!stop) {
			// selector.select()方法会阻塞当前线程,设置1秒的超时时间后唤醒线程
			try {
				selector.select(1000L);
				java.util.Set<java.nio.channels.SelectionKey> channelsSet = selector
						.selectedKeys();
				java.nio.channels.SelectionKey key = null;
				for (java.util.Iterator<java.nio.channels.SelectionKey> it = channelsSet
						.iterator(); it.hasNext();) {
					key = it.next();
					it.remove();
					try {
						handleKey(key);
					}
					catch (Exception e) {
						key.cancel();
						if (null != key.channel()) {
							key.channel().close();
						}
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (null != selector) {
			try {
				selector.close();
			}
			catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @throws IOException
	 * @Title: handleKey
	 * @Description: 处理selectKey
	 * @param @param key
	 * @return void
	 * @throws
	 */
	private void handleKey(java.nio.channels.SelectionKey key) throws IOException {
		if (null == key) {
			return;
		}
		if (!key.isValid()) {
			return;
		}
		// 7.处理接受到客户端连接成功事件
		if (key.isAcceptable()) {
			java.nio.channels.ServerSocketChannel serverChannel = (java.nio.channels.ServerSocketChannel) key
					.channel();
			// 8.这个方法与java.net.ServerSocket.accept()一样会阻塞
			java.nio.channels.SocketChannel clientChannel = serverChannel.accept();
			// 获取客户端socketChannel，设置为非阻塞模式
			clientChannel.configureBlocking(false);
			// 9.clientChannel注册Selector到的SelectionKey.OP_READ事件
			clientChannel.register(selector, java.nio.channels.SelectionKey.OP_READ);
			return;
		}
		// 10.处理读取客户端数据事件
		if (key.isReadable()) {
			java.nio.channels.SocketChannel clientChannel = (java.nio.channels.SocketChannel) key
					.channel();
			java.nio.ByteBuffer readBuffer = java.nio.ByteBuffer.allocate(1024);
			// read()为非阻塞，会直接返回读取到的字节数结果.读取数据到写入ByteBuffer到
			int readNum = clientChannel.read(readBuffer);
			if (readNum > 0) {// 读取到有效字节数, decode数据，开始业务逻辑处理
				// 切换ByteBuffer为读模式
				readBuffer.flip();
				byte[] b = new byte[readBuffer.remaining()];
				readBuffer.get(b);
				String cmd = new String(b, "utf-8");
				System.out.println("server receive cmd: " + cmd);
				if (ServerCmd.CMD_EXIT.equals(cmd)) {
					return;
				}
				String response = null;
				if (ServerCmd.CMD_QUERY_TIME.equals(cmd)) {
					response = new java.util.Date(System.currentTimeMillis()).toString();
				}
				else {
					response = "ERROR CMD";
				}
				byte[] responseByte = response.getBytes("utf-8");
				java.nio.ByteBuffer writeBuffer = java.nio.ByteBuffer
						.allocate(responseByte.length);
				// 将结果数据写入到ByteBuffer
				writeBuffer.put(responseByte);
				// 切换ByteBuffer为读模式
				writeBuffer.flip();
				clientChannel.write(writeBuffer);
			}
			else if (readNum < 0) {// connection链路已经关闭，需要个关闭socketChannel,释放资源
				key.cancel();
				selector.close();
			}
			return;
		}

	}
}
