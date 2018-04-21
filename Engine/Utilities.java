package engine;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Utilities {
	
	public static int getScreenWidth() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		return (int) width;
	}
	
	public static int getScreenHeight() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight();
		return (int) height;
	}
	
	
	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	public static float randFloat(float min, float max) {
		return min + (float) (Math.random() * ((max - min) + 1));
	}
	public static double randDouble(double min, double max) {
		return min + (Math.random() * ((max - min) + 1));
	}
	
	
	public static int mapInt(int input, int minInput, int maxInput, int minOutput, int maxOutput) {
		return (input - minInput) / (maxInput - minInput) * (maxOutput - minOutput) + minOutput;
	}
	public static float mapFloat(float input, float minInput, float maxInput, float minOutput, float maxOutput) {
		return (input - minInput) / (maxInput - minInput) * (maxOutput - minOutput) + minOutput;
	}
	public static double mapDouble(double input, double minInput, double maxInput, double minOutput, double maxOutput) {
		return (input - minInput) / (maxInput - minInput) * (maxOutput - minOutput) + minOutput;
	}
	
}
