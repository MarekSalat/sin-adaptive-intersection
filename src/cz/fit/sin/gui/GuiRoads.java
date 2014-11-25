package cz.fit.sin.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GuiRoads extends JFrame{
	private static final long serialVersionUID = 7563499771061984518L;

	// ---
	private GuiRoads gui;

	public GuiRoads() {
		super();
		
		// ---
		this.gui = this;
		
		// ---
		this.setTitle("SIN - Adaptive intersection");
		this.setSize(700, 400);
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ---
		this.initMenu();
		
		// ---
		this._setLayout();
		this.setVisible(true);
		
		// ---
		this.InitDefaultComponents();
	}
	
	private void _setLayout(){
		
	}
	
	private void initMenu(){
		
	}
	
	private void InitDefaultComponents(){
		
	}
	
	
	/**=========== MAIN
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				GuiRoads w = new GuiRoads();
				w.setVisible(true);
			}
			
		});
	}

}
