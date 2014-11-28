package cz.fit.sin.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class GuiDrawingCars extends JComponent{
	private static final long serialVersionUID = 1316313123878901987L;
	
	private Graphics g;
	private int 	 cars[];
	
	public void setCars(int cars[]){
		this.cars = cars;
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.g = g;
		
		this.drawingCars();
	}
	
	private void drawingCars(){
		
		for(int i = 0; i < this.cars.length; i++){
			
			// === umístění semaforu
			switch(i){
			
				// === NORTH
			
				case Semaphores.NORTH_FORWARD:
					this.carsInQueue(i, 204, 1, 1);
					break;
				
				case Semaphores.NORTH_LEFT:
					this.carsInQueue(i, 250, 1, 1);
					break;
					
				case Semaphores.NORTH_RIGHT:
					this.carsInQueue(i, 158, 1, 1);
					break;
					
				// === SOUTH

				case Semaphores.SOUTH_FORWARD:
					this.carsInQueue(i, 250, 1, 0);
					break;

				case Semaphores.SOUTH_LEFT:
					this.carsInQueue(i, 204, 1, 0);
					break;

				case Semaphores.SOUTH_RIGHT:
					this.carsInQueue(i, 296, 1, 0);
					break;
					
				// === WEST

				case Semaphores.WEST_FORWARD:
					this.carsInQueue(i, 250, 0, 1);
					break;

				case Semaphores.WEST_LEFT:
					this.carsInQueue(i, 204, 0, 1);
					break;

				case Semaphores.WEST_RIGHT:
					this.carsInQueue(i, 296, 0, 1);
					break;
					
				// === EAST

				case Semaphores.EAST_FORWARD:
					this.carsInQueue(i, 204, 0, 0);
					break;

				case Semaphores.EAST_LEFT:
					this.carsInQueue(i, 250, 0, 0);
					break;

				case Semaphores.EAST_RIGHT:
					this.carsInQueue(i, 158, 0, 0);
					break;
				
				
			}
			
		}
		
	}
	
	private void carsInQueue(int i, int start, int dle_y, int go_back){

		g.setColor(new Color(234, 171, 2));
		
		int posun_z = 0;
		
		// výpis aut
		
		for(int x = 0; x < this.cars[i] && x < 3; x++){
        	posun_z = go_back == 1 ? (x * 30) : (x * -30);
			
        	if(dle_y == 1){
        		g.fillRect(start + (46-18) / 2, (go_back == 1 ? 95 : 380) - posun_z, 18, 25);
        	}
        	else{
        		g.fillRect((go_back == 1 ? 95 : 380) - posun_z, start + (46-18) / 2, 25, 18);
        	}
			
		}
		
		// přidání popisku, když je moc aut
		
		if(this.cars[i] > 3){

	        g.setColor(new Color(204, 0, 0));
	        g.setFont(new Font("SansSerif", Font.BOLD, 16));
	        
	        int cars = this.cars[i] - 3;
	        
	        if(dle_y == 1 && go_back == 1){
	        	// NORTH
		        g.drawString("+" + Integer.toString(cars), start + (46-22) / 2, 20);
	        }
	        else if(dle_y == 1 && go_back == 0){
	        	// SOUTH
	        	g.drawString("+" + Integer.toString(cars), start + (46-22) / 2, 490);
	        }
	        else if(dle_y == 0 && go_back == 1){
	        	// WEST
	        	g.drawString("+" + Integer.toString(cars), 1, start + (46+10) / 2);
	        }
	        else if(dle_y == 0 && go_back == 0){
	        	// EAST
	        	g.drawString("+" + Integer.toString(cars), 471, start + (46+10) / 2);
	        }
		}
		
	}

}
