import java.net.Socket;
import java.io.DataInputStream;

public class FxClient{
    public static void main(String[] args) throws Exception{
        try(Socket socket = new Socket("localhost", 5000)) {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String input = new String(in.readAllBytes());
            System.out.println(input);
        }
    }
}