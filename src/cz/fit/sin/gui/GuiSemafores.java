package cz.fit.sin.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

class GuiSemafores extends JComponent{
	private static final long serialVersionUID = 776435645181332915L;
	
	// ---
	private int phases[];
	
	public GuiSemafores() {
		//this.repaint();
	}
	
	public void setSemPhases(int phases[]){
		this.phases = phases;
		
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 10 * 0.1f));
		
        
        //g.drawString("ahoj", 20, 20);
        
        // x- y- width- height / 158 - 130
		
		
		
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
					break;
				
				case Semaphores.NORTH_LEFT:
					g.fillOval(250 + (46-24) / 2, 130, 24, 24); 
					break;
					
				case Semaphores.NORTH_RIGHT:
					g.fillOval(158 + (46-24) / 2, 130, 24, 24); 
					break;
					
				// === SOUTH

				case Semaphores.SOUTH_FORWARD:
					g.fillOval(250 + (46-24) / 2, 346, 24, 24); 
					break;

				case Semaphores.SOUTH_LEFT:
					g.fillOval(204 + (46-24) / 2, 346, 24, 24); 
					break;

				case Semaphores.SOUTH_RIGHT:
					g.fillOval(296 + (46-24) / 2, 346, 24, 24); 
					break;
					
				// === WEST

				case Semaphores.WEST_FORWARD:
					g.fillOval(130, 250 + (46-24) / 2, 24, 24); 
					break;

				case Semaphores.WEST_LEFT:
					g.fillOval(130, 204 + (46-24) / 2, 24, 24); 
					break;

				case Semaphores.WEST_RIGHT:
					g.fillOval(130, 296 + (46-24) / 2, 24, 24); 
					break;
					
				// === EAST

				case Semaphores.EAST_FORWARD:
					g.fillOval(346, 204 + (46-24) / 2, 24, 24); 
					break;

				case Semaphores.EAST_LEFT:
					g.fillOval(346, 250 + (46-24) / 2, 24, 24); 
					break;

				case Semaphores.EAST_RIGHT:
					g.fillOval(346, 158 + (46-24) / 2, 24, 24); 
					break;
				
				
			}
			
			
		
		}

		
		// === SEMAFORE
		
        // pole semaforu --- jmena: sever - rovne, doleva, doprava ... konstanty
        // dosadit sipecku na semafor, dle svetove strany a pruhu
        
		//new Semafore(g);
		
		//drawingroad.repaint();
		//drawingroad.updateUI();
		
	}
	
}
