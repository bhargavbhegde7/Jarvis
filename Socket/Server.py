#!/usr/bin/python           # This is server.py file

import socket               # Import socket module

s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 12345                # Reserve a port for your service.
s.bind((host, port))        # Bind to the port

s.listen(1)                 # Now wait for client connection.

print "Waiting for connection : "
c, addr = s.accept()     # Establish connection with client.
print "Got connection from : ", addr

while True:
   print "waiting for client message : "
   message = str(c.recv(1024)).strip()
   if not message or message == "quit":
       break
   print "received message : ", message

c.close()                # Close the connection
