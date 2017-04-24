import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Test
{
  public static void main(String[] args)
  {
    try
    {
      ServerSocket server = new ServerSocket(3000);
      System.out.println("Started");
      
      Socket s = server.accept();
      
      System.out.println("Connected, please provide the password to continue.");
      
      RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
      RegulatedMotor m2 = new EV3LargeRegulatedMotor(MotorPort.C);
      
      BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
      
      String data = null;
      int time = 0;
      String direction = null;
      String password = null;
      while (((data = br.readLine()) != null) && (!Button.ESCAPE.isDown()))
      {
        try
        {
          password = data.substring(0, 13);
          direction = data.substring(13, 14);
          time = Integer.parseInt(data.substring(14));
        }
        catch (Exception localException) {}
        if ((direction.equals("F")) && (password.equals("random123")))
        {
          m.forward();
          m2.forward();
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              Test.this.stop();
            }
          }.start();
          m2.stop();
        }
        else if ((direction.equals("B")) && (password.equals("random123")))
        {
          m.backward();
          m2.backward();
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              Test.this.stop();
            }
          }.start();
          m2.stop();
        }
        else if ((direction.equals("R")) && (password.equals("random123")))
        {
          m.forward();
          m2.backward();
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              Test.this.stop();
            }
          }.start();
          m2.stop();
        }
        else if ((direction.equals("L")) && (password.equals("random123")))
        {
          m.backward();
          m2.forward();
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              Test.this.stop();
            }
          }.start();
          m2.stop();
        }
        else
        {
          System.out.println("Failed!");
        }
        System.out.println(data);
      }
      System.out.println("Ended");
      Delay.msDelay(4000L);
    }
    catch (Exception localException1) {}
  }
}
