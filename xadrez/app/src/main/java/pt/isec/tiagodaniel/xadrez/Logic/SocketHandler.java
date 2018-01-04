package pt.isec.tiagodaniel.xadrez.Logic;

import java.net.Socket;

public class SocketHandler {
    private static Socket clientSocket;

    public static synchronized Socket getClientSocket() {
        return clientSocket;
    }

    public static synchronized void setClientSocket(Socket socket) {
        SocketHandler.clientSocket = socket;
    }
}
