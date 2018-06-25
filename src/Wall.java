import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是墙类，里面有墙的坐标和高度宽度
 */
public class Wall extends Frame{
	//墙的位置和高度宽度
	public int x,y,w,h;
	//TankClient大管家
	private TankClient tc;
	
    //墙的构造方法
	public Wall(int x,int y,int w,int h,TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * 
	 * @Description:画出墙体
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
	 * @Description:返回墙的实例
	 * @param Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	
	}
}
