import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

/**
 * ����ǽ�࣬������ǽ������͸߶ȿ��
 */
public class Wall extends Frame{
	//ǽ��λ�ú͸߶ȿ��
	public int x,y,w,h;
	//TankClient��ܼ�
	private TankClient tc;
	
    //ǽ�Ĺ��췽��
	public Wall(int x,int y,int w,int h,TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * 
	 * @Description:����ǽ��
	 * @param g
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(c.BLACK);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		}
	
	/**
	 * 
	 * @Description:����ǽ��ʵ��
	 * @param Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	
	}
}
