package cz.fit.sin.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import cz.fit.sin.agents.ExecutorAgent;

public class GuiRoads extends JFrame{
	private static final long serialVersionUID = 7563499771061984518L;

	// ---
	private GuiRoads gui;
	private ExecutorAgent mainAgent;
	
	// --- Bar
	private JMenuBar  jmenubar;
	private JMenuItem bar_help;
	private JMenuItem bar_about;
	
	// --- Listeners
	private ListenerAbout list_bar_about;

	public GuiRoads(final ExecutorAgent mainAgent) {
		super();
		
		// ---
		this.gui 		= this;
		this.mainAgent 	= mainAgent;
		
		// ---
		this.setTitle("SIN - Adaptive intersection");
		this.setSize(700, 400);
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ---
		this._initMenu();
		
		// ---
		this._setLayout();
		this.setVisible(true);
		
		// ---
		this._initDefaultComponents();
	}
	
	private void _setLayout(){
		
	}
	
	private void _initMenu(){
		this.jmenubar = new JMenuBar();
		
		this.bar_help = new JMenu("Help");
		
		this.bar_about  	= new JMenuItem("About");
		this.list_bar_about = new ListenerAbout();
		this.bar_about.addActionListener(this.list_bar_about);
		this.bar_help.add(this.bar_about);
		
		// add to bar
		this.jmenubar.add(this.bar_help);

		this.setJMenuBar(this.jmenubar);
	}
	
	private void _initDefaultComponents(){
		
	}
	
	/* === Classes for listeners === */
	
	public class ListenerAbout implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			// okno pro help
	        
		}
	}
	
	/**=========== MAIN
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				GuiRoads w = new GuiRoads(null);
				w.setVisible(true);
			}
			
		});
	}

}
