package GalacticCats;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class Moving extends ImageIcon {
	public int x;				// 모양의 위치 좌표
	public int y;				// 모양의 위치 좌표
	private int initX, initY; 	// 초기시작 x, y좌표
	protected int xDirection;
	protected int yDirection;
	protected int xBoundary; //경계표시
	protected int yBoundary; //경계표시 
	protected int steps;
	protected int margin;		// 이 모양의 영역이 포함되는 영역을 나타내기 위함
	URL img;
	URL attack_img;
	URL attacked_img;
	URL bullet;
	boolean bird=false;
	boolean boom=false;
	String item;
	String what;
	boolean invincible =false;
	boolean isdie =false; 

	public Moving(URL imgURL,URL attack_img,URL attacked_img,URL bullet,int x, int y, int margin, int steps, int xBoundary, int yBoundary) {
		// imgPath : 그림 파일의 경로명
		// x, y : 이미지의 시작 위치 좌표
		// margin : 이 이미지의 영역을 나타내는 범위 (이 영역안에 있으면 충돌 한 것으로 판단 하기 위함) ->이미지 크기 
		// steps : 이미지가 움직일때 이동하는 좌표 단위
		// xBoundary, yBoundary : 그림이 이동할 수 있는 좌표의 최대값
		super (imgURL);
		this.attack_img=attack_img;
		this.attacked_img=attacked_img;
		this.bullet=bullet;
		this.x = x;
		this.y = y;	
		this.initX = x;
		this.initY = y;
		this.margin = margin;
		this.xDirection = 1;
		this.yDirection = 1;
		this.steps = steps;
		this.xBoundary = xBoundary;
		this.yBoundary = yBoundary;
		img=imgURL;

	}
	//stone,ball,새 이미지 그리기 : (attack_img)없는 애들 
	public Moving(URL imgURL,URL attacked,int margin,int xBoundary,int yBoundary,String what) {
		super(imgURL);
		this.attacked_img=attacked;
		this.what = what;
		this.margin=margin;
		this.xBoundary=xBoundary;
		this.yBoundary=yBoundary;
		this.xDirection=1;
		this.yDirection=1;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2-400;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.initX=x;
		this.initY=y;
		this.steps=5;
		img=imgURL;

	}

	//ph포션,파워포션,
	public Moving(URL imgURL,int margin,String item) {
		super(imgURL);
		this.initX=x;
		this.initY=y;
		this.item=item;
		this.steps=5;
		this.xDirection = 1;
		this.yDirection = 1;
		this.xBoundary=1155-margin;
		this.yBoundary=688-margin;
		this.x= (int) (Math.random() * xBoundary/2)+xBoundary/2-200;
		this.y= (int) (Math.random() * yBoundary/2+100)+80;
		this.margin=margin;
		img=imgURL;

	}
	//코인 
	public Moving(URL imgURL, int margin,int x,int y) {
		this(imgURL,margin,"coin");
		this.x=x;
		this.y=y;
		img=imgURL;

	}
	// 시작 위치를 임의의 포인트로 주는 구성자 
	//적들 (attack이미지있는애들)
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
		img=imgURL;


	}

	public boolean outside() {
		if(this.x<0)
			return true;
		return false;
	}
	public String whatAttacker() {
		return what;
	}
	public String whatItem() {
		return item;
	}
	public void setBoom(boolean boom) {
		this.boom=boom;
	}
	public void setBird(boolean bird) {
		this.bird=bird;
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
	public URL getBullet() {
		return bullet;
	}
	// 하나의 점이 이 모양과 충돌하였는지 (모양의 margin 거리안에 있는지)를 판단하는 함수
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin) return true;
		return false;
	}
	public boolean isDie() {
		return isdie;
	}
	public void reset() {
		x = initX; y= initY;
	}

	// 해당 모양을 g에 출력해주는 메소드
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

	//돌 
	public void Diaonallymove() {
		int rand =(int)(Math.random()*2);
		switch(rand) {
		case 0:
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
			break;
		case 1 :
			if (xDirection > 0 && x >= xBoundary) {
				//xDirection = -1;
			}
			if (xDirection < 0 && x <= 0) {
				//xDirection = 1;
			}
			x -= (xDirection * steps);

			if (yDirection > 0 && y >= yBoundary) {
				yDirection = -1;
			}
			if (yDirection < 0 && y <= 0) {
				yDirection = 1;
			}
			y -= (yDirection * steps);
			break;
		}
	}
}
