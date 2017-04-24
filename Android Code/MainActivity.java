package com.example.root.directev3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Socket socket2ev3 = null;
    private Button forwardButton;
    private Button backwardButton;
    private Button leftwardButton;
    private Button rightwardButton;
    private EditText passwordField;
    private EditText timeField;

    private String directionPlusTime = null;
    private String fullMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn2EV3();
        firing();
    }

    public void conn2EV3(){

        new Thread(){
            @Override
            public void run() {

                try{

                    socket2ev3 = new Socket("192.168.10.100", 3000);

                }catch(Exception e){
                    System.out.println(e);
                }

            }
        }.start();
    }

    public void firing(){

        forwardButton       = (Button) findViewById(R.id.fwdBtn);
        backwardButton      = (Button) findViewById(R.id.backBtn);
        leftwardButton      = (Button) findViewById(R.id.leftBtn);
        rightwardButton     = (Button) findViewById(R.id.btnRight);
        passwordField       = (EditText) findViewById(R.id.passwordHolder);
        timeField           = (EditText) findViewById(R.id.timeHolder);

        forwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "F";
                        fireData(direction, password, time);
                    }
                }
        );

        backwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "B";
                        fireData(direction, password, time);
                    }
                }
        );

        leftwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "L";
                        fireData(direction, password, time);
                    }
                }
        );

        rightwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "R";
                        fireData(direction, password, time);
                    }
                }
        );

    }

    public void fireData(String x, String y, int z){

        BufferedWriter bw = null;
        String movement = x;
        String password  = y;
        int time = z * 1000;
        String Stringtime = Integer.toString(time);

        directionPlusTime = movement.concat(Stringtime);
        fullMessage = password.concat(directionPlusTime);

        try{

            bw = new BufferedWriter(new OutputStreamWriter(socket2ev3.getOutputStream()));
            bw.write(fullMessage);
            bw.newLine();
            bw.flush();

        }catch (Exception e){
            System.out.println(e);
        }


    }


}
