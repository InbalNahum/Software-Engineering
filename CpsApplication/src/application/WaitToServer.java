package application;

import java.util.Optional;

import client.SqlClient;
import server.ServerResponse;

public class WaitToServer {
	
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
