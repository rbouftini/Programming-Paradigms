import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class FxServer {
    public static final int PORT = 4000;
    public static void main(String[] args) throws Exception {
        try (ServerSocket ss = new ServerSocket(PORT)){
            while (true) {
                try(Socket socket = ss.accept()){
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    System.out.println("Client connected ");
                    String header = reader.readLine();
                    System.out.println("Header received is" + header);
                    String[] tokens = header.split(" ");
                    if (tokens[0].equals("download")){
                        try(FileInputStream fileIn = new FileInputStream(tokens[1])){
                            int file_size = fileIn.available();
                            header = "OK " + file_size + "\n";
                            out.write(header.getBytes());
                            byte[] bytes = new byte[file_size];
                            fileIn.read(bytes);
                            out.write(bytes); 
                        }
                        catch(Exception e){
                            System.out.println("Not found");
                        }
                    }
                        
                }
            }
        }
    }
}