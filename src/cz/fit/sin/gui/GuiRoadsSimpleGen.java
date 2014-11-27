package cz.fit.sin.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GuiRoadsSimpleGen extends JPanel{
	private static final long serialVersionUID = 2900831093884735835L;
	
	private GuiDrawingRoad drawingroad;
	private GuiSemafores   semafores;
	
	public int semPhases[];

	public GuiRoadsSimpleGen() {
		super();
		
		this.semPhases = new int[12];

		this.semPhases[Semaphores.NORTH_FORWARD] = 0; 
		this.semPhases[Semaphores.NORTH_LEFT]    = 0; 
		this.semPhases[Semaphores.NORTH_RIGHT]   = 0; 

		this.semPhases[Semaphores.NORTH_FORWARD] = 0; 
		this.semPhases[Semaphores.NORTH_LEFT]    = 0; 
		this.semPhases[Semaphores.NORTH_RIGHT]   = 0; 

		this.semPhases[Semaphores.WEST_FORWARD]  = 1; 
		this.semPhases[Semaphores.WEST_LEFT]     = 1; 
		this.semPhases[Semaphores.WEST_RIGHT]    = 1; 

		this.semPhases[Semaphores.EAST_FORWARD]  = 1; 
		this.semPhases[Semaphores.EAST_LEFT]     = 1; 
		this.semPhases[Semaphores.EAST_RIGHT]    = 1; 
		
	}
	
	public void Generate(){
		Dimension size = this.getSize();
		System.out.println(size.width + " " + size.height);
		
		// === mám jednu křižovatku, postačí border layout
		// === jinak by se hodil např grid layout ... řádky sloupce pro více křižovatek
		// === grid layout (int radku, int sloupcu, int horizontalni mezera, int vertikalni mezera)
		
		this.setBackground(new Color(86, 176, 26));	
		this.setLayout(new BorderLayout());	
		//this.setLayout(new GridLayout(1, 2));
		
		
		// === ROAD

		
		drawingroad = new GuiDrawingRoad();
		drawingroad.setLayout(new BorderLayout());
		this.add(drawingroad, BorderLayout.CENTER);
		
		// na křižovatku přidáme semafory
		
		this.semafores = new GuiSemafores();
		this.semafores.setSemPhases(this.semPhases);
		drawingroad.add(this.semafores, BorderLayout.CENTER);

		this.repaint();
		this.updateUI();
	}
	
	public void ChangeSemPhases(){

		System.out.println("- change sem phases ");
		
		//drawingroad.setLocation(50, 40);
		

		for(int i = 0; i < this.semPhases.length; i++){
		
			if(this.semPhases[i] == 1){
				this.semPhases[i] = 0;
			}
			else{
				this.semPhases[i] = 1;
			}
			
			//this.updateUI();
			this.semafores.repaint();
			//this.semafores.setSemPhases(this.semPhases);
		}
		
	}
		
	public void ClearRoads(){
		this.setBackground(new Color(255, 255, 255));
		this.removeAll(); 
		this.updateUI();
	}
	
}
