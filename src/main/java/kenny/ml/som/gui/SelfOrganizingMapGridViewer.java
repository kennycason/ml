package kenny.ml.som.gui;

import kenny.ml.som.SelfOrganizingMap2D;
import kenny.ml.som.SelfOrganizingMapConfig;
import kenny.ml.som.features.AbstractWeightVector;
import kenny.ml.som.features.WeightVector;
import kenny.ml.som.map.AbstractMapLocation;
import kenny.ml.som.save.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class SelfOrganizingMapGridViewer extends Canvas {

	SelfOrganizingMap2D som;
	
	SelfOrganizingMapConfig config;
	
	private int squareSize = 4;
	
	private int dim = 800;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The stragey that allows us to use accelerate page flipping */
	
	private BufferStrategy strategy;
	
	public static void main(String argv[]) {
		SelfOrganizingMapGridViewer somv =new SelfOrganizingMapGridViewer();

		somv.init();
		
		somv.run();
	}
	
	public SelfOrganizingMapGridViewer() {
		// create a frame 
		JFrame container = new JFrame("Self Organizing Map 2D Grid Viewer");
				
		// get hold the content of the frame and set up the 
		// resolution
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(dim, dim));
		panel.setLayout(null);
				
		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, dim,  dim);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit 
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new KeyInputHandler());
		requestFocus();

		// create the buffering strategy which will allow AWTfw.write(
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
	}
	
	public void init() {
		config = new SelfOrganizingMapConfig();
		config.weightVectorDimension = 2;
		config.radius = 75;
		config.dimX = 40;
		config.dimY = 40;
		
	//	Random r = new Random();
		
		
		/*config.samples = new AbstractWeightVector[7];
		for(int i = 0; i < config.samples.length; i++) {
			config.samples[i] = new WeightVectorND(-0.5 + r.nextDouble(), -0.5 + r.nextDouble());
		}*/
		config.samples = new AbstractWeightVector[] {
				new WeightVector(-.5, -.5),
				new WeightVector(-.5, .5),
				new WeightVector(.5, -.5),
				new WeightVector(.5, .5),
			/*	new WeightVectorND(-0.5 + r.nextDouble(), -0.5 + r.nextDouble()),
				new WeightVectorND(-0.5 + r.nextDouble(), -0.5 + r.nextDouble()),
				new WeightVectorND(-0.5 + r.nextDouble(), -0.5 + r.nextDouble()),
				new WeightVectorND(-0.5 + r.nextDouble(), -0.5 + r.nextDouble()),	*/
		};

		som = new SelfOrganizingMap2D(config);
	}
	
	public void run() {
		
		int MAX_ITER = 500;
		double t = 0.0;
		double T_INC = 1.0 / MAX_ITER;
		
		int iter = 1;
		System.out.println("start learning:");
		while (true) {
			if (t < 1.0) {
				System.out.println("iteration: " + iter);
				for(int i = 0; i < som.getSampleSize(); i++) {
					// get a sample
					AbstractWeightVector sample = som.getSample(i);
					
					// find it's best matching unit
					AbstractMapLocation loc = som.calculateBestMatchingUnit(sample);
	
					// scale the neighbors according to t
					som.scaleNeighbors(loc, sample, t);
				}
				
				// increase t to decrease the number of neighbors and the amount
				// each weight can learn
				t += T_INC;
				
				iter++;
				
				draw();

			} else {
				break;
			}
		}
		State s = new State("save/2DGrid4PTS.som");
		s.save(som);
	//	System.exit(0);

	}
	
	private void draw() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, squareSize * dim, squareSize * dim);
		
		 // draw
		for(int y = 0; y < config.dimY; y++) {
			for(int x = 0; x < config.dimX; x++) {
				drawDot(x, y, g);
			}		
		}
		// and flip the buffer over
		System.out.println(som);
		g.dispose();
		strategy.show();
	}
	
	private void drawDot(int x, int y, Graphics2D graphics) {	
		// Draw Lines
		graphics.setColor(Color.RED);
		if(x > 0) { // left
			graphics.drawLine(
					translatePosition(som.getWeightVectors()[x][y].get(0)), 
					translatePosition(som.getWeightVectors()[x][y].get(1)), 
					translatePosition(som.getWeightVectors()[x - 1][y].get(0)), 
					translatePosition(som.getWeightVectors()[x - 1][y].get(1)));
		}
		if(x < config.dimX - 1) { // right
			graphics.drawLine(
					translatePosition(som.getWeightVectors()[x][y].get(0)), 
					translatePosition(som.getWeightVectors()[x][y].get(1)), 
					translatePosition(som.getWeightVectors()[x + 1][y].get(0)), 
					translatePosition(som.getWeightVectors()[x + 1][y].get(1)));
		}	
		if(y > 0) { // above
			graphics.drawLine(
					translatePosition(som.getWeightVectors()[x][y].get(0)), 
					translatePosition(som.getWeightVectors()[x][y].get(1)), 
					translatePosition(som.getWeightVectors()[x][y - 1].get(0)), 
					translatePosition(som.getWeightVectors()[x][y - 1].get(1)));
		}
		if(y < config.dimY - 1) { // below
			graphics.drawLine(
					translatePosition(som.getWeightVectors()[x][y].get(0)), 
					translatePosition(som.getWeightVectors()[x][y].get(1)), 
					translatePosition(som.getWeightVectors()[x][y + 1].get(0)), 
					translatePosition(som.getWeightVectors()[x][y + 1].get(1)));
		}	
		/*
		// Draw points
		graphics.setColor(Color.WHITE);
        Rectangle2D rectangle = new Rectangle2D.Float(
        		translatePosition(som.getWeightVectors()[x][y].get(0)) - squareSize / 2, 
        		translatePosition(som.getWeightVectors()[x][y].get(1)) - squareSize / 2, 
        		squareSize, squareSize);
        graphics.fill(rectangle);
        
        graphics.draw(rectangle); 
        */
	}
	
	private int translatePosition(double p) {
		return dim / 2  + (int)(dim * p);
	}
	
	private class KeyInputHandler extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

		} 
		
		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
		
	}
	
}
