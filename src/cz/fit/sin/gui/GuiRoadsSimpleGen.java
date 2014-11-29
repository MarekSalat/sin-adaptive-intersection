package cz.fit.sin.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GuiRoadsSimpleGen extends JPanel{
	private static final long serialVersionUID = 2900831093884735835L;
	
	private GuiDrawingRoad drawingroad;
	private GuiDrawingCars drawingcars;
	
	public int semPhases[];
	public int countCars[];

	public GuiRoadsSimpleGen() {
		super();
		
		// SET PHASES
		
		this.semPhases = new int[12];
		this.defaultSemaphorePhases();
		
		// SET CARS
		
		this.countCars = new int[12];
		this.defaultCars();
	}
	
	public void generate(){
		
		// === mám jednu křižovatku, postačí border layout
		// === jinak by se hodil např grid layout ... řádky sloupce pro více křižovatek
		// === grid layout (int radku, int sloupcu, int horizontalni mezera, int vertikalni mezera)
		
		this.setBackground(new Color(86, 176, 26));	
		this.setLayout(new BorderLayout());	
		//this.setLayout(new GridLayout(1, 2));
		
		
		// === ROAD + SEMAPHORES

		
		this.drawingroad = new GuiDrawingRoad();
		this.drawingroad.setSemPhases(this.semPhases);
		
		this.drawingroad.setLayout(new BorderLayout());
		this.add(this.drawingroad, BorderLayout.CENTER);
		
		
		// === ROAD + SEMAPHORES
		
		this.drawingcars = new GuiDrawingCars();
		this.drawingcars.setCars(this.countCars);
		
		// do křižovatky přidáme auta
		this.drawingroad.add(this.drawingcars, BorderLayout.CENTER);

		// aktualizace
		this.repaint();
		this.updateUI();
	}
	
	private void defaultSemaphorePhases(){
		for (Integer index : Semaphores.MAPPING.keySet()) {
			this.semPhases[index] = 0;
		}
		
	}
	
	public void changeSemPhases(int semPhases[]){
		
		for(int i = 0; i < this.semPhases.length && i < semPhases.length; i++){
			this.semPhases[i] = semPhases[i];
		}

		this.drawingroad.repaint();
		
		System.out.println("Change semaphore phases");
	}
	
	public void changeIconPhases(JLabel imagephase, int phase){
		String img 			  	= Gui.PHASE_NO;
		BufferedImage oldImage 	= null;
		BufferedImage newImage 	= null;
		int rotation			= 0;
		
		if(this.semPhases == null){
			System.out.println("Nejsou nastaveny semafory!");
			return;
		}
		
		System.out.println("Change on: " + phase);
		
		switch(phase){
		
			case 0:
				img		 = Gui.PHASE_SIMPLE;
				rotation = 180; // naopak
				break;

			case 1:
				img		 = Gui.PHASE_SIMPLE;
				rotation = -90;
				break;
				
			case 2:
				img		 = Gui.PHASE_SIMPLE;
				rotation = 0;
				break;

			case 3:
				img		 = Gui.PHASE_SIMPLE;
				rotation = 90;
				break;

			case 4:
				img		 = Gui.PHASE_FORWARD;
				rotation = 180;
				break;

			case 5:
				img		 = Gui.PHASE_FORWARD;
				rotation = -90;
				break;

			case 6:
				img		 = Gui.PHASE_LEFT;
				rotation = 180;
				break;
				
			case 7:
				img		 = Gui.PHASE_LEFT;
				rotation = -90;
				break;

			case 8:
				img		 = Gui.PHASE_LEFT_RIGHT;
				rotation = 180;
				break;

			case 9:
				img		 = Gui.PHASE_LEFT_RIGHT;
				rotation = -90;
				break;

			case 10:
				img		 = Gui.PHASE_LEFT_RIGHT;
				rotation = 0;
				break;

			case 11:
				img		 = Gui.PHASE_LEFT_RIGHT;
				rotation = +90;
				break;

			case 12:
				img		 = Gui.PHASE_RIGHT_FORWARD;
				rotation = 180;
				break;

			case 13:
				img		 = Gui.PHASE_RIGHT_FORWARD;
				rotation = -90;
				break;
				
			case -1:
			default:
				img		 = Gui.PHASE_NO;
				rotation = 0;
				break;
		}
		
		try {
			oldImage = ImageIO.read(new File(img));
			newImage = new BufferedImage(oldImage.getHeight(), oldImage.getWidth(), oldImage.getType());
			Graphics2D graphics = (Graphics2D) newImage.getGraphics();
			graphics.rotate(Math.toRadians(rotation), newImage.getWidth() / 2, newImage.getHeight() / 2);
			graphics.drawImage(oldImage, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);

			
			imagephase.setIcon(new ImageIcon(newImage));
		} 
		catch (IOException e) {
			
		}
		// --
	}
	
	private void defaultCars(){
		for (Integer index : Semaphores.MAPPING.keySet()) {
			this.countCars[index] = 0;
		}
	}
	
	public void changeCars(int cars[]){

		for(int i = 0; i < this.countCars.length && i < cars.length; i++){
			this.countCars[i] = cars[i];
		}

		this.drawingroad.repaint();
		
		System.out.println("Change cars");
	}
		
	public void clearRoads(){

		this.defaultSemaphorePhases();
		this.defaultCars();
		
		this.setBackground(new Color(255, 255, 255));
		this.removeAll(); 
		this.updateUI();
	}
	
}
