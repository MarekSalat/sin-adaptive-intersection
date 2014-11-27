package cz.fit.sin.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Line2D;

import javax.swing.JComponent;



class GuiDrawingRoad extends JComponent {
	private static final long serialVersionUID = -5267136749698529722L;
	
	public GuiDrawingRoad() {
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		// === Silnice
		
		g.setColor(new Color(100, 100, 100));
		g.fillRect (150,   0, 200, 500); 
		g.fillRect (  0, 150, 500, 200); 
		
        // === Krajnice
		
		g.setColor(new Color(250, 250, 250));
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        
        // left top x-y | x-y
        g2.draw(new Line2D.Float(158,   0, 158, 158));
        g2.draw(new Line2D.Float(  0, 158, 158, 158));
        
        // right top
        g2.draw(new Line2D.Float(342,   0, 342, 158));
        g2.draw(new Line2D.Float(342, 158, 500, 158));
        
        // left down
        g2.draw(new Line2D.Float(158, 342, 158, 500));
        g2.draw(new Line2D.Float(  0, 342, 158, 342));
        
        // right down
        g2.draw(new Line2D.Float(342, 342, 342, 500));
        g2.draw(new Line2D.Float(342, 342, 500, 342));
        
        
        // === STŘEDOVÉ ČÁRY
                    
        // jižní - plná
        g2.draw(new Line2D.Float(204, 342, 204, 500));

        Image img_arrow_bottom = Toolkit.getDefaultToolkit().getImage("images/arrow_bottom.png");
        g2.drawImage(img_arrow_bottom, 158, 342, this);
        g2.finalize();
                    
        // severní - plná
        g2.draw(new Line2D.Float(296,   0, 296, 158));

        Image img_arrow_top = Toolkit.getDefaultToolkit().getImage("images/arrow_top.png");
        g2.drawImage(img_arrow_top, 296, 112, this);
        g2.finalize();
        
        // západní - plná
        g2.draw(new Line2D.Float(  0, 204, 158, 204));

        Image img_arrow_left = Toolkit.getDefaultToolkit().getImage("images/arrow_left.png");
        g2.drawImage(img_arrow_left, 112, 158, this);
        g2.finalize();
        
        // východní - plná
        g2.draw(new Line2D.Float(342, 296, 500, 296));

        Image img_arrow_right = Toolkit.getDefaultToolkit().getImage("images/arrow_right.png");
        g2.drawImage(img_arrow_right, 342, 296, this);
        g2.finalize();
        
        // přerušované čáry
        int posun_z = 0;
        int posun_k = 0;
        
        for(int i = 0; i < 5; i++){
        	
        	posun_z = (i * 32);
        	if(i==0){
        		// -- pokud chceme u křižovatky nepřerušovanou čáru
        		//i++;
        	}
        	posun_k = (i * 32) + 19;
        			
        	// severní čáry
        	g2.draw(new Line2D.Float(250, 158 - posun_z, 250, 158 - posun_k));
        	g2.draw(new Line2D.Float(204, 158 - posun_z, 204, 158 - posun_k));
        	
        	// jižní čáry
        	g2.draw(new Line2D.Float(250, 342 + posun_z, 250, 342 + posun_k));
        	g2.draw(new Line2D.Float(296, 342 + posun_z, 296, 342 + posun_k));
        	
        	// východní čáry
        	g2.draw(new Line2D.Float(342 + posun_z, 204, 342 + posun_k, 204));
        	g2.draw(new Line2D.Float(342 + posun_z, 250, 342 + posun_k, 250));
        	
        	// západní čáry
        	g2.draw(new Line2D.Float(158 - posun_z, 296, 158 - posun_k, 296));
        	g2.draw(new Line2D.Float(158 - posun_z, 250, 158 - posun_k, 250));
        }
        
        
        
        // === KONCOVÝ BOD
        
		g.setColor(new Color(20, 20, 20));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 5 * 0.1f));
        g2.fillRect(0, 0, 30, 500);
        g2.fillRect(0, 0, 500, 30);
        g2.fillRect(470, 0, 500, 500);
        g2.fillRect(0, 470, 500, 500);

        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 10 * 0.1f));
	}
	
}