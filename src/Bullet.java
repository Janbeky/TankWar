import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description:����������ӵ������Ժͷ���
 * 
 */
public class Bullet {
	//�����ӵ����ٶȺͳ�����
	public static final int XSPEED = 5; 
	public static final int YSPEED = 5; 
	public static final int WIDTH = 5; 
	public static final int HEIGHT = 5; 
	//�ӵ�������
	int x,y;
	//���������
	Direction dir;
	//�ӵ��ĺû�
	private boolean good;
	//�����ӵ��Ƿ���ţ�Ĭ��Ϊtrue
	private boolean live = true;
	//TankClient��ܼ�
	private TankClient tc;
	//��ȡϵͳĬ�ϵĹ��߰�
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//����һ����̬���Ҽ�ΪString���ͣ�ֵΪImage���͵�����
	private static Map<String,Image> bude = new HashMap<String,Image>();
	//����һ����̬��ͼƬ����
	private static Image[] imgs = null;
	//��ͼƬװ������
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
		//����Ϊ����,ֵΪ�ӵ�ͼƬ���������
		bude.put("L", imgs[0]);
		bude.put("LU", imgs[1]);
		bude.put("U", imgs[2]);
		bude.put("RU", imgs[3]);
		bude.put("R", imgs[4]);
		bude.put("RD", imgs[5]);
		bude.put("D", imgs[6]);
		bude.put("LD", imgs[7]);	             
	}
	
	//�ӵ��Ĺ���������ʼ���ӵ�������ͷ���
	public Bullet(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	//�ӵ��Ĺ�������һ������ʼ���ӵ�������ͷ��򣬺�TankClient��ܼ�
	public Bullet(int x, int y,boolean good, Direction dir, TankClient tc) {
		this(x,y,dir);
		this.tc = tc;
		this.good = good;
	}
	
	/**
	 * @Description:�ӵ��Ƿ����
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * @Description:���ӵ�����
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
	 * @Description:�ӵ��ƶ�
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
	 * @Description:�����ӵ���ʵ��
	 * @param Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
		
	}
	
	//�ӵ�����̹��
	public boolean hitTank(MyTank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
			if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) t.setLive(false);
				
			}else {
				t.setLive(false);
			}

			live = false;
			//newһ���µı�ը��λ��Ϊ�ӵ���x��y
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
		
		
	}
	
	//����������������ж��ӵ��Ƿ����̹��
	public boolean hitTanks(List<MyTank> enemyTanks) {
		for(int i = 0; i<enemyTanks.size(); i++ ) {
			if(hitTank(enemyTanks.get(i))) {
				return true;
			}
		}
		return false;
		
	}
	
	//����������������ж��ӵ��Ƿ������ǽ������������
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
			
		}
		return false;
	}
}
