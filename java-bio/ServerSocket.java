/**
 * @Project: java-bio
 * @Package: cn.gy.bio
 * @Author: Mr.Gao
 * @Date: 2017年7月21日 下午4:46:38
 * @Since: JDK1.7
 */

/**
 * @ClassName: ServerSocket
 * @Description: BIO服务端
 * @Author Mr.Gao
 * @Date: 2017年7月21日 下午4:46:38
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
		java.net.ServerSocket server = null;
		try {
			server = new java.net.ServerSocket(PORT);
			System.out.println("server is start on port : " + PORT);
			java.net.Socket socket = null;
			while (true) {
				// accept()方式会阻塞当前线程，直到有客户端接入
				socket = server.accept();
				new Thread(new ServerHandleThread(socket)).start();
			}
		} catch (java.io.IOException e) {
			System.out.println("启动Server失败, error:\n" + e);
		} finally {
			if (null != server) {
				try {
					server.close();
					System.out.println("server is stop on port:" + PORT);
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
			System.exit(0);
		}
	}

	/**
	 * @ClassName: ServerCmd
	 * @Description: 服务端指令常量类
	 * @Author Mr.Gao
	 * @Date: 2017年7月21日 下午5:12:02
	 */

}

final class ServerCmd {
	// 退出指令
	public static final String CMD_EXIT = "EXIT";
	// 查询服务器时间
	public static final String CMD_QUERY_TIME = "QUERY TIME";
}

class ServerHandleThread implements java.lang.Runnable {

	private java.net.Socket socket;

	public ServerHandleThread(java.net.Socket socket) {
		this.socket = socket;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		java.io.BufferedReader in = null;
		java.io.PrintWriter out = null;
		try {
			// socket.getInputStream()方式也是阻塞的,直到读取完客户端的所有数据
			in = new java.io.BufferedReader(new java.io.InputStreamReader(
					socket.getInputStream()));
			// socket.getOutputStream()方式也是阻塞的,直到写出完到客户端的所有数据
			out = new java.io.PrintWriter(socket.getOutputStream(), true);
			String cmd = null;
			String response = null;
			while (true) {
				// 必须读取到一行末尾，直到读取到换行符号才结束
				cmd = in.readLine();
				if (null == cmd) {
					break;
				}
				System.out.println("server receive cmd: " + cmd);
				if (ServerCmd.CMD_EXIT.equals(cmd)) {
					break;
				}
				if (ServerCmd.CMD_QUERY_TIME.equals(cmd)) {
					response = new java.util.Date(System.currentTimeMillis())
							.toString();
				} else {
					response = "ERROR CMD";
				}
				out.println(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				out.close();
				out = null;
			}
			if (null != socket) {
				try {
					socket.close();
					socket = null;
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()
					+ "thread execute end.");
		}
	}

}
