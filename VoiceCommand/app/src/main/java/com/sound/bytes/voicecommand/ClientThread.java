package com.sound.bytes.voicecommand;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static String getop () throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        String line;
        br.readLine().toString();


        System.out.println(br.readLine().toString());

        return (br.readLine().toString());
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

            socket = new Socket(serverAddr, SERVERPORT);

            //Get reply back from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Assign it to a variable
            String serverResponse = in.readLine();

            //Print the response
            System.out.println(serverResponse);




        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}