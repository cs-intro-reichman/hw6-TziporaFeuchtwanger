import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change x/ add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();// ignore first raw
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int r = in.readInt();
				int g = in.readInt();
				int b = in.readInt();
			image[i][j] = new Color(r, g, b); // creating color	
			}
		}
		// Reads the RGB values from the file into the image array. // For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and // makes pixel (i,j) refer to that object.// Replace the following statement with your code.
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
	for (int i = 0; i < image.length; i++) {
		for (int j = 0; j < image[i].length; j++) {
		print(image[i][j]);	
		}
	System.out.println();	
	}
		//// Replace this comment with your code
		//// Notice that all you have to do is print every element (i,j) of the array using the print(Color) function.
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int rows = image.length;
		int cols = image[0].length;
		Color[][] flipped = new Color[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				flipped[i][j] = image[i][cols - j - 1];
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
			int rows = image.length;
			int cols = image[0].length;
			Color[][] flipped = new Color[rows][cols];
			for (int i = 0; i < rows; i++) {
				flipped[i] = image[rows - i - 1];
			}
			return flipped;
		}
			
	
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		Double red = pixel.getRed()*0.299;
		Double blue = pixel.getBlue()*0.114;
		Double green = pixel.getGreen()*0.587;
		int lum = (int)(red + blue + green);
		Color toLum = new Color (lum,lum,lum);
		return toLum; 
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		// Grayscale conversion constants
		double rToGrey = 0.299;
		double gToGrey = 0.587;
		double bToGrey = 0.114;
		Color[][] imageGray = new Color [image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				
				int red = image[i][j].getRed();
				int green = image[i][j].getGreen();
				int blue = image[i][j].getBlue();
	
				// Compute the grayscale value using weighted RGB
				int grayValue = (int) (red * rToGrey + green * gToGrey + blue * bToGrey);
	
				// Clamp the grayscale value to the valid range [0, 255]
				grayValue = Math.min(255, Math.max(0, grayValue));
	
				// Create a new grayscale color and assign it to the grayscale image
				imageGray[i][j] = new Color(grayValue, grayValue, grayValue);
			}
		}
	
		// Return the grayscale image
		return imageGray;
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] resized = new Color [width][height];
		int imageWidth = image.length;
		int imageHeight = image[0].length;
			for (int i = 0; i < resized.length; i++) {
			for (int j = 0; j < resized[0].length; j++) {
				resized[i][j] = image[i*(imageWidth/width)][j*(imageHeight/height)];
			}	
			}
		return resized;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		Color newColor = new Color((int)(alpha*c1.getRed() + (1-alpha)*c2.getRed()),(int)(alpha*c1.getGreen() + (1-alpha)*c2.getGreen()),(int)(alpha*c1.getBlue() + (1-alpha)*c2.getBlue())); 
		return newColor; 
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int widthimage = Math.max(image1.length, image2.length);
		int heightimage = Math.max(image1[0].length,image2[0].length);
		Color[][] imageBlended = new Color[widthimage][heightimage];
			for (int i = 0; i < imageBlended.length; i++) {
				for (int j = 0; j < imageBlended[0].length; j++) {
				imageBlended [i][j] = blend(image1[i][j], image2[i][j], alpha);
				}
			}
		return imageBlended;	
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int rows = source.length;
		int cols = source[0].length;
		target = scaled(target, rows, cols); // Scale target to match source
		for (int step = 0; step <= n; step++) {
			double alpha = step / (double) n;
			Color[][] blended = new Color[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					blended[i][j] = blend(source[i][j], target[i][j], alpha);
				}
			}
			display(blended); // Display each intermediate image
			StdDraw.pause(100); // Pause for animation
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					// Set the pen color to the color at the current pixel
					if (image[row][col] != null) {
						StdDraw.setPenColor(image[row][col]);
					} else {
						StdDraw.setPenColor(Color.BLACK); // Default to black if color is null
					}
					
					StdDraw.filledSquare(col + 0.5, height - row - 0.5, 0.5);
			}
		}
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

