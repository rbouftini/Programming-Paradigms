import argparse
from socket import create_connection
import os

parser = argparse.ArgumentParser()
parser.add_argument("command")
parser.add_argument("file_name")
args = parser.parse_args()
    
SERVER_ENDPOINT = ("localhost", 7000)

with create_connection(SERVER_ENDPOINT) as socket:
    print("Client connected to:", SERVER_ENDPOINT)
    if args.command == "u":
        if not os.path.exists(args.file_name):
            print("Target file does not exist in current directory")
        else:
            file_size = os.path.getsize(args.file_name)
            header = "upload " + args.file_name + " " + str(file_size)
            socket.send(header.encode())
            with open(args.file_name, "r") as file:
                lines = file.read()
            socket.send(lines.encode())      
            reply = socket.recv(1024).decode()
            if reply == "STORED":
                print("Your file was succesfully uploaded")
            else:
                print("Your file upload failed")  

    if args.command == "d":
        header = "download " + args.file_name
        socket.send(header.encode())
        reply = socket.recv(1024).decode()
        tokens = reply.split(" ")
        print(reply)
        if tokens[0] == "OK":
            file_content = socket.recv(int(tokens[1])).decode()
            with open(args.file_name, "w") as file:
                file.write(file_content)
            print("File was succesfully downloaded")
        elif tokens[0] == "NOT FOUND":
            print("Sorry the file specified was not found")
        else:
            print("You are connect to the wrong server")

