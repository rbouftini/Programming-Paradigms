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
                else if(tokens[0].equals("NOT") && tokens[1].equals("FOUND")) {
                    System.out.println("We are unable to download your file");
                }
                else {
                    System.out.println("You are not connected to the right server" + tokens[0]);
                }
            }

            else if(command.equals("u")){
                try(FileInputStream fileIn = new FileInputStream(file_name)){
                    int file_size = fileIn.available();
                    byte[] bytes = new byte[file_size];
                    fileIn.read(bytes);
                    String header = "upload " + file_name + " " + file_size + "\n";
                    out.write(header.getBytes());
                    out.write(bytes);
                }
                catch(Exception e){
                    System.out.println("File does not exist in current directory");
                }

                String header = reader.readLine();
                if (header.equals("STORED")){
                    System.out.println("File was uploaded succesfully in remote server");
                }
                else if(header.equals("FAILED")) {
                    System.out.println("Sorry we are unable to upload your file at the minute");
                }
                else {
                    System.out.println("You are not connected to the right server");
                }


            }
        }
    }
}
