package shakki;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.applet.*;
import java.io.File;
import java.net.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Ohjaus extends JPanel implements ActionListener, KeyListener,
        MouseListener, MouseMotionListener {

    Timer t = new Timer(5, this);
    double x = 300, y = 500, velx = 0, vely = 0;
    int p = 0;
    int random;
    int koko = 8;
    int ruudunkoko = 50;
    int luku;
    int vuoro = 1;
    int valittu;
    int apu;
    boolean trigger = false; // tornin tarkistukseen
    boolean shakkitarkistus = false;
    boolean shakki = false;
    int mattilaskuri;
    int mahdLaskuri;
    boolean matti = false;
    int uhattu = -1;
    int uhkaaja = -1;
    int cpu = 2;
    int siirronarvo;
    int parassiirto;
    int siirronarvo2;
    int parassiirto2;
    int huonoinsiirto;
    int huonoinsiirto2;
    
    
    boolean ekatarkistus = true;
    boolean teesiirto = false;
    int apunappula, apunappula2;
    int aputype, aputype2;
    int siirrettava, siirrettava2;
    int siirto, siirto2;
    int delay = 200;
    int edellinen = -1;
    int edellinen2= -1;
    int[][] lauta = {{2, 3, 4, 5, 6, 4, 3, 2},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {21, 21, 21, 21, 21, 21, 21, 21},
        {22, 23, 24, 26, 25, 24, 23, 22}};
    String polku = "";
    String sotilas1 = "sotilas1.png";
    String sotilas2 = "sotilas2.png";
    String torni1 = "torni1.png";
    String torni2 = "torni2.png";
    String hevonen1 = "hevonen1.png";
    String hevonen2 = "hevonen2.png";
    String lahetti1 = "lahetti1.png";
    String lahetti2 = "lahetti2.png";
    String kuningas1 = "kuningas1.png";
    String kuningas2 = "kuningas2.png";
    String kuningatar1 = "kuningatar1.png";
    String kuningatar2 = "kuningatar2.png";
    List<Ruutu> ruudut = new ArrayList<Ruutu>();

    // Pisara pisara1 = new Pisara();
    //Pallo pallo1 = new Pallo();
    public Ohjaus() {
        t.start();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setBackground(Color.WHITE);

        // pallo1.setVipu(true);

        polku = (getClass().getResource(".").getPath());


        for (int i = 0; i < koko; i++) {

            for (int j = 0; j < koko; j++) {

                if (i < 2) {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 2, 0));
                } else if (i > 5) {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 1, 0));
                } else {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 0, 0));
                }
            }
        }
        //------------------------------------------

        ruudut.get(0).setNappula(6); //torni
        ruudut.get(1).setNappula(5); // hevonen
        ruudut.get(2).setNappula(4); // lähetti
        ruudut.get(3).setNappula(3); // kuningatar
        ruudut.get(4).setNappula(2); // kuningas
        ruudut.get(5).setNappula(4);
        ruudut.get(6).setNappula(5);
        ruudut.get(7).setNappula(6);

        for (int i = 8; i < 16; i++) {
            ruudut.get(i).setNappula(1);
        }

        for (int i = 48; i < 56; i++) {
            ruudut.get(i).setNappula(1);
        }

        ruudut.get(56).setNappula(6); //torni
        ruudut.get(57).setNappula(5); // hevonen
        ruudut.get(58).setNappula(4); // lähetti
        ruudut.get(59).setNappula(3); // kuningatar
        ruudut.get(60).setNappula(2); // kuningas
        ruudut.get(61).setNappula(4);
        ruudut.get(62).setNappula(5);
        ruudut.get(63).setNappula(6);


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        // g2d.translate(this.getWidth()/2, this.getHeight()/2);
        // g2d.rotate(angle);        
        ImageIcon so1 = new ImageIcon(polku + sotilas1);
        ImageIcon so2 = new ImageIcon(polku + sotilas2);
        ImageIcon to1 = new ImageIcon(polku + torni1);
        ImageIcon to2 = new ImageIcon(polku + torni2);
        ImageIcon he1 = new ImageIcon(polku + hevonen1);
        ImageIcon he2 = new ImageIcon(polku + hevonen2);
        ImageIcon la1 = new ImageIcon(polku + lahetti1);
        ImageIcon la2 = new ImageIcon(polku + lahetti2);
        ImageIcon ku1 = new ImageIcon(polku + kuningas1);
        ImageIcon ku2 = new ImageIcon(polku + kuningas2);
        ImageIcon kr1 = new ImageIcon(polku + kuningatar1);
        ImageIcon kr2 = new ImageIcon(polku + kuningatar2);

        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));

        for (int i = 0; i < ruudut.size(); i++) {

            g2d.setColor(Color.BLACK);
            g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);


            g.setColor(new Color(156, 93, 82));
            if (ruudut.get(i).getVaaka() % 2 == 0 && ruudut.get(i).getPysty() % 2 == 0) {
                g.fillRect(ruudut.get(i).getX() + 1, ruudut.get(i).getY() + 1, ruudunkoko - 1, ruudunkoko - 1);
            } else if (ruudut.get(i).getVaaka() % 2 != 0 && ruudut.get(i).getPysty() % 2 != 0) {
                g.fillRect(ruudut.get(i).getX() + 1, ruudut.get(i).getY() + 1, ruudunkoko - 1, ruudunkoko - 1);
            } else {
                g.setColor(new Color(255, 255, 150));
                g.fillRect(ruudut.get(i).getX() + 1, ruudut.get(i).getY() + 1, ruudunkoko - 1, ruudunkoko - 1);
            }

            //-----nappulat------
            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 1) {
                so1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
                //g.drawString("v"+ruudut.get(i).getNappula(),ruudut.get(i).getX()+2, ruudut.get(i).getY()+(ruudunkoko/2));
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 1) {
                so2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }

            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 6) {
                to1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 6) {
                to2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }

            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 5) {
                he1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 5) {
                he2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }

            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 4) {
                la1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 4) {
                la2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }

            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 2) {
                ku1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 2) {
                ku2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }

            if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 3) {
                kr1.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            } else if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 3) {
                kr2.paintIcon(this, g, ruudut.get(i).getX(), ruudut.get(i).getY());
            }
        }

        //------------
        for (int i = 0; i < ruudut.size(); i++) {
            if (ruudut.get(i).isSelected() == true && ruudut.get(i).getType() != 0) {
                g2d.setColor(Color.YELLOW);
                g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);
            }

            if (ruudut.get(i).isPossibleMove() == true) {
                g2d.setColor(Color.GREEN);
                g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);
            }

            if (ruudut.get(valittu).getNappula() == 2 && ruudut.get(valittu).isHasMoved() == false && ruudut.get(i).getType() == vuoro) {


                if (ruudut.get(i).getVaaka() == 8) {
                    if (ruudut.get(i).getNappula() == 6 && ruudut.get(i).isHasMoved() == false
                            && ruudut.get(i - 1).getType() == 0 && ruudut.get(i - 2).getType() == 0) {

                        g2d.setColor(Color.GREEN);
                        g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);

                    }

                } else if (ruudut.get(i).getVaaka() == 1) {
                    if (ruudut.get(i).getNappula() == 6 && ruudut.get(i).isHasMoved() == false
                            && ruudut.get(i + 1).getType() == 0 && ruudut.get(i + 2).getType() == 0
                            && ruudut.get(i + 3).getType() == 0) {

                        g2d.setColor(Color.ORANGE);
                        g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);

                    }
                }
            }
            if (i == uhkaaja || i == uhattu) {
                g2d.setColor(Color.RED);
                g2d.drawRect(ruudut.get(i).getX(), ruudut.get(i).getY(), ruudunkoko, ruudunkoko);
            }
            
            g2d.setColor(Color.BLUE);
            if (edellinen >= 0&&vuoro==2) {
                g2d.drawRect(ruudut.get(edellinen).getX(), ruudut.get(edellinen).getY(), ruudunkoko, ruudunkoko);
            }else if(edellinen2>=0&&vuoro==1){
                g2d.drawRect(ruudut.get(edellinen2).getX(), ruudut.get(edellinen2).getY(), ruudunkoko, ruudunkoko); 
                }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Pelaajan " + vuoro + " vuoro", (ruudunkoko * koko) + ruudunkoko, ruudunkoko * (koko / 2));
        if (shakki == true) {
            g.setFont(new Font("Arial", Font.BOLD, 25));
            if (matti == true) {
                g.drawString("Shakki Matti!", (ruudunkoko * koko) + ruudunkoko, ruudunkoko * (koko / 4));
            } else {
                g.drawString("Shakki!", (ruudunkoko * koko) + ruudunkoko, ruudunkoko * (koko / 4));
            }
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect((ruudunkoko * koko)+ruudunkoko, ruudunkoko * (koko / 2)+ruudunkoko*3, ruudunkoko*3, ruudunkoko); 
        g2d.drawString("Restart",(ruudunkoko * koko)+ruudunkoko*2, ruudunkoko * (koko / 2)+ruudunkoko*3+(ruudunkoko/2)); 


    }

    public void actionPerformed(ActionEvent e) {

        if (vuoro == cpu && delay <= 0) {
            tekoaly();
        }

        if (vuoro == cpu) {
            delay--;
        }



        repaint();
    }

    public void reset() {

        ruudut.clear();

        for (int i = 0; i < koko; i++) {

            for (int j = 0; j < koko; j++) {

                if (i < 2) {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 2, 0));
                } else if (i > 5) {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 1, 0));
                } else {
                    ruudut.add(new Ruutu(j * ruudunkoko, i * ruudunkoko, j + 1, i + 1, 0, 0));
                }
            }
        }
        //------------------------------------------

        ruudut.get(0).setNappula(6); //torni
        ruudut.get(1).setNappula(5); // hevonen
        ruudut.get(2).setNappula(4); // lähetti
        ruudut.get(3).setNappula(3); // kuningatar
        ruudut.get(4).setNappula(2); // kuningas
        ruudut.get(5).setNappula(4);
        ruudut.get(6).setNappula(5);
        ruudut.get(7).setNappula(6);

        for (int i = 8; i < 16; i++) {
            ruudut.get(i).setNappula(1);
        }

        for (int i = 48; i < 56; i++) {
            ruudut.get(i).setNappula(1);
        }

        ruudut.get(56).setNappula(6); //torni
        ruudut.get(57).setNappula(5); // hevonen
        ruudut.get(58).setNappula(4); // lähetti
        ruudut.get(59).setNappula(3); // kuningatar
        ruudut.get(60).setNappula(2); // kuningas
        ruudut.get(61).setNappula(4);
        ruudut.get(62).setNappula(5);
        ruudut.get(63).setNappula(6);

    }

    public void mahdollisetSiirrot(int i) {

        if (ruudut.get(i).isSelected() == true) {



            //---------mustan sotilaan mahdolliset siirrot----------------

            if (ruudut.get(i).getNappula() == 1 && ruudut.get(i).getType() == 2) {

                mahdollisetSotilas(i + koko, vuoro, 1);
                if (ruudut.get(i).isHasMoved() == false && (i + koko) < ruudut.size()) {

                    if (ruudut.get(i + koko).getType() == 0) {
                        mahdollisetSotilas(i + koko + koko, vuoro, 1);
                    }
                }
                if (ruudut.get(i).getVaaka() <= 7) {
                    mahdollisetSotilas(i + koko + 1, vuoro, 2);
                }
                if (ruudut.get(i).getVaaka() >= 2) {
                    mahdollisetSotilas(i + koko - 1, vuoro, 2);
                }

                //------- valkoisen sotilaan mahdolliset siirrot------------------
            } else if (ruudut.get(i).getNappula() == 1 && ruudut.get(i).getType() == 1) {

                mahdollisetSotilas(i - koko, vuoro, 1);
                if (ruudut.get(i).isHasMoved() == false && (i - koko) >= 0) {
                    if (ruudut.get(i - koko).getType() == 0) {
                        mahdollisetSotilas(i - koko - koko, vuoro, 1);
                    }
                }

                if (ruudut.get(i).getVaaka() <= 7) {
                    mahdollisetSotilas(i - koko + 1, vuoro, 2);
                }
                if (ruudut.get(i).getVaaka() >= 2) {
                    mahdollisetSotilas(i - koko - 1, vuoro, 2);
                }
            }

            //---------hevosen mahdolliset siirrot

            if (ruudut.get(i).getNappula() == 5 && ruudut.get(i).getType() == vuoro) {

                if (ruudut.get(i).getVaaka() <= 6) {
                    mahdolliset(i + koko + 2, vuoro);
                    mahdolliset(i - koko + 2, vuoro);
                }
                if (ruudut.get(i).getVaaka() >= 3) {
                    mahdolliset(i + koko - 2, vuoro);
                    mahdolliset(i - koko - 2, vuoro);
                }
                if (ruudut.get(i).getVaaka() <= 7) {
                    mahdolliset(i + koko + koko + 1, vuoro);
                    mahdolliset(i - koko - koko + 1, vuoro);
                }
                if (ruudut.get(i).getVaaka() >= 2) {
                    mahdolliset(i + koko + koko - 1, vuoro);
                    mahdolliset(i - koko - koko - 1, vuoro);
                }
            }


            //------------kuninkaan mahdolliset siirrot------------ 

            if (ruudut.get(i).getNappula() == 2 && ruudut.get(i).getType() == vuoro) {

                mahdolliset(i + koko, vuoro);
                mahdolliset(i - koko, vuoro);

                if (ruudut.get(i).getVaaka() <= 7) {
                    mahdolliset(i + koko + 1, vuoro);
                    mahdolliset(i + 1, vuoro);
                    mahdolliset(i - koko + 1, vuoro);
                }
                if (ruudut.get(i).getVaaka() >= 2) {
                    mahdolliset(i + koko - 1, vuoro);
                    mahdolliset(i - 1, vuoro);
                    mahdolliset(i - koko - 1, vuoro);
                }

                if (ruudut.get(i).isHasMoved() == false) {    //--- tornitus
                    // if(ruudut.get)
                }

            }

            //-----------tornin mahdolliset siirrot-----

            if (ruudut.get(i).getNappula() == 6 || ruudut.get(i).getNappula() == 3) {

                trigger = false;
                for (int x = 1; x <= 8 - ruudut.get(i).getPysty() && trigger == false; x++) {
                    mahdolliset(i + koko * x, vuoro);
                }
                trigger = false;
                for (int x = 1; ruudut.get(i).getPysty() - x >= 1 && trigger == false; x++) {
                    mahdolliset(i - koko * x, vuoro);
                }
                trigger = false;
                if (ruudut.get(i).getVaaka() <= 7) {
                    for (int x = 1; x <= 8 - ruudut.get(i).getVaaka() && trigger == false; x++) {
                        mahdolliset(i + x, vuoro);
                    }
                }
                trigger = false;
                if (ruudut.get(i).getVaaka() >= 2) {
                    for (int x = 1; ruudut.get(i).getVaaka() - x >= 1 && trigger == false; x++) {
                        mahdolliset(i - x, vuoro);
                        //System.out.println("4");
                    }
                }
            }

            //----------lähetin mahdolliset siirrot-------

            if (ruudut.get(i).getNappula() == 4 || ruudut.get(i).getNappula() == 3) {

                if (ruudut.get(i).getVaaka() <= 7) {
                    trigger = false;
                    for (int x = 1; x <= 8 - ruudut.get(i).getPysty() && trigger == false; x++) {
                        mahdollisetLahetti(i + (koko * x) + x, vuoro);
                    }
                    trigger = false;
                    for (int x = 1; x <= 8 - ruudut.get(i).getVaaka() && trigger == false; x++) {
                        mahdollisetLahetti(i - (koko * x) + x, vuoro);
                    }
                }
                if (ruudut.get(i).getVaaka() >= 2) {
                    trigger = false;
                    for (int x = 1; x <= 8 - ruudut.get(i).getPysty() && trigger == false; x++) {
                        mahdollisetLahetti(i + (koko * x) - x, vuoro);
                    }
                    trigger = false;
                    for (int x = 1; ruudut.get(i).getVaaka() - x >= 1 && trigger == false; x++) { // bugi
                        mahdollisetLahetti(i - (koko * x) - x, vuoro);
                    }
                }
            }

        }

        //-------sotilas pääsee päätyyn-----
        if (ruudut.get(i).getType() == 1 && ruudut.get(i).getNappula() == 1 && ruudut.get(i).getPysty() == 1) {
            ruudut.get(i).setNappula(3);
        }

        if (ruudut.get(i).getType() == 2 && ruudut.get(i).getNappula() == 1 && ruudut.get(i).getPysty() == 8) {
            ruudut.get(i).setNappula(3);
        }

        //----shakki tarkistus----------
        for (int j = 0; j < ruudut.size(); j++) {
            if (ruudut.get(j).isPossibleMove() == true && ruudut.get(j).getNappula() == 2 && ruudut.get(i).getType() == vuoro && j != i) {
                shakkitarkistus = true;
                uhattu = j;
                uhkaaja = i;
            }



        }

    }

    public void parasSiirto(int i, int j) {
        if (siirronarvo > parassiirto) {
            parassiirto = siirronarvo;
            siirrettava = i;
            siirto = j;
        } else if (siirronarvo == parassiirto && Math.random() > 0.8) {
            parassiirto = siirronarvo;
            siirrettava = i;
            siirto = j;
        } /*else if (siirrettava==0) {
         parassiirto = siirronarvo;
         siirrettava = i;
         siirto = j;
            
         }*/
        //ekatarkistus=false;
    }

    public void minimoi(int i) {

        // System.out.println("ruudut "+ruudut.get(j).isPossibleMove());

        for (int j = 0; j < ruudut.size(); j++) {

            if (ruudut.get(j).isPossibleMove() == true && ruudut.get(i).getType() == vuoro) {

                if (ruudut.get(j).getNappula() == 2) {
                    huonoinsiirto = 20;

                } else if (ruudut.get(j).getNappula() == 3) {
                    huonoinsiirto = 10;
                } else if (ruudut.get(j).getNappula() == 4 || ruudut.get(j).getNappula() == 5 || ruudut.get(j).getNappula() == 6) {
                    huonoinsiirto = 7;
                } else if (ruudut.get(j).getNappula() == 1) {
                    huonoinsiirto = 5;
                } else if (ruudut.get(j).getNappula() == 0) {
                    huonoinsiirto = 0;
                }
                
                if(huonoinsiirto>huonoinsiirto2){
                    huonoinsiirto2=huonoinsiirto;
                }

                /*if (j == uhkaaja) {
                 siirronarvo -= 6;
                 }

                 if (i == uhattu) {
                 siirronarvo -= 5;
                 }*/

                /* if (ruudut.get(j).getType() != 0) {

                 if (ruudut.get(i).getNappula() == 1) {
                 siirronarvo -= 3;
                 } else if (ruudut.get(i).getNappula() == 4 || ruudut.get(i).getNappula() == 5 || ruudut.get(i).getNappula() == 6) {
                 siirronarvo -= 2;
                 } else if (ruudut.get(i).getNappula() == 3) {
                 siirronarvo -= 1;
                 }
                 }*/


            }
        }
        
        //siirronarvo-=huonoinsiirto2;
        

    }

    public void arvioiSiirto(int i) { // j = mahdollinen siirto  i = valittu nappula



        for (int j = 0; j < ruudut.size(); j++) {
            
            ruudut.get(i).setSelected(true);
            mahdollisetSiirrot(i);
            
            if (ruudut.get(j).isPossibleMove() == true && ruudut.get(i).getType() == vuoro && i != j) {

                System.out.println("valittu:"+i);
                
                if (ruudut.get(j).getNappula() == 2) {
                    siirronarvo = 20;

                } else if (ruudut.get(j).getNappula() == 3) {
                    siirronarvo = 10;
                } else if (ruudut.get(j).getNappula() == 4 || ruudut.get(j).getNappula() == 5 || ruudut.get(j).getNappula() == 6) {
                    siirronarvo = 7;
                } else if (ruudut.get(j).getNappula() == 1) {
                    //System.out.println("haa");
                    siirronarvo = 5;
                } else if (ruudut.get(j).getNappula() == 0) {
                    siirronarvo = 0;
                }

                //System.out.println("i " + i + "j " + j + " siirronarvo: " + siirronarvo);

                if (j == uhkaaja) {
                    siirronarvo += 6;
                }
                
                if(ruudut.get(j).getNappula() == 1&&ruudut.get(i).getNappula() == 1){
                    siirronarvo+=1;
                }else if(ruudut.get(j).getNappula() == 4&&ruudut.get(i).getNappula() == 4){
                    siirronarvo+=1;
                }else if(ruudut.get(j).getNappula() == 5&&ruudut.get(i).getNappula() == 5){
                    siirronarvo+=1;
                }else if(ruudut.get(j).getNappula() == 6&&ruudut.get(i).getNappula() == 6){
                    siirronarvo+=1;
                }
                
                
                
                testaaSiirtoa(j, i); // i= siirto, j= sirrettava
                
                System.out.println("siirrettava: "+i +" siirto: "+j+" siirronarvo: "+siirronarvo);
                
                
                if (siirronarvo > parassiirto2) {
                    parassiirto2 = siirronarvo;
                    siirrettava2 = i;
                    siirto2 = j;
                } else if (siirronarvo == parassiirto2 && Math.random() > 0.8) {
                    parassiirto2 = siirronarvo;
                    siirrettava2 = i;
                    siirto2 = j;
                } else if (ekatarkistus == true) {
                    siirrettava2 = i;
                    siirto2 = j;
                    ekatarkistus = false;
                }

                System.out.println("parassiirto2: "+parassiirto2);

                //parasSiirto(i, j);

            }
        }
    }

    public void mahdolliset(int apu, int type) {
        if (type == 1) {
            type = 2;
        } else {
            type = 1;
        }
        trigger = false;
        if (apu < ruudut.size() && apu >= 0) {
            if (ruudut.get(apu).getType() == 0 || ruudut.get(apu).getType() == type
                    && ruudut.get(apu).getVaaka() <= 8 && ruudut.get(apu).getVaaka() >= 1
                    && ruudut.get(apu).getPysty() <= 8 && ruudut.get(apu).getPysty() >= 1) {
                ruudut.get(apu).setPossibleMove(true);
            }
            if (ruudut.get(apu).getType() != 0) {
                trigger = true;
            }


        }
    }

    public void mahdollisetLahetti(int apu, int type) {
        if (type == 1) {
            type = 2;
        } else {
            type = 1;
        }
        trigger = false;
        if (apu <= ruudut.size() && apu >= 0) {
            if (ruudut.get(apu).getType() == 0 || ruudut.get(apu).getType() == type
                    && ruudut.get(apu).getVaaka() <= 8 && ruudut.get(apu).getVaaka() >= 1
                    && ruudut.get(apu).getPysty() <= 8 && ruudut.get(apu).getPysty() >= 1) {
                ruudut.get(apu).setPossibleMove(true);
            }
            if (ruudut.get(apu).getType() != 0 || ruudut.get(apu).getVaaka() == 1 || ruudut.get(apu).getVaaka() == 8) {
                trigger = true;
            }
        }
    }

    public void mahdollisetSotilas(int apu, int type, int tyyli) {
        // tyyli 1 = suoraan, tyyli 2 = viistoon
        if (type == 1) {
            type = 2;
        } else {
            type = 1;
        }
        trigger = false;
        if (apu < ruudut.size() && apu >= 0 && tyyli == 1) {
            //System.out.println("apu: " + apu);
            if (ruudut.get(apu).getType() == 0
                    && ruudut.get(apu).getVaaka() <= 8 && ruudut.get(apu).getVaaka() >= 1
                    && ruudut.get(apu).getPysty() <= 8 && ruudut.get(apu).getPysty() >= 1) {
                ruudut.get(apu).setPossibleMove(true);
            }

        } else if (apu < ruudut.size() && apu >= 0 && tyyli == 2) {
            if (ruudut.get(apu).getType() == type
                    && ruudut.get(apu).getVaaka() <= 8 && ruudut.get(apu).getVaaka() >= 1
                    && ruudut.get(apu).getPysty() <= 8 && ruudut.get(apu).getPysty() >= 1) {
                ruudut.get(apu).setPossibleMove(true);
            }
        }
    }

    public void vaihdaVuoro() {
        if (vuoro == 1) {
            vuoro = 2;
            //cpu = 2;
        } else {
            vuoro = 1;
            //cpu = 1;
        }



    }

    public void tarkistaShakki() {

        shakkitarkistus = false;

        for (int j = 0; j < ruudut.size(); j++) {

            ruudut.get(j).setSelected(true);
            mahdollisetSiirrot(j);
            ruudut.get(j).setSelected(false);
            for (int i = 0; i < ruudut.size(); i++) {
                ruudut.get(i).setPossibleMove(false);
            }
        }

        if (shakkitarkistus == true) {
            shakki = true;
        }
    }

    public void tarkistaMatti() {

        mattilaskuri = 0;
        mahdLaskuri = 0;

        for (int j = 0; j < ruudut.size(); j++) {


            for (int i = 0; i < ruudut.size(); i++) {

                ruudut.get(j).setSelected(true);
                mahdollisetSiirrot(j);


                if (ruudut.get(i).isPossibleMove() == true && ruudut.get(j).getType() == vuoro) {

                    mahdLaskuri++;

                    //System.out.println("siirrettava: "+j+" siirto: "+i);

                    apunappula = ruudut.get(i).getNappula();
                    apunappula2 = ruudut.get(j).getNappula();
                    aputype = ruudut.get(i).getType();
                    aputype2 = ruudut.get(j).getType();

                    ruudut.get(i).setNappula(ruudut.get(j).getNappula());           // i= siirto , j=siirrettävä
                    ruudut.get(i).setType(ruudut.get(j).getType());
                    ruudut.get(j).setNappula(0);
                    ruudut.get(j).setType(0);

                    tarkistaShakki();
                    vaihdaVuoro();
                    tarkistaShakki();
                    vaihdaVuoro();

                    ruudut.get(j).setSelected(true);
                    mahdollisetSiirrot(j);

                    //System.out.println("shakkitarkistus: "+shakkitarkistus);


                    if (shakkitarkistus == false) {
                        mattilaskuri++;
                        //System.out.println("mattilaskuri: "+mattilaskuri);
                    }
                    // System.out.println("laskuri: "+mahdLaskuri);
                    // System.out.println("mattilaskuri: "+mattilaskuri);

                    ruudut.get(i).setNappula(apunappula);
                    ruudut.get(i).setType(aputype);
                    ruudut.get(j).setNappula(apunappula2);
                    ruudut.get(j).setType(aputype2);
                }
            }
            unSelect();

        }


        shakki = true;
        System.out.println("mattilaskuri: " + mattilaskuri);

        if (mattilaskuri > 0) {
            System.out.println("Ei Matti!");
        } else {
            System.out.println("Matti!");
            matti = true;
        }
    }

    public void teeSiirto(int i, int j) {   //i=siirto, j=siirrettava

        if (ruudut.get(j).getType() != ruudut.get(i).getType() && i != j) {
            if(vuoro==2){
            edellinen2 = i;
            }else{
                edellinen = i;
            }
            ruudut.get(i).setNappula(ruudut.get(j).getNappula());
            ruudut.get(i).setType(ruudut.get(j).getType());
            ruudut.get(i).setHasMoved(true);
            ruudut.get(j).setNappula(0);
            ruudut.get(j).setType(0);
            shakki = false;
            uhattu = -1;
            uhkaaja = -1;
            tarkistaShakki();
            //System.out.println(vuoro);
            vaihdaVuoro();
            tarkistaShakki();
        }

    }

    public void testaaSiirtoa(int i, int j) {   //i=siirto, j= siirrettava

        //System.out.println(i + " " + j);



        if (ruudut.get(i).getType() != ruudut.get(j).getType() && i != j) {
            //System.out.println(" i ennen: "+ruudut.get(i).getNappula()+" "+ruudut.get(i).getType());
            //System.out.println(" j ennen: "+ruudut.get(j).getNappula()+" "+ruudut.get(j).getType());

            apunappula = ruudut.get(i).getNappula();
            apunappula2 = ruudut.get(j).getNappula();
            aputype = ruudut.get(i).getType();
            aputype2 = ruudut.get(j).getType();

            ruudut.get(i).setNappula(ruudut.get(j).getNappula());
            ruudut.get(i).setType(ruudut.get(j).getType());
            ruudut.get(j).setNappula(0);
            ruudut.get(j).setType(0);

            shakki = false;
            uhattu = -1;
            uhkaaja = -1;
            tarkistaShakki();
            //System.out.println(vuoro);
            vaihdaVuoro();
            tarkistaShakki();

            //parassiirto2 = parassiirto;
            huonoinsiirto2=0;
            huonoinsiirto=0;
            for (int x = 0; x < ruudut.size(); x++) {

                if (ruudut.get(x).getType() == vuoro) {

                    ruudut.get(x).setSelected(true);
                    mahdollisetSiirrot(x);
                    //System.out.println("ennen: "+siirronarvo);
                    minimoi(x);
                    //System.out.println("jälkeen: "+siirronarvo);
                    unSelect();
                }

            }
            siirronarvo-=huonoinsiirto2;
                
            vaihdaVuoro();

            ruudut.get(i).setNappula(apunappula);
            ruudut.get(i).setType(aputype);
            ruudut.get(j).setNappula(apunappula2);
            ruudut.get(j).setType(aputype2);


            //System.out.println(" i jälkeen: "+ruudut.get(i).getNappula()+" "+ruudut.get(i).getType());
            //System.out.println(" j jälkeen: "+ruudut.get(j).getNappula()+" "+ruudut.get(j).getType());
        }


        for (int y = 0; y < ruudut.size(); y++) {
            ruudut.get(y).setSelected(false);
            ruudut.get(y).setPossibleMove(false);

        }


    }

    public void unSelect() {
        for (int i = 0; i < ruudut.size(); i++) {
            ruudut.get(i).setSelected(false);
            ruudut.get(i).setPossibleMove(false);

        }
    }

    public void tekoaly() {

        ekatarkistus = true;

        for (int i = 0; i < ruudut.size(); i++) {

            //System.out.println("laskuri: "+i);

            if (ruudut.get(i).getType() == vuoro) {

                ruudut.get(i).setSelected(true);
                mahdollisetSiirrot(i);
                arvioiSiirto(i);

               //testaa vain parhaimman siirron... pitää korjata

                //testaaSiirtoa(siirto, siirrettava);


               // System.out.println("paras: " + parassiirto);
               // System.out.println("parassiirto2: " + parassiirto2);

               /* if (siirronarvo > parassiirto2) {
                    parassiirto2 = siirronarvo;
                    siirrettava2 = siirrettava;
                    siirto2 = siirto;
                } else if (siirronarvo == parassiirto2 && Math.random() > 0.8) {
                    parassiirto2 = siirronarvo;
                    siirrettava2 = siirrettava;
                    siirto2 = siirto;
                } else if (ekatarkistus == true) {
                    siirrettava2 = siirrettava;
                    siirto2 = siirto;
                    ekatarkistus = false;
                }*/

                //System.out.println(parassiirto2);

                unSelect();
            }

        }

        System.out.println("final parassiirto2: " + parassiirto2);

        //System.out.println(siirto2 + " " + siirrettava2);
        //if(siirto2!=edellinen2){
            //System.out.println("tehdään");
        teeSiirto(siirto2, siirrettava2);
        //}

        if (shakki == true) {
            tarkistaMatti();
            //System.out.println();
        }

        //katsotaan vastustajan seuraava paras siirto
               /* for (int i = 0; i < ruudut.size(); i++) {

         if (ruudut.get(i).getType() == vuoro) {

         ruudut.get(i).setSelected(true);
         mahdollisetSiirrot(i);
         parasSiirto(i);

         for (int j = 0; j < ruudut.size(); j++) {
         ruudut.get(j).setSelected(false);
         ruudut.get(j).setPossibleMove(false);

         }
         }

         }*/

        delay = 200;
        siirronarvo = -10;
        parassiirto = -10;
        parassiirto2 = -10;

    }

    public void mouseMoved(MouseEvent e) {

        int koordX = e.getX();
        int koordY = e.getY();



    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int koordX = e.getX();
        int koordY = e.getY();


    }

    public void mouseClicked(MouseEvent e) {
        int koordX = e.getX();
        int koordY = e.getY();
        int hiiri = e.getButton();
        //System.out.println(asd);

        for (int i = 0; i < ruudut.size(); i++) {
            if (koordX > ruudut.get(i).getX() && koordX < ruudut.get(i).getX() + ruudunkoko
                    && koordY > ruudut.get(i).getY() && koordY < ruudut.get(i).getY() + ruudunkoko) {
                if (hiiri == 1) {
                    //System.out.println(ruudut.get(i).getX()+" "+ruudut.get(i).getY());
                    //System.out.println("vaaka: " + ruudut.get(i).getVaaka() + " pysty:" + ruudut.get(i).getPysty());
                    //System.out.println("id: " + i);
                    if (ruudut.get(i).getType() == vuoro) {
                        for (int j = 0; j < ruudut.size(); j++) {
                            ruudut.get(j).setSelected(false);
                            ruudut.get(j).setPossibleMove(false);

                        }
                        //-----tornitus

                        if (ruudut.get(valittu).getNappula() == 2 && ruudut.get(valittu).isHasMoved() == false) {


                            if (ruudut.get(i).getVaaka() == 8) {
                                if (ruudut.get(i).getNappula() == 6 && ruudut.get(i).isHasMoved() == false
                                        && ruudut.get(i - 1).getType() == 0 && ruudut.get(i - 2).getType() == 0) {

                                    ruudut.get(valittu).setType(0);
                                    ruudut.get(valittu).setNappula(0);
                                    ruudut.get(i).setType(0);
                                    ruudut.get(i).setNappula(0);
                                    ruudut.get(i - 1).setType(vuoro);
                                    ruudut.get(i - 2).setType(vuoro);
                                    ruudut.get(i - 1).setNappula(2);
                                    ruudut.get(i - 2).setNappula(6);
                                    ruudut.get(i - 1).setHasMoved(true);
                                    vaihdaVuoro();
                                }

                            } else if (ruudut.get(i).getVaaka() == 1) {
                                if (ruudut.get(i).getNappula() == 6 && ruudut.get(i).isHasMoved() == false
                                        && ruudut.get(i + 1).getType() == 0 && ruudut.get(i + 2).getType() == 0
                                        && ruudut.get(i + 3).getType() == 0) {

                                    ruudut.get(valittu).setType(0);
                                    ruudut.get(valittu).setNappula(0);
                                    ruudut.get(i).setType(0);
                                    ruudut.get(i).setNappula(0);
                                    ruudut.get(i + 2).setType(vuoro);
                                    ruudut.get(i + 3).setType(vuoro);
                                    ruudut.get(i + 2).setNappula(2);
                                    ruudut.get(i + 3).setNappula(6);
                                    ruudut.get(i + 2).setHasMoved(true);
                                    vaihdaVuoro();
                                }
                            }
                        }
                        //-----tornitus loppuu----

                        ruudut.get(i).setSelected(true);
                        mahdollisetSiirrot(i);
                        valittu = i;
                    }

                    //teeSiirto(i);
                    if (ruudut.get(i).isPossibleMove() == true && ruudut.get(i).isSelected() == false) {
                        ruudut.get(i).setNappula(ruudut.get(valittu).getNappula());
                        ruudut.get(i).setType(ruudut.get(valittu).getType());
                        ruudut.get(i).setHasMoved(true);
                        ruudut.get(valittu).setNappula(0);
                        ruudut.get(valittu).setType(0);
                        edellinen = i;
                        shakki = false;
                        uhattu = -1;
                        uhkaaja = -1;
                        tarkistaShakki();
                        vaihdaVuoro();
                        tarkistaShakki();
                        if (shakki == true) {
                            tarkistaMatti();
                        }
                    }


                }
            }

        }
        //System.out.println(koordX+" "+koordY);   

    }

    public void mouseDragged(MouseEvent e) {
        int koordX = e.getX();
        int koordY = e.getY();

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


    }
}
