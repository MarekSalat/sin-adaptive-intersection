package cz.fit.sin.gui;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import cz.fit.sin.agents.CrossroadAgent;

public class GuiRoads extends JFrame{
	private static final long serialVersionUID = 7563499771061984518L;
	
	// ---
	//private ExecutorAgent 	  executorAgent;
	//private GuiRoadsGenerator roadsGenerator;
	private GuiRoadsSimpleGen roadsSimpleGen;
	
	// --- Menu
	private JMenuBar  jmenubar;
	private JMenuItem bar_help;
	private JMenuItem bar_about;
	
	// --- Listeners
	private ListenerAbout list_bar_about;
	
	// --- Layout
	private JPanel 		container, box;
	private JPanel 		content;
	
	private JButton		btnStart, btnStop, btnAddCar;
	
	private JTextField	roads_x, roads_y;
	private JLabel  	number_cars;

	public GuiRoads(final CrossroadAgent executorAgent) {
		super();
		
		// ---
		//this.executorAgent 	= executorAgent;
		
		// ---
		this.setTitle("SIN - Adaptive intersection");
		
		this.setMinimumSize(new Dimension(Gui.default_width, Gui.default_height));
		
		
		this.setResizable(false);
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ---
		this._initMenu();
		
		// ---
		this._setLayout();
		this.setVisible(true);
		
		// ---
		this._initDeafult();
		
		// CLOSE APLICATION
		addWindowListener(new WindowAdapter(){
			
			@Override
            public void windowClosing(WindowEvent e){
				
                System.out.println("Close simulation");
                e.getWindow().dispose();
            }
			
		});
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
	
	private void _setLayout(){

		Dimension screenSize = this.getToolkit().getScreenSize();
		
		// === SET MAIN CONTAINER
		this.container = new JPanel();
		this.container.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(this.container);
		//this.container.setLayout(new BoxLayout(this.container, BoxLayout.LINE_AXIS));
		this.container.setLayout(new BorderLayout());
		
		// === SET BOX WITH MENU & STATS
		this.box 	 = new JPanel();
		this.container.add(this.box, BorderLayout.WEST);

		// === SET CONTENT WITH ROADS
		this.content = new GuiRoadsSimpleGen();
		this.content.setMinimumSize(new Dimension(Gui.default_width, Gui.default_height));
		this.content.setPreferredSize(new Dimension(Gui.default_width, Gui.default_height));
		this.content.setMaximumSize(new Dimension(Gui.default_width, Gui.default_height));
		
		this.content.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
		this.content.setBackground(new Color(255, 255, 255));
		
		this.roadsSimpleGen = (GuiRoadsSimpleGen)this.content;
		
		this.container.add(this.content, BorderLayout.EAST);		

		// === -----------------------
		// === -----------------------
		// === -----------------------

		// === SÍ� KØIŽOVATEK --------

		//github.com/MarekSalat/SIN-Adaptive-intersection.git
		// === -----------------------
		// === -----------------------
		// === -----------------------

		/*
		JScrollPane pane = new JScrollPane(this.content);
		//pane.setBackground(new Color(0, 0, 0));
		pane.setBorder(BorderFactory.createEmptyBorder());
		this.container.add(pane);
        
		// === ADD TO CONTENT
		this.roadsGenerator = new GuiRoadsGenerator();
		this.roadsGenerator.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.roadsGenerator.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.roadsGenerator.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.roadsGenerator.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.content.add(this.roadsGenerator);
		*/

		// === -----------------------
		// === -----------------------
		// === -----------------------
		// === ADD GROUP LAYOUT TO BOX 
		// === -----------------------
		// === -----------------------
		// === -----------------------
        GroupLayout layout = new GroupLayout(this.box);
        this.box.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
                
        // === ADD ELEM TO BOX
		this.btnStart = new JButton("Start");
		btnStart.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnStart.addActionListener(new ListenerBtnStart());

		this.btnStop = new JButton("Stop");
		btnStop.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnStop.addActionListener(new ListenerBtnStop());

		this.btnAddCar = new JButton("Add car");
		btnAddCar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnAddCar.addActionListener(new ListenerBtnAddCar());
		
		// === ADD ELEM TO BOX - textfield
		this.roads_x = new JTextField(Gui.default_silnice_x);  
		this.roads_x.setHorizontalAlignment(JTextField.CENTER);
        this.roads_x.setPreferredSize( new Dimension( 30, 20 ) ); 
        this.roads_x.setMaximumSize(new Dimension(screenSize.width, 20));

		this.roads_y = new JTextField(Gui.default_silnice_y);  
		this.roads_y.setHorizontalAlignment(JTextField.CENTER);
        this.roads_y.setPreferredSize( new Dimension( 30, 20 ) ); 
        this.roads_y.setMaximumSize(new Dimension(screenSize.width, 20));
        
        // === ADD ELEM TO BOX - jlabel
        //JLabel label_1   = new JLabel("Roads - cols:");
        //JLabel label_2   = new JLabel("Roads - rows:");
        JLabel label_3   = new JLabel("Number of cars:");
        this.number_cars = new JLabel("0");
        this.number_cars.setMinimumSize(new Dimension( 30, 16 ));

        // do sloupeèku

        layout.setHorizontalGroup(layout.createSequentialGroup()

                .addGroup(layout.createParallelGroup(LEADING)
            			.addComponent(btnStart)
            			.addComponent(btnStop)
            			.addComponent(btnAddCar)
            			.addComponent(label_3)
                		 )
                 .addGroup(layout.createParallelGroup(LEADING)
              			.addComponent(this.number_cars)
              			//.addComponent(label_2)
                  		 )
                 /*
                 .addGroup(layout.createParallelGroup(LEADING)
             			.addComponent(this.roads_x)
             			.addComponent(this.roads_y)
                 		 )*/
        );
        
        layout.linkSize(SwingConstants.HORIZONTAL, btnStart, btnStop, btnAddCar);
        //layout.linkSize(SwingConstants.HORIZONTAL, this.roads_x, this.roads_y);
 

        // do øádku

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
					.addComponent(btnStart)
					//.addComponent(label_1)
					//.addComponent(this.roads_x)
		    		)
            .addGroup(layout.createParallelGroup(LEADING)
        			.addComponent(btnStop)
        			//.addComponent(label_2)
        			//.addComponent(this.roads_y)
            		)
            .addGroup(layout.createParallelGroup(LEADING)
        			.addComponent(btnAddCar)
            		)
            .addGroup(layout.createParallelGroup(LEADING)
            		.addComponent(label_3)
            		.addComponent(this.number_cars)
            		)		
        );
 
