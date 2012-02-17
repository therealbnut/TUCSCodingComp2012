import java.awt.Color;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Point;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class TUCSDevCompExample2011
{
	public static final Color CREEPER_COLOUR = new Color(73, 156, 64);

	public static void main(String[] args)
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		boolean output;

		if (args.length == 2 && args[0].equals("--output"))
		{
			TUCSDevCompExample2011.processInput(input, args[1]);
		}
		else
		{
			TUCSDevCompExample2011.processInput(input, null);
		}
	}

	public static void processInput(BufferedReader input, String output)
	{
		int size[] = new int[2];
		Rectangle bounds = null;
		Vector<Color> colours;
		Point point;

		colours = TUCSDevCompExample2011.readImage(input, size);
		for (int i=0; i<colours.size(); ++i)
		{
			point = new Point(i%size[0], i/size[0]);
			if (compareColours(colours.get(i), TUCSDevCompExample2011.CREEPER_COLOUR, 0.02f))
			{
				if (bounds == null) bounds = new Rectangle(point); else bounds.add(point);
			}
		}

		if (bounds != null)
		{
			if (output != null)
			{
				processOutput(output, colours, size[0], size[1], bounds);
			}
			System.out.print(""  + (int)(bounds.getX()));
			System.out.print(" " + (int)(bounds.getY()));
			System.out.print(" " + (int)(bounds.getX()+bounds.getWidth()));
			System.out.print(" " + (int)(bounds.getY()+bounds.getHeight()));
			System.out.print("\n");
		}
	}

	public static boolean compareColours(Color a, Color b, float threshold)
	{
		float ca[], cb[], dr, dg, db, aa, ab;
		ca = a.getRGBColorComponents(null); cb = b.getRGBColorComponents(null);
		aa = (ca[0] + ca[1] + ca[2]) / 3.0f;
		ab = (cb[0] + cb[1] + cb[2]) / 3.0f;
		for (int i=0; i<3; ++i) {ca[i] = (ca[i]-aa)/aa; cb[i] = (cb[i]-ab)/ab;}
		return ((dr=ca[0]-cb[0])*dr + (dg=ca[1]-cb[1])*dg + (db=ca[2]-cb[2])*db) < threshold;
	}

	public static Vector<Color> readImage(BufferedReader input, int size[])
	{
		Vector<Color> colours = new Vector<Color>();
		char hex_rgb[] = new char[6];
		int x=0, y=0, c;

		do
		{
			try
			{
				input.read(hex_rgb, 0, 6);
				if (x == 0 && hex_rgb[0] == '\n')
				{
					size[1] = y;
					break;
				}
				colours.add(new Color(Integer.parseInt(new String(hex_rgb), 16)));
				c = input.read();
			}
			catch (java.io.IOException e)
			{
				System.err.println("Failed to read colour: " + e);
				break;
			}

			if (c == ' ')
			{
				++x;
			}
			else if (c == '\n')
			{
				size[0] = x+1;
				x = 0;
				++y;
			}
			else
			{
				System.err.println("Unexpected input: '" + c + " at ("+x+","+y+")'!");
				break;
			}
		}
		while (true);

		return colours;
	}

	public static void processOutput(String output, Vector<Color> colours,
									 int width, int height, Rectangle bounds)
	{
		int x, y, i, rgb, r, g, b;
		BufferedImage image;
		Point p;
		Color c;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (i=0,y=0; y<height; ++y)
		{
			for (x=0; x<width; ++x,++i)
			{
				p   = new Point(x, y);
				c   = colours.get(i);
				r   = c.getRed();
				g   = c.getGreen();
				b   = c.getBlue();
				if (!bounds.contains(p))
				{
					r /= 2;
					g /= 2;
					b /= 2;
				}
				rgb = (r<<16)|(g<< 8)|(b<< 0);
				image.setRGB(x, y, rgb);
			}
		}
		try
		{
			if (ImageIO.write(image, "png", new File(output)))
			{
				System.out.println("done");
			}
			else
			{
				System.err.println("unable to write png!");
			}
		}
		catch (java.io.IOException e)
		{
			System.err.println("failed! ("+e+")");
		}
	}
};