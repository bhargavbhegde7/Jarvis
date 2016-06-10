#!/usr/bin/python           # This is server.py file

import socket
from thread import *
import logging
import sys
from multiprocessing.pool import ThreadPool
pool = ThreadPool(processes=1)

def getIp(interface):
    import netifaces as ni
    ni.ifaddresses(interface)
    ip = ni.ifaddresses(interface)[2][0]['addr']
    return ip

def runCommand(command,):
    #import time
    print "executing ", command
    #time.sleep(3)
    print "done running . . . "
    print "ending . . . "
    #thread.exit()
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
                #print "Executing the task : ", message

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
        #async_result = pool.apply_async(clientThread, (conn,addr))
        #return_val = async_result.get()

    conn.close()
    sock.close()

if __name__ == '__main__':
    main(sys.argv[1:])
