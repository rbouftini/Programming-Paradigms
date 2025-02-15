from socket import create_server
import os

SERVER_ENDPOINT = ("localhost", 7000)
with create_server(SERVER_ENDPOINT) as ss:
    print("Server bound to port number", SERVER_ENDPOINT[1])
    while True:
        socket, client_endpoint = ss.accept()
        with socket:
            print("Connected from:", client_endpoint)
            message = socket.recv(1024).decode()
            tokens = message.split(" ")
            if tokens[0] == "upload":
                try:
                    file_content = socket.recv(int(tokens[2])).decode()  # checking if its a valid integer should be in the protocol
                    with open(tokens[1], "w") as file:
                        file.write(file_content)
                    socket.send("STORED".encode())
                except:
                    socket.send("FAILED".encode())
            elif tokens[0] == "download":
                try:
                    with open(tokens[1], "r") as file:
                        header = "OK " + str(os.path.getsize(tokens[1])) 
                        socket.send(header.encode())
                        file_content = file.read()
                        socket.send(file_content.encode())
                except:
                    socket.send("NOT FOUND".encode())


