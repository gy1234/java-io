/**
 * @Project: java-bio
 * @Package: cn.gy.bio
 * @Author: Mr.Gao
 * @Date: 2017年7月21日 下午5:21:27
 * @Since: JDK1.3
 */

/**
 * @ClassName: ClientSocket
 * @Description: 客户端类
 * @Author Mr.Gao
 * @Date: 2017年7月21日 下午5:21:27
 */
public class ClientSocket {
	/**
	 * 监听端口号
	 */
	private static final int PORT = 9090;

	public static void main(String[] args) {

		java.io.BufferedReader in = null;
		java.io.PrintWriter out = null;
		java.net.Socket socket = null;
		try {
			socket = new java.net.Socket(java.net.InetAddress.getLocalHost(),
					PORT);
			out = new java.io.PrintWriter(socket.getOutputStream(), true);
			out.println("QUERY TIME");
			System.out.println("send cmd to server suceess!");
			in = new java.io.BufferedReader(new java.io.InputStreamReader(
					socket.getInputStream()));
			System.out.println("server response time: " + in.readLine());

		} catch (java.io.IOException e) {
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
		}
	}

}
