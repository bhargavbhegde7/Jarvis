#!/usr/bin/python           # This is server.py file

import socket               # Import socket module

def getIp(interface):
    import netifaces as ni
    ni.ifaddresses(interface)
    ip = ni.ifaddresses(interface)[2][0]['addr']
    return ip

s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
#host = getIp('eth0')
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
       print 'quit message received '
       break
   print "received message : ", message

c.close()                # Close the connection
