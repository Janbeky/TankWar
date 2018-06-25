import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
*  
*@Description:���������˱�ը���ֵ�λ��������
*�����ر�ըͼƬ
*
*/  
public class Explode {
	//��ը��x��y����
	int x, y;
	//�ò������������Ʊ�ը������
	private boolean live = true;
	//�þ�̬�Ĳ������������Ʊ�ը�ļ���
	private static boolean loading = false;
	//����һ��TankClient�Ĵ�ܼң�ͬʱҲ�ǳ�������
	private TankClient tc;
	//��ȡϵͳ��Ĭ�Ϲ��߰�
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//����ը��ͼƬ��ӽ�Image������
	private static Image[] imgs = { 
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif")),
	};
	//����һ����ʼ������
	int step = 0;
    
	//��ը�Ĺ���������ʼ���˱�ը��x��y��λ�ã���ʼ���˴�ܼ�
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	/**
	 * @Description ������ը
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!loading) {
			for(int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
		}
		
		//�жϱ�ը����
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		//�жϱ�ըͼƬʮ�ּ�����ɣ���������꣬���������ó�false
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		
		//������ը
		g.drawImage(imgs[step], x, y, null);
		step++;
	}

}
