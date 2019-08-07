package GalacticCats;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Gamepanel extends JPanel{
	final int SPEED=50;
	final int A_MARGIN=110;//�ܰ��ε� ����
	final int B_MARGIN=80; //ū ��ź,��,ū ��� ����
	final int S_MARGIN=40; //���� ��ź�� ���� 
	final int OTHER_MARGIN=50; //���ǵ� ����
	final int STEPS=5;
	final int NEW_ATTACKER_INTERVAL= 5;
	final int OTHER_ATTACKER_INTERVAL= 5;
	final int xB=1045;
	final int yB=578;
	Image buffImage;
	Graphics buffg;
	Image startBackground=new ImageIcon("src/Image/startpanel.png").getImage();
	Image gameBackground=new ImageIcon("src/Image/gameBackground.gif").getImage();
	ImageIcon power_ing=new ImageIcon("src/Image/power_ing.gif");
	ImageIcon power_full=new ImageIcon("src/Image/power_full.gif");
	ImageIcon ph_4=new ImageIcon("src/Image/ph_full.png");
	ImageIcon ph_3=new ImageIcon("src/Image/ph_2.png");
	ImageIcon ph_2=new ImageIcon("src/Image/ph_3.png");
	ImageIcon ph_1=new ImageIcon("src/Image/ph_4.png");
	ImageIcon ph_0=new ImageIcon("src/Image/ph_5.png");

	ArrayList<Bullet> weapon=new ArrayList<Bullet>();
	ArrayList<Bullet> Attacker_weapon=new ArrayList<Bullet>();
	ArrayList<Moving> alian;
	ArrayList<Moving> item;
	ArrayList<Moving> sbb; //stone AND boom AND bird 
	ArrayList<Moving> coinList;

	JButton startButton =new JButton();
	JLabel profile=new JLabel(new ImageIcon("src/Image/profile.png"));
	JLabel nameLabel=new JLabel();
	JLabel scoreLabel=new JLabel();
	JLabel powergauge=new JLabel(power_ing);
	JLabel coinLabel=new JLabel();
	JLabel attack_touched=new JLabel(new ImageIcon("src/Image/attack_touched.gif"));
	JLabel phBar=new JLabel(ph_4);
	ClockListener clockListener;
	Timer goAnime;
	Timer goScore;
	Bullet coin;
	Moving hero;
	Moving green;
	Moving purple;
	Moving yellow;
	int score=0;
	int randNum;
	int coinNum=0;
	int x=0;
	int ph=4;
	String playerName;
	boolean space=false;
	boolean start=false;
	boolean power=false;
	boolean attacked=false;
	boolean upPushed=false;
	boolean downPushed=false;
	boolean leftPushed=false;
	boolean rightPushed=false;

	KeyListener tmp=new heroKeyEvent();

	public Gamepanel() {
		playerName=JOptionPane.showInputDialog("�̸��� �Է����ּ��� :");
		setupGUI();
	}
	public void setupGUI() {
		coin=new Bullet(getClass().getResource("/Image/coin.gif"),980,65);
		setBounds(0,0,1155,688);
		setLayout(null);
		startButton.setBounds(230,550,680,100);
		startButton.setBackground(Color.white);
		startButton.setBorderPainted(false);
		startButton.setOpaque(false);
		startButton.addActionListener(new startButtonListener());
		add(startButton);
		prepareAttacker();
		//ĳ����
		hero=new Moving(getClass().getResource("/Image/fly_basic.gif"),
				getClass().getResource("/Image/basic.gif"),
				getClass().getResource("/Image/hero_attacked.gif"),
				getClass().getResource("/Image/basic_attack.gif"),
				0,250,A_MARGIN,5,xB,yB);

		//Ÿ�̸�
		clockListener = new ClockListener();
		goScore = new Timer(800, clockListener);			// �ð��� �ʴ����� ��Ÿ���� ���� ������
		goAnime = new Timer(SPEED, new AnimeListener());	// �׸��� �̵��� ó���ϱ� ���� ������
		//������ �� 
		phBar.setBounds(750,60, 240,55);
		powergauge.setBounds(750,10,360,80);
		coinLabel.setText("X"+coinNum);
		coinLabel.setFont(new Font("Serif",Font.BOLD,25));
		coinLabel.setBounds(1030,70,60,30);
		nameLabel.setText(playerName);
		nameLabel.setBounds(230,38,60,30);
		scoreLabel.setText(String.valueOf(score));
		scoreLabel.setFont(new Font("Serif",Font.BOLD,25));
		scoreLabel.setBounds(240,65,100,50);
		profile.setBounds(15,15,352,118);
		profile.setVisible(false);
		nameLabel.setVisible(false);
		scoreLabel.setVisible(false);
		coinLabel.setVisible(false);
		powergauge.setVisible(false);
		phBar.setVisible(false);
		add(coinLabel);
		add(powergauge);
		add(nameLabel);
		add(scoreLabel);
		add(profile);
		add(phBar);


	}
/*
	//������ ������ ���� �������۸� ���� paint�޼ҵ�� screenDraw�޼ҵ� 
	public void paint(Graphics g) {
		buffImage = createImage(1155, 688);
		buffg = buffImage.getGraphics();
		screenDraw(buffg);
		g.drawImage(buffImage,0,0,null);
	}
	public void screenDraw(Graphics g) {

		g.drawImage(startBackground,0,0,null); //ù ȭ�� 

		if(start) { //����ȭ������ ��ȯ
			g.drawImage(gameBackground,x,0,null);
			hero.draw(g,this);
			for (Moving v : alian) {
				v.draw(g,this);
			}
			for (Moving v : sbb) {
				v.draw(g,this);
			}
			for(Moving m: coinList) {
				m.draw(g,this);
			}
			coin.draw(g); //����������
	
			if(space) {
				System.out.println(""+space);
				hero.setImg(hero.getAttack_img());
				hero.draw(g,this);
				hero.setImg(hero.getImg());
				hero.draw(g,this);
				space=false;
				/*hero.setImage(new ImageIcon("src/Image/basic.gif").getImage());
				space=false;
				hero.setImage(new ImageIcon("src/Image/fly_basic.gif").getImage());
				 
			}
			for(Bullet b:weapon) {
				b.draw(g);
				if(b.getX()>1155) {
					weapon.remove(b);
					break;
				}
				b.HeroMove();
			}
			if(attacked)
				hero.setImg(hero.getAttacked_img());
			for(Bullet b:Attacker_weapon) {
				b.draw(g);
				b.Attakcermove();
				if(b.getX()>1155) {
					Attacker_weapon.remove(b);
				}
			}
		}

		this.repaint();
	}*/
	public void paintComponent(Graphics g) {
		
		if(!start)g.drawImage(startBackground,0,0,null); //ù ȭ�� 
		else{ //����ȭ������ ��ȯ
			g.drawImage(gameBackground,x,0,null);
			hero.draw(g,this);
			for (Moving v : alian) {
				v.draw(g,this);
			}
			for (Moving v : sbb) {
				v.draw(g,this);
			}
			for(Moving m: coinList) {
				m.draw(g,this);
			}
			coin.draw(g); //����������
	
			if(space) {
				System.out.println(""+space);
				hero.setImg(hero.getAttack_img());
				hero.draw(g,this);
				hero.setImg(hero.getImg());
				hero.draw(g,this);
				space=false;
				/*hero.setImage(new ImageIcon("src/Image/basic.gif").getImage());
				space=false;
				hero.setImage(new ImageIcon("src/Image/fly_basic.gif").getImage());
				 */
			}
			for(Bullet b:weapon) {
				b.draw(g);
				if(b.getX()>1155) {
					weapon.remove(b);
					break;
				}
				b.HeroMove();
			}
			if(attacked)
				hero.setImg(hero.getAttacked_img());
			for(Bullet b:Attacker_weapon) {
				b.draw(g);
				b.Attakcermove();
				if(b.getX()>1155) {
					Attacker_weapon.remove(b);
				}
			}
		}

		this.repaint();
	}
	public class startButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			start=true;
			startButton.setVisible(false);
			profile.setVisible(true);
			nameLabel.setVisible(true);
			scoreLabel.setVisible(true);
			coinLabel.setVisible(true);
			powergauge.setVisible(true);
			phBar.setVisible(true);
			prepareAttacker();
			goScore.start();
			goAnime.start();
			startButton.setContentAreaFilled(false);
			startButton.setFocusPainted(false);
		}

	}
	public void prepareAttacker() {
		coinList=new ArrayList<Moving>();
		item=new ArrayList<>();
		alian=new ArrayList<>();
		sbb=new ArrayList<>();
		green=new Moving(getClass().getResource("/Image/green_attack.gif"),
				getClass().getResource("/Image/green_attacked.gif"),
				getClass().getResource("/Image/attack4.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(green);

		purple= new Moving(getClass().getResource("/Image/purple_attack.gif"),
				getClass().getResource("/Image/purple_attacked.gif"),
				getClass().getResource("/Image/attack1.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(purple);

		yellow=new Moving(getClass().getResource("/Image/yellow_attack.gif"),
				getClass().getResource("/Image/yellow_attacked.gif"),
				getClass().getResource("/Image/attack3.png"),
				A_MARGIN,STEPS,xB,yB
				);
		alian.add(yellow);

		for(int i=0; i<3; i++) {
			randNum=(int)(Math.random()*14)+13;
			item.add(getRandomMoving(randNum));
		}
		for(int i=0; i<3; i++) {
			randNum=(int)(Math.random()*12)+4;
			sbb.add(getRandomMoving(randNum));
		}

		for(int i=0; i<150; i+=30) {
			coinList.add(getcoin(500+i,200));
			coinList.add(getcoin(1000+i,400+i));
			coinList.add(getcoin(1500+i,250-i));
			coinList.add(getcoin(2000-i,400+i));
			coinList.add(getcoin(2500+i,250));
			coinList.add(getcoin(3000+i,400+i));
			coinList.add(getcoin(3500+i,250-i));
			coinList.add(getcoin(4000-i,400-i));
			coinList.add(getcoin(4500+i,250));
			coinList.add(getcoin(5000+i,400+i));
			coinList.add(getcoin(5500+i,250-i));
			coinList.add(getcoin(6000+i,400+i));
			coinList.add(getcoin(6500+i,250));
			coinList.add(getcoin(7000+i,400+i));
			coinList.add(getcoin(7500+i,250));
			coinList.add(getcoin(8000+i,400));
			coinList.add(getcoin(8500+i,250));
			coinList.add(getcoin(9000+i,400+i));
			coinList.add(getcoin(9500+i,250-i));
			coinList.add(getcoin(10000+i,400-i));
			coinList.add(getcoin(10500+i,250));
			coinList.add(getcoin(11000+i,400-i));
			coinList.add(getcoin(11500+i,250));
		}

	}

	public class heroKeyEvent implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			//ĳ���;ȿ����� 

			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upPushed = true;
				break;
			case KeyEvent.VK_DOWN:
				downPushed = true;
				break;
			case KeyEvent.VK_LEFT:
				leftPushed = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightPushed =true;
				break;
			case KeyEvent.VK_SPACE:
				space=true;
				weapon.add(new Bullet(hero.getBullet(),hero.x,hero.y));
				break;	
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {

			case KeyEvent.VK_UP:
				upPushed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPushed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPushed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPushed =false;
				break;
			case KeyEvent.VK_SPACE:
				break;
			}

		}
	}

	public void heroMove() {
		if(upPushed) 
			if (hero.y >= 0)
				hero.y -= 10;
		if(downPushed) 
			if (hero.y <=580)
				hero.y += 10;
		if(leftPushed)
			if (hero.x >= 0)
				hero.x -= 10;
		if(rightPushed)
			if (hero.x <=1050)
				hero.x += 10;
	}

	private class ClockListener implements ActionListener {

		public void actionPerformed (ActionEvent event) {

			score++;	
			Attacker_weapon.add(new Bullet(green.getBullet(),green.getX(),green.getY()+green.getMargin()/2));
			Attacker_weapon.add(new Bullet(purple.getBullet(),purple.getX(),purple.getY()+purple.getMargin()/2));
			Attacker_weapon.add(new Bullet(yellow.getBullet(),yellow.getX(),yellow.getY()+yellow.getMargin()/2));
			scoreLabel.setText(String.valueOf(score));
			//���ΰ��� setText ���ֱ�

			if(score==30) power=true;
			if(power) {
				powergauge.setIcon(power_full);
			}else {
				powergauge.setIcon(power_ing);
			}

			// �ð��� �����ð� ������ ���ο� �ܰ��ε��� ������Ŵ
			if (score % NEW_ATTACKER_INTERVAL == 0) {
				randNum=(int)(Math.random()*3)+1;
				alian.add(getRandomMoving(randNum));
				for(Moving m:alian) {
					Attacker_weapon.add(new Bullet(m.getBullet(),m.getX(),m.getY()+m.getMargin()/2));	
				}
			}
			// �ð��� �����ð� ������ ��ź,��,����,,,,�����۵�  ����/�Ҹ� ��Ŵ
			if (score % OTHER_ATTACKER_INTERVAL == 0) {

				randNum=(int)(Math.random()*12)+4;
				sbb.add(getRandomMoving(randNum));

				randNum=(int)(Math.random()*15)+13;
				item.add(getRandomMoving(randNum));


			}
		}

	}
	public Moving getRandomMoving(int rand) {
		Moving newAttacker = null;

		switch (rand) {
		case 1 : //1~3�� �ܰ��ε� 
			newAttacker = new Moving(getClass().getResource("/Image/green_attack.gif"),
					getClass().getResource("/Image/green_attacked.gif"),
					getClass().getResource("/Image/attack4.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 2 :
			newAttacker = new Moving(getClass().getResource("/Image/purple_attack.gif"),
					getClass().getResource("/Image/purple_attacked.gif"),
					getClass().getResource("/Image/attack1.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 3 :
			newAttacker =new Moving(getClass().getResource("/Image/yellow_attack.gif"),
					getClass().getResource("/Image/yellow_attacked.gif"),
					getClass().getResource("/Image/attack3.png"),
					A_MARGIN,STEPS,xB,yB
					);
			for(int i=0;i<10;i++)
				Attacker_weapon.add(new Bullet(newAttacker.getBullet(),newAttacker.getX(),newAttacker.getY()+newAttacker.getMargin()/2));
			break;
		case 4 :	//case 4~8 ��ź
			newAttacker =  new Moving(getClass().getResource("/Image/ball1.png"),
					getClass().getResource("/Image/boom.gif"),
					S_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;
		case 5:
			newAttacker =  new Moving(getClass().getResource("/Image/ball2.png"),
					getClass().getResource("/Image/boom.gif"),
					S_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;
		case 6:
			newAttacker =  new Moving(getClass().getResource("/Image/ball3.png"),
					getClass().getResource("/Image/boom.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;
		case 7:
			newAttacker =  new Moving(getClass().getResource("/Image/ball4.png"),
					getClass().getResource("/Image/boom.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;
		case 8: //8~10 stone
			newAttacker =  new Moving(getClass().getResource("/Image/stone1.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;		
		case 9:
			newAttacker =  new Moving(getClass().getResource("/Image/stone3.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					S_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;
		case 10:
			newAttacker =  new Moving(getClass().getResource("/Image/stone4.png"),
					getClass().getResource("/Image/stone_destroyed.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBoom(true);
			break;

		case 11: //11~12��
			newAttacker =  new Moving(getClass().getResource("/Image/blackbird.gif"),
					getClass().getResource("/Image/blackbird_attacked.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBird(true);
			break;
		case 12:
			newAttacker =  new Moving(getClass().getResource("/Image/redbird.gif"),
					getClass().getResource("/Image/redbird_attacked.gif"),
					B_MARGIN,xB,yB);
			newAttacker.setBird(true);
			break;
		case 13:
			newAttacker= new Moving(getClass().getResource("/Image/ph.gif"),OTHER_MARGIN);
			break;
		case 14:
			newAttacker =new Moving(getClass().getResource("/Image/power.gif"),OTHER_MARGIN);

		}
		return newAttacker;
	}
	public void setPhbar(int ph) {
		if(ph==4) phBar.setIcon(ph_4);
		else if(ph==3) phBar.setIcon(ph_3);
		else if(ph==2)phBar.setIcon(ph_2);
		else if(ph==1)phBar.setIcon(ph_1);
		else if(ph==0) 	phBar.setIcon(ph_0); //��������
		else if(ph<0) {
			goAnime.stop();
			goScore.stop();
		}
	}
	public Moving getcoin(int x,int y) {
		Moving NewItem=null;
		NewItem=new Moving(getClass().getResource("/Image/coin.gif"),OTHER_MARGIN,x,y);
		return NewItem;
	}
	public class AnimeListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// ���� �浹�Ͽ����� �浹�� ȿ���� ��Ÿ���� Ÿ�̸Ӹ� �ߴܽ�Ŵ
			heroMove();
			x-=1;
			if(x<-160)x=0;
			// �׸� ��ü���� �̵���Ŵ
			if(!alian.isEmpty()) {
				for (Moving s : alian) {
					if (s.collide(new Point(hero.x, hero.y))) {
						//boomSound.play();					// �浹�� ����
						//hero��ģ�ɷ� �׷��ֱ�
						//ü�� ��� (ph--)->setPhbar();
						attacked=true;
						ph--;
						setPhbar(ph);

					}
				}
				for (Moving m : alian) {
					m.Horizontallymove();
				}
			}
			if(!sbb.isEmpty()) {
				for (Moving m : sbb) {
					if (m.collide(new Point(hero.x, hero.y))) {
						//boomSound.play();					// �浹�� ����
						//hero��ģ�ɷ� �׷��ֱ�
						attacked=true;
						ph--;
						setPhbar(ph);

					}
				}
				//������ 
				for (Moving m : sbb) {
					if(m.boom)
						m.Diaonallymove();
					else if(m.bird)
						m.Horizontallymove();
				}	
			}
			if(!coinList.isEmpty()) {
				for(Moving m: coinList) {
					m.Horizontallymove();
				}
			}



			//hero �� �ѽ��� �������̴°� ���� ->���� arrayList���� remove;
			//������ �ѽ��� hero�� �´°� ���� -> ü�´�� ->ü�� �ٴ����� ������ (return)
		}
	}
}


