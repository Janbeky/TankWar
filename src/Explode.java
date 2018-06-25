import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
*  
*@Description:这个类控制了爆炸出现的位置了条件
*并加载爆炸图片
*
*/  
public class Explode {
	//爆炸的x，y坐标
	int x, y;
	//用布尔类型来控制爆炸的生命
	private boolean live = true;
	//用静态的布尔类型来控制爆炸的加载
	private static boolean loading = false;
	//设置一个TankClient的大管家，同时也是程序的入口
	private TankClient tc;
	//获取系统的默认工具包
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//将爆炸的图片添加进Image数组中
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
	//设置一个开始计数器
	int step = 0;
    
	//爆炸的构造器，初始化了爆炸的x，y的位置，初始化了大管家
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	/**
	 * @Description 画出爆炸
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!loading) {
			for(int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
		}
		
		//判断爆炸生死
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		//判断爆炸图片十分加载完成，如果加载完，则生命设置成false
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		
		//画出爆炸
		g.drawImage(imgs[step], x, y, null);
		step++;
	}

}
