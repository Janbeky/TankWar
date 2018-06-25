import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ����
 *�����������ǻ���̹�ˣ�������̹���ƶ�
 */

public class MyTank {
	//������X��Y�����ƶ����ٶ�
	public static final int XSPEED = 3;
	public static final int YSPEED = 3;
	//����̹�˵Ŀ�Ⱥ͸߶�
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	//���ó�ʼ����
	private int life = 100;
    //����x��y������
	private int x,y;	
	//�����Ƿ���ŵ�����
	private boolean live = true;
    //����ǰһ���ƶ���x��y������
	private int oldX,oldY;
	//��ȡ�����
	private Random rn = new Random();
	//newһ��Ѫ��
	private BloodStrip b = new BloodStrip();
	//����һ����̬�ļ�����
	private static int step = 0;
    //���ò������͵�������ȷ��̹���Ǻ��ǻ�
	private boolean good;
	//���ò�������ȷ������ʲô����
	private boolean L , U , R , D = false;
	//�������е�ֹͣ��ֵ��d
	private Direction d = Direction.STOP;
	//�������е����°�ť��ֵ��ptd
	private Direction ptd = Direction.D;
	//����һ��TankClient�Ĵ�ܼ�
	TankClient tc;
	//��ȡϵͳ���߰�
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//����һ��Image������
	private static Image[] imgs = null;
	//����һ����̬������
	private static Map<String,Image> m = new HashMap<String,Image>();
	//��ͼƬ�洢��������
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
		//�Է���Ϊ����ͼƬΪֵ�洢��������
		m.put("L",  imgs[0]);
		m.put("LU", imgs[1]);
		m.put("U",  imgs[2]);
		m.put("RU", imgs[3]);
		m.put("R",  imgs[4]);
		m.put("RD", imgs[5]);
		m.put("D",  imgs[6]);
		m.put("LD", imgs[7]);
}
	//����̹�˹���������ʼ������x��y���û����ԣ��������긳ֵ�������꣬
	public MyTank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.oldX = x;
		this.oldY = y;
	}
	//������һ��̹�˹���������ʼ������x��y������d���û����Լ���ܼ�tc
	public MyTank(int x,int y, Direction d,boolean good,TankClient tc) {
		this(x,y,good);
		this.tc = tc;
		this.d = d;
	}
	
	/**
	*  
	*@Description:̹�˷����ϴ��ƶ���λ�� 
	*/  
	private void stay() {
		x = oldX;
		y = oldY;
	}
	
	/**
	*  
	*@Description:����̹���ƶ� 
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
		
	    //̹���ƶ����߽�ʱ�Ĵ���
		if(x < 0) {x = 0;}
		if(y < 30) {y = 30;}
		if(x + MyTank.WIDTH > TankClient.WINDOW_WIDTH) x = TankClient.WINDOW_WIDTH - MyTank.WIDTH-5;  
		if(y + MyTank.HEIGHT > TankClient.WINDOW_HEIGHT) y = TankClient.WINDOW_HEIGHT - MyTank.HEIGHT-5;  
        
		//���ǻ�̹��ʱ�����������ƶ�
		if(!good) {
			if(step == 0) {
				step = rn.nextInt(13) +3 ;
				Direction[] dirs = Direction.values();
				int dn = rn.nextInt(dirs.length);
				d = dirs[dn];
			}
			step--;
			//������������38������÷�������
			if(rn.nextInt(40)>38)
			fire();
		}
	}
	
	/**
	 * 
	 * @Description ����̹��
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
		
        //���հ������򻭳�ÿ��̹�˵�ͼ��
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
	 * @Description �����ɿ�ʱִ�в���
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
	
	//�жϼ�������ȥ�ļ�
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
	 * @Description ��������ʱִ�в���
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
	 * @Description �����ӵ�
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
	 * @Description ����̹�˵ķ������ӵ�
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
	 * @Description �����ӵ����������������ӵ�
	 */
	public void superFire() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++) {
			fire(dirs[i]);	
		}
	}
	
	//��ȡ̹�˵ľ���
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH,HEIGHT);
		
	}
	
	//����̹���Ƿ����
	public boolean isLive() {
		return live;
	}
	
    //����̹�˺û�
	public boolean isGood() {
		return good;
	}
    
	//����̹������
	public void setLive(boolean live) {
		this.live = live;
	}
	
	//����̹������
	public int getLife() {
		return life;
	}

	//����̹������ֵ
	public void setLife(int life) {
		this.life = life;
	}
	
	/**
	 * 
	 * @Description �ж�̹���Ƿ��ǽ�ཻ
	 * @param w
	 */
	public boolean impactWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			//���̹�˺�ǽ��ϣ��򷵻���һ�ε�λ��
			stay();
			return true;
		}
		return false;
	}
	
	/**
	 * @Description �ж�̹���Ǻ�̹���ཻ������ཻ�򶼷�����һ�ε�λ��
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
	
	//����ڲ��໭����Ѫ����ʣ����
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
	 * @Description ��Ѫ�飬����ҷ�̹�˿�Ѫ���ཻ����Ѫ�ָ�����Ѫ����ʧ
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
