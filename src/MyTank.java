import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 陈振东
 *这个类的作用是画出坦克，并控制坦克移动
 */

public class MyTank {
	//设置往X和Y坐标移动的速度
	public static final int XSPEED = 3;
	public static final int YSPEED = 3;
	//设置坦克的宽度和高度
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	//设置初始生命
	private int life = 100;
    //设置x和y的坐标
	private int x,y;	
	//设置是否活着的属性
	private boolean live = true;
    //设置前一次移动的x和y的坐标
	private int oldX,oldY;
	//获取随机数
	private Random rn = new Random();
	//new一个血块
	private BloodStrip b = new BloodStrip();
	//设置一个静态的计数器
	private static int step = 0;
    //设置布尔类型的类型来确定坦克是好是坏
	private boolean good;
	//设置布尔类型确认摁了什么方向
	private boolean L , U , R , D = false;
	//将方向中的停止赋值给d
	private Direction d = Direction.STOP;
	//将方向中的向下按钮赋值给ptd
	private Direction ptd = Direction.D;
	//设置一个TankClient的大管家
	TankClient tc;
	//获取系统工具包
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//创建一个Image的数组
	private static Image[] imgs = null;
	//创建一个静态的容器
	private static Map<String,Image> m = new HashMap<String,Image>();
	//将图片存储到数组中
	static{ 
		imgs = new Image[]{
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(MyTank.class.getClassLoader().getResource("images/tankLD.gif"))
	};
		//以方向为键，图片为值存储到容器中
		m.put("L",  imgs[0]);
		m.put("LU", imgs[1]);
		m.put("U",  imgs[2]);
		m.put("RU", imgs[3]);
		m.put("R",  imgs[4]);
		m.put("RD", imgs[5]);
		m.put("D",  imgs[6]);
		m.put("LD", imgs[7]);
}
	//创建坦克构造器，初始化坐标x，y，好坏属性，并将坐标赋值给老坐标，
	public MyTank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.oldX = x;
		this.oldY = y;
	}
	//创建另一个坦克构造器，初始化坐标x，y，方向d，好坏，以及大管家tc
	public MyTank(int x,int y, Direction d,boolean good,TankClient tc) {
		this(x,y,good);
		this.tc = tc;
		this.d = d;
	}
	
	/**
	*  
	*@Description:坦克返回上次移动的位置 
	*/  
	private void stay() {
		x = oldX;
		y = oldY;
	}
	
	/**
	*  
	*@Description:设置坦克移动 
	*/  
	public void move() {
		this.oldX = x;
		this.oldY = y;
		switch(d) {
		case L:
			x-=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		case STOP:
			break;
		}
		if(d != Direction.STOP) {
			this.ptd = this.d;
		}
		
	    //坦克移动到边界时的处理
		if(x < 0) {x = 0;}
		if(y < 30) {y = 30;}
		if(x + MyTank.WIDTH > TankClient.WINDOW_WIDTH) x = TankClient.WINDOW_WIDTH - MyTank.WIDTH-5;  
		if(y + MyTank.HEIGHT > TankClient.WINDOW_HEIGHT) y = TankClient.WINDOW_HEIGHT - MyTank.HEIGHT-5;  
        
		//当是坏坦克时，将会任意移动
		if(!good) {
			if(step == 0) {
				step = rn.nextInt(13) +3 ;
				Direction[] dirs = Direction.values();
				int dn = rn.nextInt(dirs.length);
				d = dirs[dn];
			}
			step--;
			//如果随机数大于38，则调用方法开火
			if(rn.nextInt(40)>38)
			fire();
		}
	}
	
	/**
	 * 
	 * @Description 画出坦克
	 * @param g
	 */
	public void paint(Graphics g) {
		if(!live) {
			if(!good) {
				tc.enemyTanks.remove(this);
			}
			return;
		}
		if(isGood()) {
			b.draw(g);
		}
		
        //按照按键方向画出每个坦克的图像
		switch (ptd) {
		case L:
			g.drawImage(m.get("L"),x,y,null);
			break;
		case LU:
			g.drawImage(m.get("LU"),x,y,null);
			break;
		case U:
			g.drawImage(m.get("U"),x,y,null);
			break;
		case R:
			g.drawImage(m.get("R"),x,y,null);
			break;
		case RU:
			g.drawImage(m.get("RU"),x,y,null);
			break;
		case D:
			g.drawImage(m.get("D"),x,y,null);
			break;
		case RD:
			g.drawImage(m.get("RD"),x,y,null);
			break;
		case LD:
			g.drawImage(m.get("LD"),x,y,null);
			break;
		}
		move();
	}
	
	/**
	 * 
	 * @Description 键盘松开时执行操作
	 * @param k
	 */
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		
		switch(key) {
			
		case KeyEvent.VK_LEFT:
		   L = true;
		    break;
		
		case KeyEvent.VK_UP:
			U = true;
		    break;
		
		case KeyEvent.VK_RIGHT:
			R = true;
			break;

		case KeyEvent.VK_DOWN:
			D = true;
			break;
			
		case KeyEvent.VK_A:
			L = true;
			break;	
			
		case KeyEvent.VK_W:
			U = true;
			break;	
			
		case KeyEvent.VK_D:
			R = true;
			break;	
			
		case KeyEvent.VK_S:
			D = true;
			break;		
		}
		LocationDierction();
	}
	
	//判断键盘摁下去的键
	void LocationDierction() {
		if(L && !U && !R && !D) d = Direction.L;
		else if(L && U && !R && !D) d = Direction.LU;
		else if(!L && U && !R && !D) d = Direction.U;
		else if(!L && U && R && !D) d = Direction.RU;
		else if(!L && !U && R && !D) d = Direction.R;
		else if(!L && !U && R && D) d = Direction.RD;
		else if(!L && !U && !R && D) d = Direction.D;
		else if(L && !U && !R && D) d = Direction.LD;
		else if(!L && !U && !R && !D) d = Direction.STOP;
	}
     
	/**
	 * @Description 键盘摁下时执行操作
	 * @param k
	 */
	public void keyReleased(KeyEvent k) {
        int key = k.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_F2:
			if(!this.live) {this.live = true;this.life = 100;}	
			break;
			
		case KeyEvent.VK_LEFT:
		   L = false;
		    break;
		
		case KeyEvent.VK_UP:
			U = false;
		    break;
		
		case KeyEvent.VK_RIGHT:
			R = false;
			break;

		case KeyEvent.VK_DOWN:
			D = false;
			break;
			
		case KeyEvent.VK_A:
			L = false;
			break;	
			
		case KeyEvent.VK_W:
			U = false;
			break;	
			
		case KeyEvent.VK_D:
			R = false;
			break;	
			
		case KeyEvent.VK_S:
			D = false;
			break;	
			
		case KeyEvent.VK_K:
			this.superFire();
			break;
			
		case KeyEvent.VK_J:
			fire();
			break;
		}
		LocationDierction();
		
	}
	
	/**
	 * 
	 * @Description 发射子弹
	 * @return b
	 */
	public Bullet fire() {
		if(!live)
			return null;
		int x = this.x+MyTank.WIDTH/2-Bullet.WIDTH/2;
		int y = this.y+MyTank.HEIGHT/2-Bullet.HEIGHT/2;
		Bullet b = new Bullet(x, y,good, ptd,this.tc);
		tc.bullets.add(b);
		return b;
	}
	
	/**
	 * 
	 * @Description 按照坦克的方向发射子弹
	 * @return b
	 */
	public Bullet fire(Direction d) {
		if(!live)
			return null;
		int x = this.x+MyTank.WIDTH/2-Bullet.WIDTH/2;
		int y = this.y+MyTank.HEIGHT/2-Bullet.HEIGHT/2;
		Bullet b = new Bullet(x, y,good, d,this.tc);
		tc.bullets.add(b);
		return b;
	}
	
	/**
	 * 
	 * @Description 超级子弹，朝各个方向发射子弹
	 */
	public void superFire() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++) {
			fire(dirs[i]);	
		}
	}
	
	//获取坦克的矩形
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH,HEIGHT);
		
	}
	
	//返回坦克是否活着
	public boolean isLive() {
		return live;
	}
	
    //返回坦克好坏
	public boolean isGood() {
		return good;
	}
    
	//设置坦克生死
	public void setLive(boolean live) {
		this.live = live;
	}
	
	//返回坦克生命
	public int getLife() {
		return life;
	}

	//设置坦克生命值
	public void setLife(int life) {
		this.life = life;
	}
	
	/**
	 * 
	 * @Description 判断坦克是否和墙相交
	 * @param w
	 */
	public boolean impactWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			//如果坦克和墙相较，则返回上一次的位置
			stay();
			return true;
		}
		return false;
	}
	
	/**
	 * @Description 判断坦克是和坦克相交，如果相交则都返回上一次的位置
	 * @param tanks
	 */
	public boolean impactWithTanks(java.util.List<MyTank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			MyTank t = tanks.get(i);
			if(this != t) {
				if(this.live &&t.isLive()&& this.getRect().intersects(t.getRect())) {
					
					this.stay();
					t.stay();
					return true;
			    }			
			}	
		}
		return false;
	}
	
	//这个内部类画出了血条的剩余量
	private class BloodStrip {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			int w = WIDTH * life/100;
			g.drawRect(x, y-10, WIDTH, 10);
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}	
	}
	
	/**
	 * @Description 吃血块，如果我方坦克可血块相交，则血恢复满，血块消失
	 * @param tanks
	 */
	public boolean eatBlood(Blood b) {
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;	
	}
}
