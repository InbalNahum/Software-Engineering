package application;

import java.util.Optional;

import client.SqlClient;
import server.ServerResponse;
/**
 * Class for connected the client to the server
 * @author inahum
 *
 */
public class WaitToServer {
	/**
	 * @param sqlClient
	 * @param requestToken
	 * @return
	 */
	public static Optional<ServerResponse> waitToServerResponse(SqlClient sqlClient,
			int requestToken) {
		Optional<ServerResponse> toRet;
		
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toRet = sqlClient.getResponseByToken(requestToken);
		} while (!toRet.isPresent());
		return toRet;
	}
	/**
	 * Wait for server token
	 * A client communicates with the server with a unique token
	 * @param sqlClient
	 * @return
	 * @throws InterruptedException
	 */
	public static int waitForServerToken(SqlClient sqlClient) throws InterruptedException {
		Optional<Integer> toRet;
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toRet = sqlClient.fetchToken();
		} while (!toRet.isPresent());
		return toRet.get();
	}

}
