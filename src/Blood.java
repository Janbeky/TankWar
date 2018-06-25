import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author ����
 * �������Ѫ���࣬������һ��Ѫ�����ҷ�̹�˳Ե�Ѫ��ʱ
 * ����Ѫ��Ѫ����ʧ
 */

//����Ѫ���࣬�����x��yλ�����꣬��w�͸�h����
public class Blood {
	private int x ,y, w, h;
	//���Ѫ�����ŵĲ���ֵ
	private boolean live = true;
    //�����TankClient��ܼ�
	TankClient tc;
	//�����˶�ά����ĳ�ʼλ��
	int step = 0;
	//Ѫ���ƶ��ķ��������
	private int[] [] pos= {{531,234},{531,234},{531,238},{531,241},{531,244},
			               {531,247},{531,249},{531,251},{531,251},{531,251},
			               {526,249},{520,251},{515,245},{510,245},{508,253},
                           {502,258},{498,261},{495,265},{490,269},{485,274},
                           {481,275},{481,280},{481,285},{472,290},{468,283}};
	//Ѫ���Ĺ���������͸����ó�15
	public Blood(){
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
	}
	
	//���������ȡ�˻��ʣ���Ѫ�����˳���
	public void paint(Graphics g) {
		//���Ѫ�����ԣ��򲻻�
		if(!live) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.PINK);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		move();
	}
    
	//������������Ľ�Ѫ��λ���ƶ�
	public void move() {
		step++;
		//����������õ����һ�����꣬��ӵ�һ��������ƶ�
		if(step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	//����Ѫ���Ƿ�Ļ��ŵķ���
	public boolean isLive() {
		return live;
	}
	
    //����Ѫ��������
	public void setLive(boolean live) {
		this.live = live;
	}
	
    //����Ѫ��������λ�úʹ�С
	public Rectangle getRect() {
		
		return new Rectangle(x,y,w,h);
	}


}
