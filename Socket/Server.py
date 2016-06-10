#!/usr/bin/python           # This is server.py file

import socket               # Import socket module
from thread import *
import logging

#import pdb
#pdb.set_trace()

def getIp(interface):
    import netifaces as ni
    ni.ifaddresses(interface)
    ip = ni.ifaddresses(interface)[2][0]['addr']
    return ip

def runCommand(command,asd):
    #import time
    #print "command : ", command
    print "going to sleep . . . "
    #time.sleep(3)
    print "woke up . . . "
    print "ending . . . "


s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
#host = getIp('eth0')       # to get ip in linux systems
port = 12345                # Reserve a port for your service.
s.bind((host, port))        # Bind to the port

s.listen(1)                 # Now wait for client connection.

print "Waiting for connection . . . "
c, addr = s.accept()     # Establish connection with client.
print "Got connection from : ", addr

while True:
   print "waiting for client message . . . \n"
   message = str(c.recv(1024)).strip()

   if not message or message == "quit":
       print 'exiting . . .'
       break

   else:
       try:
           start_new_thread(runCommand,(message,""))
       except:
           logging.exception("Unhandled exception during main")

   #print "received message : ", message

c.close()                # Close the connection
