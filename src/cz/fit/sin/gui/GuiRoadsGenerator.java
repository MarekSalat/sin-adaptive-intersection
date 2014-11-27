package cz.fit.sin.gui;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/* ===========================================
 * ======== SÌù k¯iûovatek - generov·nÌ ======
 * ===========================================
 */
class GuiRoadsGenerator extends JTable {
	private static final long serialVersionUID = 8506432666450680154L;
	
	// --- count roads
	private int x;
	private int y;
	private int cols;
	private int rows;
	
	// --- 
	private DefaultTableModel model;
	
	public GuiRoadsGenerator() {
		super();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void ClearRoads(){
		this.model = (DefaultTableModel) this.getModel();
		this.model.setRowCount(0);
	}
		
	public void Generate(int x, int y) throws Exception{
		this.x = (int)x;
		this.y = (int)y;

		System.out.println("X: " + this.x + " Y: " + this.y);
		
		if(this.x < 1 || this.y < 1){
			throw new Exception("Error of intersections: (x || y) <= 0");
		}

		this.cols = 3 + 3 * x;
		this.rows = 3 + 3 * y; 
		
		System.out.println("Cols: " + this.cols + " Rows: " + this.rows);
		
		this.GenerateTable();
		Cells bunky = new Cells();
		this.setDefaultRenderer(Object.class, bunky);
		
		this.setRoadsSize();
		this.setTableSettings();
	}
	
	private void GenerateTable(){
		this.model = new DefaultTableModel(this.rows, this.cols);
		this.setModel(this.model);
	}
	
	private class Cells extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 430870298637547435L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			
			// Borders
			if(  (row == 0 || col == 0 || row == rows-1 || col == cols-1)
				  && (
						  ((row % 3 == 0 && col == 0  	  && row != 0) || ((row+1) % 3 == 0 && col == 0 	 && row+1 != rows))  
						  ||
						  ((row % 3 == 0 && col == cols-1 && row != 0) || ((row+1) % 3 == 0 && col == cols-1 && row+1 != rows))
						  ||
						  ((col % 3 == 0 && row == 0  	  && col != 0) || ((col+1) % 3 == 0 && row == 0 	 && col+1 != cols)) 
						  ||
						  ((col % 3 == 0 && row == rows-1 && col != 0) || ((col+1) % 3 == 0 && row == rows-1 && col+1 != cols))
					  )
					
			){
				try{
					setBackground(Color.BLUE);
				}
				catch(Exception e){
					setBackground(Color.WHITE);
				}
			}
			else{
				setBackground(Color.WHITE);
			}
						
			// Intersection
			if(  (row != 0 && col != 0 && row != rows-1 && col != cols-1)
			      && (
			    		  (row % 3 == 0 && col % 3 == 0)
			    		  ||
			    		  ((row+1) % 3 == 0 && (col+1) % 3 == 0)
			    		  ||
			    		  ((row+1) % 3 == 0 && (col) % 3 == 0)
			    		  ||
			    		  ((row) % 3 == 0 && (col+1) % 3 == 0)
			    	  )
			){
				try{
					setBackground(Color.GREEN);
				}
				catch(Exception e){
					setBackground(Color.WHITE);
				}
			}
			//else
			
			// Roads
			if(  (row != 0 && col != 0 && row != rows-1 && col != cols-1)
				      && (
				    		  ((row-1) % 3 == 0 && (col) % 3 == 0)
				    		  ||
				    		  ((row) % 3 == 0   && (col-1) % 3 == 0)
				    		  ||
				    		  ((row-1) % 3 == 0   && (col-2) % 3 == 0)
				    		  ||
				    		  ((row-2) % 3 == 0   && (col-1) % 3 == 0)
				    	  )
			){
				try{
					setBackground(Color.GRAY);
				}
				catch(Exception e){
					setBackground(Color.WHITE);
				}
			}
			
			return this;
			
		}
		
		
	}
	
	private void setRoadsSize(){
		
		// SET ROWS
		for(int i = 0; i < this.rows; i++){
			if(i == 0){
				System.out.print("\nprvni ");
			}
			else if(i == this.rows - 1){
				System.out.print("posledni\n");
			}
			else if((i-1) % 3 == 0){
				System.out.print(i + " ");
				
				this.setRowHeight(i, Gui.delka_silnice);
			}
			else{
				//System.out.print(i + " ");
				
				this.setRowHeight(i, Gui.sirka_silnice);
			}
		}
		
		// SET COLS
		for(int i = 0; i< this.cols; i++){
			if(i == 0){
				System.out.print("\nprvni ");
			}
			else if(i == this.rows - 1){
				System.out.print("posledni\n");
			}
			else if((i-1) % 3 == 0){
				System.out.print(i + " ");
				
				this.getColumnModel().getColumn(i).setPreferredWidth(Gui.delka_silnice);
			}
			else{
				//System.out.print(i + " ");

				this.getColumnModel().getColumn(i).setPreferredWidth(Gui.sirka_silnice);
			}
		}
		
		// BORDER
		this.setRowHeight(0, 			 Gui.konec_silnice);
		this.setRowHeight(this.rows - 1, Gui.konec_silnice);

		this.getColumnModel().getColumn(0)				.setMinWidth		(Gui.konec_silnice);
		this.getColumnModel().getColumn(0)				.setPreferredWidth	(Gui.konec_silnice);
		this.getColumnModel().getColumn(0)				.setMaxWidth		(Gui.konec_silnice);
		this.getColumnModel().getColumn(this.rows - 1)	.setMinWidth		(Gui.konec_silnice);
		this.getColumnModel().getColumn(this.rows - 1)	.setPreferredWidth	(Gui.konec_silnice);
		this.getColumnModel().getColumn(this.rows - 1)	.setMaxWidth		(Gui.konec_silnice);
	}
	
	private void setTableSettings(){
		//this.setShowHorizontalLines(false);
		//this.setShowVerticalLines(false);
		//this.setShowGrid(false);
		//this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setBorder(new LineBorder(new Color(255,255,255)));
	}
	
	
	
	
	
}
