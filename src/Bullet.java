import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description:这个类中有子弹的属性和方法
 * 
 */
public class Bullet {
	//定义子弹的速度和长、宽
	public static final int XSPEED = 5; 
	public static final int YSPEED = 5; 
	public static final int WIDTH = 5; 
	public static final int HEIGHT = 5; 
	//子弹的坐标
	int x,y;
	//方向的引用
	Direction dir;
	//子弹的好坏
	private boolean good;
	//定义子弹是否活着，默认为true
	private boolean live = true;
	//TankClient大管家
	private TankClient tc;
	//获取系统默认的工具包
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//创建一个静态，且键为String类型，值为Image类型的容器
	private static Map<String,Image> bude = new HashMap<String,Image>();
	//创建一个静态的图片数组
	private static Image[] imgs = null;
	//将图片装入数组
	static{
		imgs = new Image[] {
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileD.gif")),
			    tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLD.gif"))	
		};
		//将键为方向,值为子弹图片添加至容器
		bude.put("L", imgs[0]);
		bude.put("LU", imgs[1]);
		bude.put("U", imgs[2]);
		bude.put("RU", imgs[3]);
		bude.put("R", imgs[4]);
		bude.put("RD", imgs[5]);
		bude.put("D", imgs[6]);
		bude.put("LD", imgs[7]);	             
	}
	
	//子弹的构造器，初始化子弹的坐标和方向
	public Bullet(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	//子弹的构造器另一个，初始化子弹的坐标和方向，和TankClient大管家
	public Bullet(int x, int y,boolean good, Direction dir, TankClient tc) {
		this(x,y,dir);
		this.tc = tc;
		this.good = good;
	}
	
	/**
	 * @Description:子弹是否活着
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * @Description:将子弹画出
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.bullets.remove(this);
			return;
		}
		switch (dir) {
		case L:
			g.drawImage(bude.get("L"),x,y,null);
			break;
		case LU:
			g.drawImage(bude.get("LU"),x,y,null);
			break;
		case U:
			g.drawImage(bude.get("U"),x,y,null);
			break;
		case R:
			g.drawImage(bude.get("R"),x,y,null);
			break;
		case RU:
			g.drawImage(bude.get("RU"),x,y,null);
			break;
		case D:
			g.drawImage(bude.get("D"),x,y,null);
			break;
		case RD:
			g.drawImage(bude.get("RD"),x,y,null);
			break;
		case LD:
			g.drawImage(bude.get("LD"),x,y,null);
			break;
		}
		move();
			
	}

	/**
	 * @Description:子弹移动
	 */
	public void move() {
		switch(dir) {
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
		}	
		if(x  <0 || y < 0 || x > TankClient.WINDOW_WIDTH||y > TankClient.WINDOW_HEIGHT) {
			live = false;
			tc.bullets.remove(this);
			
		}
	}
	
	/**
	 * @Description:返回子弹的实例
	 * @param Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
		
	}
	
	//子弹攻击坦克
	public boolean hitTank(MyTank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
			if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) t.setLive(false);
				
			}else {
				t.setLive(false);
			}

			live = false;
			//new一个新的爆炸，位置为子弹的x，y
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
		
		
	}
	
	//这个方法的作用是判断子弹是否击中坦克
	public boolean hitTanks(List<MyTank> enemyTanks) {
		for(int i = 0; i<enemyTanks.size(); i++ ) {
			if(hitTank(enemyTanks.get(i))) {
				return true;
			}
		}
		return false;
		
	}
	
	//这个方法的作用是判断子弹是否击中了墙，击中则死亡
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
			
		}
		return false;
	}
}
