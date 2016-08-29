import java.awt.Graphics;
import java.applet.Applet;
public class HelloApplet extends Applet
{
	public void paint(Graphics g)
	{
		g.drawString("Hello World!",100,250);
	}
	public static void main(String args[])
	{
	}
}