#!/usr/bin/python           # This is client.py file

import socket               # Import socket module

s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 12345                # Reserve a port for your service.

s.connect((host, port))

while True:
    message = raw_input("enter message : ")
    # if message == "quit":
    #     s.send(message)
    #     break
    s.send(message)
    print s.recv(1024)

s.close                     # Close the socket when done
