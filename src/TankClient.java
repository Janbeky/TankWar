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
 * @author ����
 * @version 2.4
 * @���ڣ�6��24
 * @����˵��������һ��̹�˴�ս��С��Ϸ��ʵ���˼��̼�������̹���ƶ�
 * ������ͨ�ӵ��������ӵ���Ѫ�����ҷ�̹�˸���з�̹������
 * �ӵ���ӵȹ��ܡ�
 * @���� W-A-S-D �ֱ����̹����-��-��-���ƶ���ͬʱҲ����ͨ������С���̵ķ�������ƣ�
 *      J������ͨ�ӵ�   K���䳬���ӵ�  F2������̹�˸���
 *    
 */

public class TankClient extends Frame {
	// ���ڳߴ糣��
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	// new���ҷ�̹��
	MyTank t1 = new MyTank(50, 50,Direction.STOP,true, this);
	//new������ǽ
	Wall w1 = new Wall(120,100,150,20,this);
	Wall w2 = new Wall(500,400,20,130,this);
	//new��Ѫ��
	Blood blood = new Blood();
    //���ӵ�������Ϊ��
	Bullet b = null;
    //����ʾ��ͼƬ����Ϊ��
	Image bhScreenImage = null;

	// �����ӵ�����
	List<Bullet> bullets = new ArrayList<Bullet>();
	//����̹�˱�ը����
	List<Explode> explodes = new ArrayList<Explode>();
	//����̹������
	List<MyTank> enemyTanks = new ArrayList<MyTank>();
	
	/**
	*  
	*@Description:�����е�̹�ˡ��ӵ�����ը������ 
	*/  
	public void paint(Graphics g) {

		if(enemyTanks.size() <= 0) {
			for(int i = 0; i<20; i++) {
				enemyTanks.add(new MyTank(50+40*(i+1), 300, Direction.D,false,this));
			}
		}
		//�ڴ��ڵ����Ͻ���������
		g.drawString("�ӵ�������:" + bullets.size(), WINDOW_WIDTH-100, WINDOW_HEIGHT-530);
		g.drawString("̹�˱�ը��:"+explodes.size(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-550);
		g.drawString("�з�̹��ʣ:"+enemyTanks.size(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-510);
		g.drawString("̹������ֵ:"+t1.getLife(),  WINDOW_WIDTH-100, WINDOW_HEIGHT-490);
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.draw(g);
			bu.hitTanks(enemyTanks);
			bu.hitTank(t1);
			bu.hitWall(w1);
			bu.hitWall(w2);
			
		}
		
		//����ÿһ����������ı�ը�����������
		for(int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		//����ÿ���з�̹�ˣ������ݸ�enemy's
		for(int i = 0; i < enemyTanks.size(); i++) {
			MyTank enemys =  enemyTanks.get(i);
			
			//���ú�ǽ��ײ�ķ���������ǽ���ݽ���
			enemys.impactWithWall(w1);
			enemys.impactWithWall(w2);
			//���õз�̹����ײ�ķ���
			enemys.impactWithTanks(enemyTanks);
			//�ѵз�̹�˻�����
			enemys.paint(g);
		}
		
		// �����ҷ�̹��
		t1.paint(g);
		//���ó�Ѫ��ķ���
		t1.eatBlood(blood);
		//��������ǽ
		w1.paint(g);
		w2.paint(g);
		//����Ѫ��
		blood.paint(g);

	}
	
	// DoubleBuffer˫����
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
	
	
    //��������
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
	
	//main����
	public static void main(String[] args) {
		new TankClient().lauchFrame();	
	}
	
	private class repThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(30);
					// System.out.println("�߳�ִ����");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


    //���̼��������ƶ�
	private class KeyControl extends KeyAdapter {

		public void keyReleased(KeyEvent k) {

			t1.keyReleased(k);
		}

		public void keyPressed(KeyEvent k) {
			t1.keyPressed(k);
		}
	}
}