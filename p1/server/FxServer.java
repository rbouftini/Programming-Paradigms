import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;

public class FxServer {
    public static final int port = 5000;  
    public static void main(String[] args) throws Exception{
        // Server needs to request a port number from the OS through the Socket API
        try(ServerSocket ss = new ServerSocket(port)){ // this throws an expection so I need to throw it or handle it
            while(true){
                try(Socket socket = ss.accept()){ //blocking call, listens forever
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.write("Hello World!".getBytes());
                }
            }
        }
    }
}