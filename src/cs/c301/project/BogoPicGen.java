package cs.c301.project;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BogoPicGen {
	
	/**
	 * @author Bryan Liles and Abram Hindle
	 * 
	 * BogoPicGen generates a random bitmap with given width and height
	 * parameters.  Uses an algorithm based on: 
	 * 
	 * http://countercomplex.blogspot.com/2011/10/some-deep-analysis-of-one-line-music.html
	 * 
	 * @param width	the width of the bitmap to be generated
	 * @param height the height of the bitmap to be generated
	 */
	public static Bitmap generateBitmap(int width, int height) {
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888 );
		int t = (int) (255.0 * Math.random());
		int r = (int) (255.0 * Math.random());
		int g = (int) (255.0 * Math.random());
		int b = (int) (255.0 * Math.random());
		int offset1 = (int) (255 * Math.random());
		int offset2 = (int) (255 * Math.random());
		int offset3 = (int) (255 * Math.random());
		int rm1 = 1 + (int)( 12 * Math.random());
		int gm1 = 1 + (int)( 12 * Math.random());
		int bm1 = 1 + (int)( 12 * Math.random());
		int rm2 = 1 + (int)( 12 * Math.random());
		int gm2 = 1 + (int)( 12 * Math.random());
		int bm2 = 1 + (int)( 12 * Math.random());
		int rm3 = 1 + (int)( 12 * Math.random());
		int gm3 = 1 + (int)( 12 * Math.random());
		int bm3 = 1 + (int)( 12 * Math.random());

		int [] mods = { 65535, width, height, 255, 64, 32, 512, 1024 , width, height, width, height}; 
		int rmod = mods[(int)(mods.length * Math.random())];
		int gmod = mods[(int)(mods.length * Math.random())];
		int bmod = mods[(int)(mods.length * Math.random())];
		
		int rs1 = 1 + (int)( 11 * Math.random());
		int gs1 = 1 + (int)( 11 * Math.random());
		int bs1 = 1 + (int)( 11 * Math.random());
		int rs2 = 1 + (int)( 11 * Math.random());
		int gs2 = 1 + (int)( 11 * Math.random());
		int bs2 = 1 + (int)( 11 * Math.random());

		int rf = (int)(2*Math.random());
		int bf = (int)(2*Math.random());
		int gf = (int)(2*Math.random());
		
		int [] pixels = new int[width*height];
		float [] hsv = new float[3];
		boolean isHSV = (Math.random() > 0.5);
		int constantT = (Math.random() > 0.5)?1:0;
		int c;
		//public void setPixels (int[] pixels, int offset, int stride, int x, int y, int width, int height) 
		for (int i = 0; i < height; i++) {		
			for (int j = 0 ; j < width; j++) {
				r = (r+offset1+(b*rf|t*rm1&t>>rs1|t*rm2&t>>rs2|t*rm3&t/1024)-1)%rmod;
				g = (g+offset2+(r*gf|t*gm1&t>>gs1|t*gm2&t>>gs2|t*gm3&t/1024)-1)%gmod;
				b = (b+offset3+(g*bf|t*bm1&t>>bs1|t*bm2&t>>bs2|t*bm3&t/1024)-1)%bmod;
				if (isHSV) {
					hsv[0] = (float)r / (float)255.0;
					hsv[1] = (float)g / (float)255.0;
					hsv[2] = (float)b / (float)255.0;
					//	int c = Color.rgb(r,g,b);
					c = Color.HSVToColor(hsv);
				} else {
					c = Color.rgb(r,g,b);					
				}
				pixels[i*width + j] = c;
				t = t + constantT * 1;
				
			}
		}
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
