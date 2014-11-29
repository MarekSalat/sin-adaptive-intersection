package cz.fit.sin.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

class GuiDrawingRoad extends JComponent {
	private static final long serialVersionUID = -5267136749698529722L;
	
	private Graphics g;
	private int 	 phases[];
	
	public void setSemPhases(int phases[]){
		this.phases = phases;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		this.g = g;
		
		this.drawingRoad();
		this.drawingSemaphores();
	}
	
	private void drawingRoad(){
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

	private void drawingSemaphores() {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 10 * 0.1f));		
		
		for(int i = 0; i < this.phases.length; i++){
			
			// === volba barvy
			
			if(this.phases[i] == 1){
				// green
				g.setColor(new Color(43, 202, 0));
			}
			else{
				// red
				g.setColor(new Color(227, 0, 32));
			}
			
			// === umístění semaforu
			switch(i){
			
				// === NORTH
			
				case Semaphores.NORTH_FORWARD:
					g.fillOval(204 + (46-24) / 2, 130, 24, 24); 

			        Image arrow1 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_bottom.png");
			        g2.drawImage(arrow1, 204 + (46-24) / 2, 130, this);
			        g2.finalize();
					break;
				
				case Semaphores.NORTH_LEFT:
					g.fillOval(250 + (46-24) / 2, 130, 24, 24); 

			        Image arrow2 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_right.png");
			        g2.drawImage(arrow2, 250 + (46-24) / 2, 130, this);
			        g2.finalize();
					break;
					
				case Semaphores.NORTH_RIGHT:
					g.fillOval(158 + (46-24) / 2, 130, 24, 24); 

			        Image arrow3 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_left.png");
			        g2.drawImage(arrow3, 158 + (46-24) / 2, 130, this);
			        g2.finalize();
					break;
					
				// === SOUTH

				case Semaphores.SOUTH_FORWARD:
					g.fillOval(250 + (46-24) / 2, 346, 24, 24); 

			        Image arrow4 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_top.png");
			        g2.drawImage(arrow4, 250 + (46-24) / 2, 346, this);
			        g2.finalize();
					break;

				case Semaphores.SOUTH_LEFT:
					g.fillOval(204 + (46-24) / 2, 346, 24, 24); 

			        Image arrow5 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_left.png");
			        g2.drawImage(arrow5, 204 + (46-24) / 2, 346, this);
			        g2.finalize();
					break;

				case Semaphores.SOUTH_RIGHT:
					g.fillOval(296 + (46-24) / 2, 346, 24, 24); 

			        Image arrow6 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_right.png");
			        g2.drawImage(arrow6, 296 + (46-24) / 2, 346, this);
			        g2.finalize();
					break;
					
				// === WEST

				case Semaphores.WEST_FORWARD:
					g.fillOval(130, 250 + (46-24) / 2, 24, 24); 

			        Image arrow7 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_right.png");
			        g2.drawImage(arrow7, 130, 250 + (46-24) / 2, this);
			        g2.finalize();
					break;

				case Semaphores.WEST_LEFT:
					g.fillOval(130, 204 + (46-24) / 2, 24, 24); 

			        Image arrow8 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_top.png");
			        g2.drawImage(arrow8, 130, 204 + (46-24) / 2, this);
			        g2.finalize();
					break;

				case Semaphores.WEST_RIGHT:
					g.fillOval(130, 296 + (46-24) / 2, 24, 24); 

			        Image arrow9 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_bottom.png");
			        g2.drawImage(arrow9, 130, 296 + (46-24) / 2, this);
			        g2.finalize();
					break;
					
				// === EAST

				case Semaphores.EAST_FORWARD:
					g.fillOval(346, 204 + (46-24) / 2, 24, 24); 

			        Image arrow10 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_left.png");
			        g2.drawImage(arrow10, 346, 204 + (46-24) / 2, this);
			        g2.finalize();
					break;

				case Semaphores.EAST_LEFT:
					g.fillOval(346, 250 + (46-24) / 2, 24, 24); 

			        Image arrow11 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_bottom.png");
			        g2.drawImage(arrow11, 346, 250 + (46-24) / 2, this);
			        g2.finalize();
					break;

				case Semaphores.EAST_RIGHT:
					g.fillOval(346, 158 + (46-24) / 2, 24, 24); 

			        Image arrow12 = Toolkit.getDefaultToolkit().getImage("images/sem_arrow_top.png");
			        g2.drawImage(arrow12, 346, 158 + (46-24) / 2, this);
			        g2.finalize();
					break;
				
				
			}
			
			
		
		}		
	}
	
}