import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class CreepEncodeImage
{
	static final String COMMAND_USAGE      = "Usage: java CreepEncodeImage [filename ...]";
	static final String INPUT_INSTRUCTIONS = "Enter file names or \"q\" to exit.";
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			for (int i=0; i<args.length; ++i)
			{
				processFile(args[i]);
			}
		}
		else
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String filename;

			System.out.println(CreepEncodeImage.COMMAND_USAGE);
			System.out.println(CreepEncodeImage.INPUT_INSTRUCTIONS);

			do
			{
				try
				{
					filename = input.readLine();
					if (filename.equals("q"))
						break;
				}
				catch (java.io.IOException e)
				{
					System.err.println("Failed to read line: " + e);
					break;
				}
				if (!CreepEncodeImage.processFile(filename))
				{
					System.out.println(CreepEncodeImage.INPUT_INSTRUCTIONS);
				}
			}
			while (true);
		}
	}
	public static boolean processFile(String filename)
	{
		BufferedImage image;
		PrintWriter output;
		int w, h, x, y, i;
		int pixels[];
		String hex;

		try
		{
			image = ImageIO.read(new File(filename));
		}
		catch (java.io.IOException e)
		{
			System.err.println("Failed to open '" + filename + "': " + e);
			return false;
		}

		w = image.getWidth();
		h = image.getHeight();

		System.out.print("Processing " + filename + "...");
		pixels = image.getRGB(0,0,w,h,null,0,w);

		try
		{
			output = new PrintWriter(new FileWriter(filename+".creep")); 
			for (i=0,y=0; y<h; ++y)
			{
				for (x=0; x<w; ++x, ++i)
				{
					hex = Integer.toHexString(pixels[i] & 0xFFFFFF);
					while (hex.length()<6) hex = "0" + hex;
					output.print("" + hex);
					output.print((x+1<w)?" ":"\n");
				}
			}
			output.print("\n");
			output.flush();
			output.close();
			output = null;

			System.out.println("done");
		}
		catch (java.io.IOException e)
		{
			System.err.println("failed! ("+e+")");
			return false;
		}

		return true;
	}
};