        pack();
	}
	
	private void _initDeafult(){
		
		// buttons
		this.btnStart.setEnabled(true);
        this.btnStop.setEnabled(false);
        this.btnAddCar.setEnabled(false);
        
        // text
        this.roads_x.setEditable(false);
        this.roads_y.setEditable(false);
        
        // stats
		this.number_cars.setText("0");
		
	}
	
	/* === Classes for listeners === */
	
	private class ListenerBtnStart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Start simulation");
			System.out.println("----------------");

			// buttons
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
	        btnAddCar.setEnabled(true);

	        // text
	        roads_x.setEditable(false);
	        roads_y.setEditable(false);
	        /*
	        int x,y;
	        try{
		        x = Integer.parseInt(roads_x.getText().toString());
		        y = Integer.parseInt(roads_y.getText().toString());
	        }
	        catch(NumberFormatException e){
	        	x = 0;
	        	y = 0;
	        }*/
	        
	        try{
	        	roadsSimpleGen.Generate();
	        	
	        	
	        	
	        	//executorAgent.prepareWorld(x, y);
	        }
	        catch(Exception e){
	        	System.out.println(e.toString());
	        	JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	        	
	        	
				System.out.println("----------------");
				System.out.println("Stop simulation");
				
	        	_initDeafult();
	        }
		}
	}
	
	private class ListenerBtnStop implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("----------------");
			System.out.println("Stop simulation");
			
			_initDeafult();
			
			try{
				roadsSimpleGen.ClearRoads();
	        }
	        catch(Exception e){
	        	System.out.println(e.getMessage());
	        	JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	        	_initDeafult();
	        }
		}
		
	}
	
	private class ListenerBtnAddCar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			int auta[] = new int[12];
			for(int i = 0; i < auta.length; i ++){
				auta[i] = new Random().nextInt(10);
			}
			setCars(auta);
		}
		
	}
	
	private class ListenerAbout implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			new MenuHelp().setVisible(true);
	        
		}
		
	}
	
	/* === ReDrawing === */
	
	public void setSemaphores(int semPhases[]){
		roadsSimpleGen.ChangeSemPhases(semPhases);
	}
	
	public void setCars(int cars[]){
		roadsSimpleGen.ChangeCars(cars);
		
		int count = 0;
		for (int i = 0; i < cars.length; i++){
			count += cars[i];
		}
		
		this.number_cars.setText(Integer.toString(count));
		System.out.println("Cars: " + count);
	}
	
	/**=========== MAIN
	 * @param args ====
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
