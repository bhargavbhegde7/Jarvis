package com.sound.bytes.voicecommand;

import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by goodbytes on 5/23/2016.
 */
public class ClientThread implements Runnable {

    private static int SERVERPORT;
    private static String SERVER_IP;
    private static Socket socket;

    /*public static int getSERVERPORT() {
        return SERVERPORT;
    }*/

    public static void setSERVERPORT(int SERVERPORT) {
        ClientThread.SERVERPORT = SERVERPORT;
    }

    /*public static String getServerIp() {
        return SERVER_IP;
    }*/

    public static void setServerIp(String serverIp) {
        SERVER_IP = serverIp;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void endConnection(){
        try {
            socket.close();
        }catch(Exception e){
            System.out.println("Exception in closing the socket : "+e.getMessage());
        }
    }

    /*public static void setSocket(Socket socket) {
        ClientThread.socket = socket;
    }*/

    @Override
    public void run() {

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            this.socket = new Socket(serverAddr, SERVERPORT);

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}