import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

//Michael Dobrzanski

public class StarsAndStripes {
	
	public static void drawFlag(int stars, int stripes, java.awt.Graphics g, int x, int y, int width, int height) {
        //white background
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		 
		 //stripes
		int stripeHeight = height / stripes;
		for(int i = 0; i < stripes; i += 2) {
	        if(i != stripes - 1){
	          g.setColor(Color.RED);
	          g.fillRect(x, y + i * stripeHeight, width, stripeHeight); 	
	          } 
		} 
		
		 //fill last stripe if stripes = odd
        if(stripes % 2 != 0) {
        	g.setColor(Color.RED);
	        g.fillRect(x, (stripes - 1) * stripeHeight, width, stripeHeight);	
        }

	     //star field
	     g.setColor(Color.BLUE);
	     int redStripes = (int)Math.ceil(stripes / 2.0);
	     int starfieldHeight = stripeHeight * redStripes;
	     int starfieldWidth = starfieldHeight * width / height;
	     g.fillRect(x, y, starfieldWidth, starfieldHeight);
	     
	    //columns and rows
	 	//stars divided by columns equals rows; columns > rows but < 2*rows; stars = columns * rows
	     int columns = 2;
         int rows = 1;
	     for(rows = 1; rows < 10; rows++) {
		       if(columns < stars / rows || stars % rows != 0) {
			     	  columns++;
			       }
		       if(columns * rows == stars && columns > rows && columns < 2 * rows) {
		    	   break;
		       }
	     }
	     //check star, column, and row count
	    //System.out.println("Stars: " + stars + " Columns: " + columns + " Rows: " + rows);
	     
		//star area = starWidth * starHeight
	 	int starWidth = starfieldWidth / columns;
	 	int starHeight = starfieldHeight / rows;
	 	//starScale = smaller of starWidth or starHeight
	 	int starScale = 0;
	 	if (starWidth > starHeight) {
	 	    starScale = starHeight;
	 	}
	 	else {
	 		starScale = starWidth;
	 	}
		// star grid
	 	// centering - create margins? 
	 	// x/y + (starfieldWidth/Height - (columns/rows * starScale) / 2) : stacks stars
		for(int w = 0; w < columns; w++) {
			for(int h = 0; h < rows; h++) {
		     drawStar(g, x + (w * starScale), y + (h * starScale), starScale);
			}
		}
	}
	public static void drawStar(java.awt.Graphics g, int x, int y, int size) { 
		g.setColor(Color.WHITE);
		 g.drawLine(x + (size * 1 / 5), y + size, x + size, y + (size * 2 / 5));
		 g.drawLine(x + size, y + (size * 2 / 5), x, y + (size * 2 / 5));
		 g.drawLine(x, y + (size * 2 / 5), x + (size * 4 / 5), y + size);
		 g.drawLine(x + (size * 4 / 5), y + size, x + (size / 2), y);
		 g.drawLine(x + (size / 2), y, x + (size * 1 / 5), y + size);	        
	}
	// Only alter the "drawFlag" part of the paintComponent
	// code to call it in different ways. You can also test
	// drawing multiple flags at once!
	public static void main(String[] args) {
		JFrame window = new JFrame("Graphics window");
		window.setLocationByPlatform(true);
		final JLabel coords = new JLabel(" ");
		@SuppressWarnings("serial")
		final JPanel panel = new JPanel() {

			protected void paintComponent(Graphics gx) {
				coords.setText(" ");
				Graphics2D g = (Graphics2D) gx;
				int width = getWidth();
				int height = getHeight();
				g.setBackground(Color.GREEN); // To make sure you cover the base rectangle!
				g.clearRect(0, 0, width, height);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setColor(Color.BLACK);

				// You could alter this code to try different flags!
				drawFlag(15, 13, g, 0, 0, width/2, height/2);
				drawFlag(20, 14, g, width/2, 0, width/2, height/2);
				drawFlag(24, 15, g, 0, height/2, width/2, height/2);
				drawFlag(48, 16, g, width/2, height/2, width/2, height/2);
			}
		};
		panel.addMouseMotionListener(new MouseMotionListener() {


			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				coords.setText(e.getX()+", "+e.getY());				
			}
			
		});
		window.setLayout(new BorderLayout());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(d.width / 2, d.height / 2);

		JPanel coordPanel = new JPanel();
		coordPanel.setLayout(new BorderLayout());
		coordPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		window.add(coordPanel, BorderLayout.SOUTH);
		coordPanel.add(coords, BorderLayout.WEST);
		
		window.setBackground(Color.WHITE); // To make sure you cover the base rectangle!		
		panel.setBackground(Color.BLACK);
		window.add(panel, BorderLayout.CENTER);
		//window.setContentPane(panel);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
