#!/usr/bin/python           # This is server.py file

import socket
from thread import *
import logging
import sys

def runCommand(command,):
    print "executing ", command
    print "done running . . . "
    print "ending . . . "
    return

def clientThread(conn,addr):
    print "Got connection from : " + str(addr)
    while True:
        try:
            message = str(conn.recv(1024)).strip() #1024 bytes
            print message," by ",addr
            if not message or message.lower()=='quit':
                print "Terminating connection . . . "
                break   #connection closes as soon as break from the loop
            else:   #incoming normal command message
                try:
                    start_new_thread(runCommand,(message,)) #run the task inside a new thread
                except:
                    print "Exception in running command thread . . .", "Terminating connection . . . "
                    break
        except:
            print "Exception in client thread . . .", "Terminating connection . . . "
            break

    conn.close()
    return

def main(argv):
    port = int(argv[0])             #port from command line argument
    host = ''                       #'localhost' or '127.0.0.1' or '' are all same
    sock = socket.socket()
    sock.bind((host, port))
    sock.listen(1)                  #only one client
    conn = None                     #to access conn outside the loop
    while True:
        print "Waiting for connection . . . \n"
        conn, addr = sock.accept()  #waits untill a client connects
        start_new_thread(clientThread,(conn,addr))  #start a thread for every new client

    conn.close()
    sock.close()

if __name__ == '__main__':
    main(sys.argv[1:])
