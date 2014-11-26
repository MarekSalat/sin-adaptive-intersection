package cz.fit.sin.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


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
		/*
		this.setShowHorizontalLines(false);
		this.setShowVerticalLines(false);
		this.setShowGrid(false);
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);*/
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
		
		resizeTable();
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
			
			
			
			
			
			/*
			
			// Others
			else {
				row -= 1;
				column -= 1;
				if (row % 3 == 0 && column % 3 == 0) {
					setIcon(null);
					setBackground(Color.WHITE);
				} else if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
					setIcon(null);
					if (value == null) {
						setBackground(Color.GREEN);
					} else {
						String[] temp;
						String act;
						String max;
						temp = ((String) value).split("/");
						try { // pri zobrazeni obsazenosti silnice vedouci do koncovky hazi pristup mimo pole
							act = temp[0];
							max = temp[1];
							setBackground(interpolateColorFromValue(Integer.parseInt(act), Integer.parseInt(max)));
						} catch (java.lang.ArrayIndexOutOfBoundsException x) {
							setBackground(Color.GREEN);
						}
					}
					// vykreslen isemaforu do bunek prezentujicich silnice
					if (column % 3 == 1 && row % 3 == 0 && row < table.getRowCount() - 3) { // prijezd ze severu
						String v = (String)table.getValueAt(row + 2, column + 1);
						if (v != null) {
							int sem = Integer.parseInt(v);
							drawSemaphoreToCell(sem);
							setVerticalAlignment(SwingConstants.BOTTOM);
						}
					} else if (column % 3 == 2 && row % 3 == 0 && row > 1) { // prijezd z jihu
						String v = (String)table.getValueAt(row, column + 1);
						if (v != null) {
							int sem = Integer.parseInt(v);
							drawSemaphoreToCell(sem);
							setVerticalAlignment(SwingConstants.TOP);
						}
					} else if (column > 1 && (column % 3 == 0) && (row % 3 == 1)) { // prijezd z vychodu
						String v = (String)table.getValueAt(row + 1, column);
						if (v != null) {
							int sem = Integer.parseInt(v);
							drawSemaphoreToCell(sem);
							setHorizontalAlignment(SwingConstants.LEFT);
						}
					} else if (column % 3 == 0 && row % 3 == 2 && column < table.getColumnCount() - 3) { // prijezd ze zapadu
						
						String v = (String)table.getValueAt(row + 1, column + 2);
						if (v != null) {
							int sem = Integer.parseInt(v);
							drawSemaphoreToCell(sem);
							setHorizontalAlignment(SwingConstants.RIGHT);
						}
					}
				} else if (row % 3 != 0 && column % 3 != 0) {
					setIcon(null); // jak kurva fungují podmínky v Javì?!!?
					setBackground(Color.GRAY);
				}
			}
			
			*/
			return this;
			
		}
		
		
	}
	

	

	private Color interpolateColorFromValue(int actCars, int maxCars) {
		float carsf = actCars / (float) maxCars;

		// Green color channel
		float origGreenRChannel = Color.GREEN.getRed() / 255f;
		float origGreenGChannel = Color.GREEN.getGreen() / 255f;
		float origGreenBChannel = Color.GREEN.getBlue() / 255f;
		float origGreenAChannel = Color.GREEN.getAlpha() / 255f;

		// Red color channel
		float origRedRChannel 	= Color.RED.getRed() / 255f;
		float origRedGChannel 	= Color.RED.getGreen() / 255f;
		float origRedBChannel 	= Color.RED.getBlue() / 255f;
		float origRedAChannel 	= Color.RED.getAlpha() / 255f;

		// Interpolate color
		float red = origRedRChannel * carsf + origGreenRChannel * (1f - carsf);
		float green = origRedGChannel * carsf + origGreenGChannel * (1f - carsf);
		float blue = origRedBChannel * carsf + origGreenBChannel * (1f - carsf);
		float alpha = origRedAChannel * carsf + origGreenAChannel * (1f - carsf);


		return new Color(red, green, blue, alpha);
	}
	

	private void drawSemaphoreToCell(int sem) {
		switch (sem) {/*
		case Constants.GREEN:
			setIcon(new ImageIcon("images/crossroad_green.gif"));
			break;
		case Constants.RED:
			setIcon(new ImageIcon("images/crossroad_red.gif"));
			break;
		case Constants.ORANGE:
			setIcon(new ImageIcon("images/crossroad_orange.gif"));
			break;*/
		default:
			setBackground(Color.GRAY);
			break;
		}
	}
	
	
	private static final int endpointSize 	= 20;		// blue
	private static final int crossSize 		= 20;		// sirka krizovatky / silnice
	private static final int roadSize 		= 80;		// delka silnice
	

	/**
	 * Delete everything from table and generate new table with new params (width, height)
	 * @param width
	 * @param height
	 */
	
	
	
	// ZMÌNA VELIKOSTI


	/**
	 * It should resible table to constant values but DOESNT WORK
	 */
	private void resizeTable() {
		int rows = this.getRowCount();
		int cols = this.getColumnCount();
		int sizeHeight;
		
		this.setRowHeight(0, endpointSize);
		this.setRowHeight(rows - 1, endpointSize);
		sizeHeight = 2*endpointSize;
		for (int i = 1; i < rows - 1; i++) {
			if (i % 3 == 1) {
				this.setRowHeight(i, roadSize);
				sizeHeight += roadSize;
			} else {
				this.setRowHeight(i, crossSize);
				sizeHeight += crossSize;
			}
		}
		
		this.getColumnModel().getColumn(0).setMinWidth(1);
		this.getColumnModel().getColumn(0).setMaxWidth(endpointSize);
		this.getColumnModel().getColumn(0).setPreferredWidth(endpointSize);
		this.getColumnModel().getColumn(cols - 1).setMinWidth(1);
		this.getColumnModel().getColumn(cols - 1).setMaxWidth(endpointSize);
		this.getColumnModel().getColumn(cols - 1).setPreferredWidth(endpointSize);
		for (int i = 1; i < cols - 1; i++) {
			this.getColumnModel().getColumn(i).setMinWidth(1);
			if (i % 3 == 1) {	
				this.getColumnModel().getColumn(i).setPreferredWidth(roadSize);
				this.getColumnModel().getColumn(i).setMaxWidth(roadSize);
			} else {
				this.getColumnModel().getColumn(i).setPreferredWidth(crossSize);
				this.getColumnModel().getColumn(i).setMaxWidth(crossSize);
			}
		}
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setBounds(5, 109, this.getColumnModel().getTotalColumnWidth(), sizeHeight);
		this.setPreferredSize(new Dimension(this.getColumnModel().getTotalColumnWidth(), sizeHeight));
	}

	/**
	 * Update value at all fronts
	 * @param x
	 * @param y
	 */
	/*
	public void updateCrossRoadAt(CrossroadStatus s) {
		String name = s.name;

		int x = s.position.x;
		int y = s.position.y;

		x *= 3;
		y *= 3;

		for (int dir : Constants.DIRECTIONS) {
			CoordRoad coordRoad = null;
			CoordRoad coordSem = null;
			switch (dir) {
				case Constants.WEST:
					coordRoad = coordCrossRoadWest(x, y);
					coordSem = coordCrossSemWest(x, y);
					break;
				case Constants.EAST:
					coordRoad = coordCrossRoadEast(x, y);
					coordSem = coordCrossSemEast(x, y);
					break;
				case Constants.NORTH:
					coordRoad = coordCrossRoadNorth(x, y);
					coordSem = coordCrossSemNorth(x, y);
					break;
				case Constants.SOUTH:
					coordRoad = coordCrossRoadSouth(x, y);
					coordSem = coordCrossSemSouth(x, y);

					break;
				default:
					break;

			}

			int actualLenght = s.directions[dir].carQueue.size();
			int maxLength = s.directions[dir].maximumLength;

			String valueRoadString = actualLenght + "/" + maxLength;
			String valueSemString = Integer.toString(s.directions[dir].semaphore);

			if (coordRoad != null) {
				tableModel.setValueAt(valueRoadString, coordRoad.getY(), coordRoad.getX());
			}
			if (coordSem != null) {
				tableModel.setValueAt(valueSemString, coordSem.getY(), coordSem.getX());
			}
		}
	}




		// PRO UKLÁDÁNÍ


	/****************************************************/
	/*************** COORDINATES IN CITY ****************/
	/****************************************************/
	private CoordRoad coordCrossSemSouth(int x, int y) {
		return coordCrossRoadSouth(x, y - 1);
	}

	private CoordRoad coordCrossSemNorth(int x, int y) {
		return coordCrossRoadNorth(x, y + 1);
	}

	private CoordRoad coordCrossSemEast(int x, int y) {
		return coordCrossRoadEast(x - 1, y);
	}

	private CoordRoad coordCrossSemWest(int x, int y) {
		return coordCrossRoadWest(x + 1, y);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadWest(int x, int y) {

		return new CoordRoad(x + 1, y + 3);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadEast(int x, int y) {
		return new CoordRoad(x + 4, y + 2);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadNorth(int x, int y) {
		return new CoordRoad(x + 2, y + 1);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadSouth(int x, int y) {
		return new CoordRoad(x + 3, y + 4);
	}



	/**
	 * To store X, Y coord of roads of crossRoad
	 * @author xsych_000
	 *
	 */
	
	public class CoordRoad {
		private int x;
		private int y;

		public CoordRoad(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

}
