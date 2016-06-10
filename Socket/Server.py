#!/usr/bin/python           # This is server.py file

import socket
from thread import *
import logging
import sys

def getIp(interface):
    import netifaces as ni
    ni.ifaddresses(interface)
    ip = ni.ifaddresses(interface)[2][0]['addr']
    return ip

def runCommand(command,):
    print "executing ", command
    print "done running . . . "
    print "ending . . . "
    return

def clientThread(conn,addr):
    print "Got connection from : " + str(addr)
    while True:
        try:
            message = str(conn.recv(1024)).strip() #number of bytes
            print message," by ",addr
            if not message or message.lower()=='quit':
                print "Terminating connection . . . "
                break
            else:
                try:
                    start_new_thread(runCommand,(message,))
                except:
                    print "Exception in running command thread . . .", "Terminating connection . . . "
                    break;
        except:
            print "Exception in client thread . . .", "Terminating connection . . . "
            break

    conn.close()
    return

def main(argv):
    port = int(argv[0])
    host = ''
    sock = socket.socket()
    sock.bind((host, port))
    sock.listen(1)                 #only one client
    conn = None
    while True:
        print "Waiting for connection . . . \n"
        conn, addr = sock.accept()
        start_new_thread(clientThread,(conn,addr))

    conn.close()
    sock.close()

if __name__ == '__main__':
    main(sys.argv[1:])
