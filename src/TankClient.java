import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 
 * @author 陈振东
 * @version 2.4
 * @日期：6月24
 * @内容说明：这是一个坦克大战的小游戏，实现了键盘监听，让坦克移动
 * 发射普通子弹、超级子弹、血包、我方坦克复活、敌方坦克死亡
 * 子弹添加等功能。
 * @操作 W-A-S-D 分别控制坦克上-左-下-右移动（同时也可以通过电脑小键盘的方向键控制）
 *      J发射普通子弹   K发射超级子弹  F2可以让坦克复活
 *    
 */

public class TankClient extends Frame {
	// 窗口尺寸常量
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	// new出我方坦克
	MyTank t1 = new MyTank(50, 50,Direction.STOP,true, this);
	//new出两块墙
	Wall w1 = new Wall(120,100,150,20,this);
	Wall w2 = new Wall(500,400,20,130,this);
	//new出血块
	Blood blood = new Blood();
    //将子弹的引用为空
	Bullet b = null;
    //将显示的图片引用为空
	Image bhScreenImage = null;

	// 创建子弹容器
	List<Bullet> bullets = new ArrayList<Bullet>();
	//创建坦克爆炸容器
	List<Explode> explodes = new ArrayList<Explode>();
	//创建坦克容器
	List<MyTank> enemyTanks = new ArrayList<MyTank>();
	
	/**
	*  
	*@Description:将所有的坦克、子弹、爆炸画出来 
	*/  
	public void paint(Graphics g) {

		if(enemyTanks.size() <= 0) {
			for(int i = 0; i<20; i++) {
				enemyTanks.add(new MyTank(50+40*(i+1), 300, Direction.D,false,this));
			}
		}
		//在窗口的右上角以下内容
		g.drawString("子弹发射中:" + bullets.size(), WINDOW_WIDTH-100, WINDOW_HEIGHT-530);
		g.drawString("坦克爆炸中:"+explodes.size(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-550);
		g.drawString("敌方坦克剩:"+enemyTanks.size(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-510);
		g.drawString("坦克生命值:"+t1.getLife(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-490);
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.draw(g);
			bu.hitTanks(enemyTanks);
			bu.hitTank(t1);
			bu.hitWall(w1);
			bu.hitWall(w2);
			
		}
		
		//遍历每一个加入数组的爆炸并逐个画出来
		for(int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		//遍历每个敌方坦克，并传递给enemy's
		for(int i = 0; i < enemyTanks.size(); i++) {
			MyTank enemys =  enemyTanks.get(i);
			
			//调用和墙相撞的方法将两个墙传递进入
			enemys.impactWithWall(w1);
			enemys.impactWithWall(w2);
			//调用敌方坦克相撞的方法
			enemys.impactWithTanks(enemyTanks);
			//把敌方坦克画出来
			enemys.paint(g);
		}
		
		// 画出我方坦克
		t1.paint(g);
		//调用吃血块的方法
		t1.eatBlood(blood);
		//画出两块墙
		w1.paint(g);
		w2.paint(g);
		//画出血块
		blood.paint(g);

	}
	
	// DoubleBuffer双缓冲
	public void update(Graphics g) {
		if (bhScreenImage == null) {
			bhScreenImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
		}
		Graphics gbhScreen = bhScreenImage.getGraphics();
		Color c = gbhScreen.getColor();
		gbhScreen.setColor(Color.LIGHT_GRAY);
		gbhScreen.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		gbhScreen.setColor(c);
		paint(gbhScreen);
		g.drawImage(bhScreenImage, 0, 0, null);
	}
	
	
    //窗口设置
	public void lauchFrame() {
	    Properties prop = new Properties();
	    try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("config/tanks.properties"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		int tankNum = Integer.parseInt(prop.getProperty("tankRefreshNum"));
		for(int i = 0; i < tankNum; i++) {
			enemyTanks.add(new MyTank(50+40*(i+1), 300, Direction.D,false,this));
		}
		
		this.setLocation(MAXIMIZED_BOTH, WIDTH);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyControl());
		this.setVisible(true);
		new Thread(new repThread()).start();
	}
	
	//main方法
	public static void main(String[] args) {
		new TankClient().lauchFrame();	
	}
	
	private class repThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(30);
					// System.out.println("线程执行了");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


    //键盘监听控制移动
	private class KeyControl extends KeyAdapter {

		public void keyReleased(KeyEvent k) {

			t1.keyReleased(k);
		}

		public void keyPressed(KeyEvent k) {
			t1.keyPressed(k);
		}
	}
}