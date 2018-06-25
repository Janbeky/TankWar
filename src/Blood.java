import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author 陈振东
 * 这个类是血包类，定义了一个血包，我方坦克吃到血包时
 * 补满血，血包消失
 */

//创建血包类，并添加x，y位置坐标，宽w和高h属性
public class Blood {
	private int x ,y, w, h;
	//添加血包活着的布尔值
	private boolean live = true;
    //添加了TankClient大管家
	TankClient tc;
	//设置了二维数组的初始位置
	int step = 0;
	//血包移动的方向的坐标
	private int[] [] pos= {{531,234},{531,234},{531,238},{531,241},{531,244},
			               {531,247},{531,249},{531,251},{531,251},{531,251},
			               {526,249},{520,251},{515,245},{510,245},{508,253},
                           {502,258},{498,261},{495,265},{490,269},{485,274},
                           {481,275},{481,280},{481,285},{472,290},{468,283}};
	//血包的构造器，宽和高设置成15
	public Blood(){
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
	}
	
	//这个方法获取了画笔，将血包画了出来
	public void paint(Graphics g) {
		//如果血包被吃，则不画
		if(!live) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.PINK);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		move();
	}
    
	//这个方法持续的将血包位置移动
	public void move() {
		step++;
		//如果到了设置的最后一个坐标，则从第一个点继续移动
		if(step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	//返回血包是否的活着的方法
	public boolean isLive() {
		return live;
	}
	
    //设置血包的生死
	public void setLive(boolean live) {
		this.live = live;
	}
	
    //返回血包的外框的位置和大小
	public Rectangle getRect() {
		
		return new Rectangle(x,y,w,h);
	}


}
