package GalacticCats;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class Moving extends ImageIcon {
	public int x;				// ����� ��ġ ��ǥ
	public int y;				// ����� ��ġ ��ǥ
	private int initX, initY; 	// �ʱ���� x, y��ǥ
	protected int xDirection;
	protected int yDirection;
	protected int xBoundary; //���ǥ��
	protected int yBoundary; //���ǥ�� 
	protected int steps;
	protected int margin;		// �� ����� ������ ���ԵǴ� ������ ��Ÿ���� ����
	URL img;
	URL attack_img;
	URL attacked_img;
	URL bullet;
	boolean bird=false;
	boolean boom=false;
	boolean item=false; 
	boolean isattacked;
	public Moving(URL imgURL,URL attack_img,URL attacked_img,URL bullet,int x, int y, int margin, int steps, int xBoundary, int yBoundary) {
		// imgPath : �׸� ������ ��θ�
		// x, y : �̹����� ���� ��ġ ��ǥ
		// margin : �� �̹����� ������ ��Ÿ���� ���� (�� �����ȿ� ������ �浹 �� ������ �Ǵ� �ϱ� ����) ->�̹��� ũ�� 
		// steps : �̹����� �����϶� �̵��ϴ� ��ǥ ����
		// xBoundary, yBoundary : �׸��� �̵��� �� �ִ� ��ǥ�� �ִ밪
		super (imgURL);
		this.attack_img=attack_img;
		this.attacked_img=attacked_img;
		this.bullet=bullet;
		this.x = x;
		this.initX = x;
		this.y = y;
		this.initY = y;
		this.margin = margin;
		this.xDirection = 1;
		this.yDirection = 1;
		this.steps = steps;
		this.xBoundary = xBoundary;
		this.yBoundary = yBoundary;
		isattacked=false;
	}
	//stone,ball,�� �̹��� �׸��� : (attack_img)���� �ֵ� 
	public Moving(URL imgURL,URL attacked,int margin,int xBoundary,int yBoundary) {
		super(imgURL);
		this.attacked_img=attacked;
		this.margin=margin;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		this.xDirection=1;
		this.yDirection=1;
		x= (int) (Math.random() * xBoundary/2)+xBoundary/2;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.initX=x;
		this.initY=y;
		this.steps=5;
		isattacked=false;
	}

	//ph����,�Ŀ�����,
	public Moving(URL imgURL,int margin) {
		super(imgURL);
		this.initX=x;
		this.initY=y;
		this.steps=5;
		this.xDirection = 1;
		this.yDirection = 1;
		this.xBoundary=1155-margin;
		this.yBoundary=688-margin;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.margin=margin;
		item=true;
		isattacked=false;
	}
	//���� 
	public Moving(URL imgURL, int margin,int x,int y) {
		this(imgURL,margin);
		this.x=x;
		this.y=y;

		isattacked=false;

	}
	// ���� ��ġ�� ������ ����Ʈ�� �ִ� ������ 
	//���� (attack�̹����ִ¾ֵ�)
	public Moving(URL imgURL,URL attacked_img,URL bullet,int margin, int steps, int xBoundary, int yBoundary) {
		super(imgURL);
		this.attack_img=attacked_img;
		this.margin=margin;
		this.bullet=bullet;
		this.steps=steps;
		this.xDirection=1;
		this.yDirection=1;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		x= (int) (Math.random() * xBoundary/2)+xBoundary/2;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.initX=x;
		this.initY=y;
		isattacked=false;

	}

	public boolean isAttacked() {
		return isattacked;
	}
	public void setAttacked(boolean isattacked) {
		this.isattacked=isattacked;
	}
	public void setBoom(boolean boom) {
		this.boom=boom;
	}
	public void setBird(boolean bird) {
		this.bird=bird;
	}
	public void setItem(boolean item) {
		this.item=item;
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public int getMargin() {
		return margin;
	}
	public URL getAttack_img() {
		return attack_img;
	}
	public URL getAttacked_img() {
		return attacked_img;
	}
	public URL getImg() {
		return img;
	}
	public void setImg(URL img) {
		this.img = img;
	}
	public URL getBullet() {
		return bullet;
	}
	// �ϳ��� ���� �� ���� �浹�Ͽ����� (����� margin �Ÿ��ȿ� �ִ���)�� �Ǵ��ϴ� �Լ�
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin) return true;
		return false;
	}

	public void reset() {
		x = initX; y= initY;
	}

	// �ش� ����� g�� ������ִ� �޼ҵ�
	public void draw(Graphics g, ImageObserver io) {
	((Graphics2D)g).drawImage(this.getImage(), x, y, margin, margin, io);
	}

	public void Horizontallymove() {
		if (xDirection > 0 && x >= xBoundary) {
			xDirection = -1;
			y += (yDirection * steps * 5);
		}
		if (xDirection < 0 && x <= 0) {
			xDirection = 1;
			y += (yDirection * steps * 5);
		}
		x -= (xDirection * steps);

		if (yDirection > 0 && y >= yBoundary) {
			yDirection = -1;
		}
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
		}
	}

	//�� 
	public void Diaonallymove() {

		if (xDirection > 0 && x >= xBoundary) {
			//xDirection = -1;
		}
		if (xDirection < 0 && x <= 0) {
			//xDirection = 1;
		}
		x += (xDirection * steps);

		if (yDirection > 0 && y >= yBoundary) {
			yDirection = -1;
		}
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
		}
		y += (yDirection * steps);
	}
}