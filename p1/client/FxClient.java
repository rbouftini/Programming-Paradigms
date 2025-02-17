import java.net.Socket;
import java.io.*;
public class FxClient {
    public static void main(String[] args) throws Exception{
        try (Socket socket = new Socket("localhost", 4000)){
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String command = args[0];
            String file_name = args[1];
            if (command.equals("d")){
                String header = "download " + file_name + "\n";
                out.write(header.getBytes());
                header = reader.readLine();
                String[] tokens = header.split(" ");
                if (tokens[0].equals("OK")){
                    int file_size = Integer.parseInt(tokens[1]);
                    byte[] bytes = in.readNBytes(file_size);
                    try(FileOutputStream fileOut = new FileOutputStream(file_name)){
                        fileOut.write(bytes);
                        System.out.println("File downloaded succesfully");
                    }
                }
                else{
                    System.out.println("We are unable to download your file");
                }
            }
        }
    }
}
