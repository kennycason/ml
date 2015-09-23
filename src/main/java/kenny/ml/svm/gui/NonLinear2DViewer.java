package kenny.ml.svm.gui;

import kenny.ml.svm.EvalMeasures;
import kenny.ml.svm.FeatureSpace;
import kenny.ml.svm.KernelParams;
import kenny.ml.svm.SupportVectorMachine;
import kenny.ml.svm.kernels.Gaussian;
import kenny.ml.svm.kernels.IKernel;
import kenny.ml.svm.problem.IProblemLoader;
import kenny.ml.svm.problem.Problem;
import kenny.ml.svm.problem.SimpleProblemLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;


public class NonLinear2DViewer extends Canvas {

	private SupportVectorMachine svm;
	
	private Problem train;
	
	private Problem test;

	private int squareSize = 5;

	private int dim = 40;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The stragey that allows us to use accelerate page flipping */

	private BufferStrategy strategy;

	public static void main(String argv[]) throws IOException {
		NonLinear2DViewer somv = new NonLinear2DViewer();

		somv.init();

		somv.run();
	}

	public NonLinear2DViewer() {
		// create a frame
		JFrame container = new JFrame("Support Vector Machine 2D Viewer");

		// get hold the content of the frame and set up the
		// resolution
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(squareSize * dim * 2, squareSize * dim * 2));
		panel.setLayout(null);

		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, squareSize * dim * 2, squareSize * dim * 2);
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

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

	}

	public void init() throws IOException {
		svm = new SupportVectorMachine();

		IKernel k;
		KernelParams kp;

		IProblemLoader loader = new SimpleProblemLoader();
		
		train = loader.load("kenny/ml/svm/nonlinear_train.svm");
		test = loader.load("kenny/ml/svm/nonlinear_test.svm");
		

		k = new Gaussian();
		kp = new KernelParams(k, 0.023, 2, 7);

		kp.setC(Math.pow(2, 0));

		System.out.println("Loaded.");
		System.out.println("Training...");
		
		svm.train(train, kp);
		System.out.println("Testing...");
		int[] pred = svm.test(test, true);

		EvalMeasures e = new EvalMeasures(test, pred, 2);
		System.out.println("Accuracy = " + e.accuracy());
	}

	public void run() {
		System.out.println("drawing...");
		draw();
	}

	private void draw() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.PINK);
		g.fillRect(0, 0, squareSize * dim * 2, squareSize * dim * 2);

		// draw
		for (int y = -dim; y < dim; y++) {
			for (int x = -dim; x < dim; x++) {
				int result = (svm.testOne(new FeatureSpace(x, y)) < 0 ? -1 : 1);
			//	System.out.println("(" + x + ", " + y + ") => " + svm.testOne(new FeatureSpace(2, x, y)));
				if(result == 1) {
					drawSquare(x + dim, y + dim, Color.WHITE, g);
				} else {
					drawSquare(x + dim, y + dim, Color.BLACK, g);
				}
			}
		}
		g.setColor(Color.RED);
		// draw axis
		g.drawLine(0, (dim * squareSize), (dim * squareSize) * 2, (dim * squareSize));
		g.drawLine((dim * squareSize), 0, (dim * squareSize), (dim * squareSize) * 2);
		
		drawTrainingData(g);
		// and flip the buffer over
		g.dispose();
		strategy.show();
	}

	private void drawTrainingData(Graphics2D g) {

		for(int i = 0; i < train.trainingSize; i++) {
			Color c = Color.RED;
			char[] text = new char[] {'-', '1'};
			if(train.y[i] == 1) {
				c = Color.GREEN;
				text = new char[] {'+', '1'};
			}
			drawSquare((int)train.x[i].get(0) + dim, (int)train.x[i].get(1) + dim , c, g);
			g.drawChars(text, 0, 1, ((int)train.x[i].get(0) + dim) * squareSize + 2, ((int)train.x[i].get(1) + dim) * squareSize - 2);
		}
	}
	
	private void drawSquare(int x, int y, Color c, Graphics2D g) {
		g.setColor(c);
		Rectangle2D rectangle = new Rectangle2D.Float(x * squareSize, y
				* squareSize, squareSize, squareSize);
		g.fill(rectangle);
		g.draw(rectangle);
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
