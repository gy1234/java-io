/**
 * @Project: java-nio
 * @Author: Mr.Gao
 * @Date: 2017年7月22日 下午5:21:27
 * @Since: JDK1.5
 */
package nio;

import java.io.IOException;

/**
 * @ClassName: ClientSocket
 * @Description: NIO客户端
 * @Author Mr.Gao
 * @Date: 2017年7月22日 下午4:46:38
 */
public class ClientSocket {
	/**
	 * 监听端口号
	 */
	private static final int PORT = 9090;

	public static void main(String[] args) {
		new Thread(new ClientHandleThread(PORT)).start();
	}

}

class ClientHandleThread implements java.lang.Runnable {

	private java.nio.channels.SocketChannel clientChannel = null;

	private java.nio.channels.Selector selector = null;

	private volatile boolean stop;

	private int port;

	/**
	 * Creates a new instance of ClientHandleThread.
	 */
	public ClientHandleThread(final int port) {
		this.port = port;
		try {
			// 1.创建SocketChannel
			clientChannel = java.nio.channels.SocketChannel.open();
			// 2.设置SocketChannel非阻塞模式
			clientChannel.configureBlocking(false);
			// 3.创建Selector
			selector = java.nio.channels.Selector.open();
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @throws IOException
	 * @Title: doWirte
	 * @Description: 写数据到服务端
	 * @param
	 * @return void
	 * @throws
	 */
	private void doWirte() throws IOException {
		byte[] reqByte = "QUERY TIME".getBytes();
		java.nio.ByteBuffer writeBuffer = java.nio.ByteBuffer.allocate(reqByte.length);
		writeBuffer.put(reqByte);
		// 切换为读模式
		writeBuffer.flip();
		clientChannel.write(writeBuffer);
		if (!writeBuffer.hasRemaining()) {
			System.out.println("send cmd to server suceess!");
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			// 4.设置SocketChannel的tcp属性, 并且尝试connection
			boolean firtConneciton = clientChannel
					.connect(new java.net.InetSocketAddress(java.net.InetAddress
							.getLocalHost(), port));
			// 5.注册connection事件或read事件到selector
			if (firtConneciton) {// 首次连接成功直接注册read事件
				clientChannel.register(selector, java.nio.channels.SelectionKey.OP_READ);
				doWirte();
			}
			else {// 注册连接事件到selector
				clientChannel.register(selector,
						java.nio.channels.SelectionKey.OP_CONNECT);
			}
		}
		catch (java.io.IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		// 6. while循环处理SelectionKey事件
		while (!stop) {
			try {
				selector.select(1000L);
				java.util.Set<java.nio.channels.SelectionKey> channelsSet = selector
						.selectedKeys();
				java.nio.channels.SelectionKey key = null;
				for (java.util.Iterator<java.nio.channels.SelectionKey> it = channelsSet
						.iterator(); it.hasNext();) {
					key = it.next();
					it.remove();
					handleKey(key);
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
	 * @Title: handleKey
	 * @Description: 处理selectKey
	 * @param @param key
	 * @return void
	 * @throws
	 */
	private void handleKey(java.nio.channels.SelectionKey key) {
		if (null == key) {
			return;
		}
		if (!key.isValid()) {
			return;
		}
		// 7.处理连接服务端成功事件
		if (key.isConnectable()) {

			java.nio.channels.SocketChannel channel = (java.nio.channels.SocketChannel) key
					.channel();
			try {
				if (channel.finishConnect()) {
					// 8.注册监听服务端的消息的read事件
					channel.register(selector, java.nio.channels.SelectionKey.OP_READ);
					// 9.写数据到服务端
					doWirte();
				}
				else {
					System.exit(1);
				}
			}
			catch (java.io.IOException e) {
				e.printStackTrace();
			}

			return;
		}
		// 10.处理读取服务端数据事件
		if (key.isReadable()) {
			java.nio.channels.SocketChannel clientChannel = (java.nio.channels.SocketChannel) key
					.channel();
			java.nio.ByteBuffer readBuffer = java.nio.ByteBuffer.allocate(1024);
			try {
				// read()为非阻塞，会直接返回读取到的字节数结果.读取数据到写入ByteBuffer到
				int readNum = clientChannel.read(readBuffer);
				if (readNum > 0) {// 读取到有效字节数, decode数据，开始业务逻辑处理
					// 切换ByteBuffer为读模式
					readBuffer.flip();
					byte[] b = new byte[readBuffer.remaining()];
					readBuffer.get(b);
					System.out.println("server response time: " + new String(b, "utf-8"));
					this.stop = true;
				}
				else if (readNum < 0) {// connection链路已经关闭，需要个关闭socketChannel,释放资源
					key.cancel();
					selector.close();
				}
			}
			catch (java.io.IOException e) {
				e.printStackTrace();
			}
			return;
		}

	}

}