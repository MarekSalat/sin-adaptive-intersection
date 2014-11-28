package cz.fit.sin.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		this.DefaultsemPhases();
		
		// SET CARS
		
		this.countCars = new int[12];
		this.DefaultCars();
	}
	
	public void Generate(){
		
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
	
	private void DefaultsemPhases(){

		this.semPhases[Semaphores.NORTH_FORWARD] = 0; 
		this.semPhases[Semaphores.NORTH_LEFT]    = 0; 
		this.semPhases[Semaphores.NORTH_RIGHT]   = 0; 

		this.semPhases[Semaphores.SOUTH_FORWARD] = 0; 
		this.semPhases[Semaphores.SOUTH_LEFT]    = 0; 
		this.semPhases[Semaphores.SOUTH_RIGHT]   = 0; 

		this.semPhases[Semaphores.WEST_FORWARD]  = 0; 
		this.semPhases[Semaphores.WEST_LEFT]     = 0; 
		this.semPhases[Semaphores.WEST_RIGHT]    = 0; 

		this.semPhases[Semaphores.EAST_FORWARD]  = 0; 
		this.semPhases[Semaphores.EAST_LEFT]     = 0; 
		this.semPhases[Semaphores.EAST_RIGHT]    = 0; 
		
	}
	
	public void ChangeSemPhases(int semPhases[]){
		
		for(int i = 0; i < this.semPhases.length && i < semPhases.length; i++){
			this.semPhases[i] = semPhases[i];
		}

		this.drawingroad.repaint();
		
		System.out.println("Change semaphore phases");
	}
	
	public void ChangeIconPhases(JLabel imagephase, int phase){
		String img 			  	= Gui.phase_no;
		BufferedImage oldImage 	= null;
		BufferedImage newImage 	= null;
		int rotation			= 0;
		
		if(this.semPhases == null){
			System.out.println("Nejsou nastaveny semafory!");
			return;
		}
		
		switch(phase){
		
			case 0:
				img		 = Gui.phase_simple;
				rotation = 180; // naopak
				break;

			case 1:
				img		 = Gui.phase_simple;
				rotation = 90;
				break;
				
			case 2:
				img		 = Gui.phase_simple;
				rotation = 0;
				break;

			case 3:
				img		 = Gui.phase_simple;
				rotation = -90;
				break;

			case 4:
				img		 = Gui.phase_forward;
				rotation = 180;
				break;

			case 5:
				img		 = Gui.phase_forward;
				rotation = -90;
				break;

			case 6:
				img		 = Gui.phase_left;
				rotation = 180;
				break;
				
			case 7:
				img		 = Gui.phase_left;
				rotation = -90;
				break;

			case 8:
				img		 = Gui.phase_left_right;
				rotation = 180;
				break;

			case 9:
				img		 = Gui.phase_left_right;
				rotation = -90;
				break;

			case 10:
				img		 = Gui.phase_left_right;
				rotation = 0;
				break;

			case 11:
				img		 = Gui.phase_left_right;
				rotation = +90;
				break;

			case 12:
				img		 = Gui.phase_right_forward;
				rotation = 180;
				break;

			case 13:
				img		 = Gui.phase_right_forward;
				rotation = -90;
				break;
				
			case -1:
			default:
				img		 = Gui.phase_no;
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
	
	private void DefaultCars(){

		this.countCars[Semaphores.NORTH_FORWARD] = 0; 
		this.countCars[Semaphores.NORTH_LEFT]    = 0; 
		this.countCars[Semaphores.NORTH_RIGHT]   = 0; 

		this.countCars[Semaphores.SOUTH_FORWARD] = 0; 
		this.countCars[Semaphores.SOUTH_LEFT]    = 0; 
		this.countCars[Semaphores.SOUTH_RIGHT]   = 0; 

		this.countCars[Semaphores.WEST_FORWARD]  = 0; 
		this.countCars[Semaphores.WEST_LEFT]     = 0; 
		this.countCars[Semaphores.WEST_RIGHT]    = 0; 

		this.countCars[Semaphores.EAST_FORWARD]  = 0; 
		this.countCars[Semaphores.EAST_LEFT]     = 0; 
		this.countCars[Semaphores.EAST_RIGHT]    = 0; 
		
	}
	
	public void ChangeCars(int cars[]){

		for(int i = 0; i < this.countCars.length && i < cars.length; i++){
			this.countCars[i] = cars[i];
		}

		this.drawingroad.repaint();
		
		System.out.println("Change cars");
	}
		
	public void ClearRoads(){

		this.DefaultsemPhases();
		this.DefaultCars();
		
		this.setBackground(new Color(255, 255, 255));
		this.removeAll(); 
		this.updateUI();
	}
	
}
