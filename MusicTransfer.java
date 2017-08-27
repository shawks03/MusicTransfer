import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class MusicTransfer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        if (args.length() < 2) {
            return;
        }

		final String FROM = args[0];
		final String TO = args[1];
		ArrayList<File> list = getMusicFiles(new File(FROM));
		int size = list.size();
		File src, dest;
		Random rand = new Random();
		int index;
		String destName;

		while (size > 0) {
			index = rand.nextInt(size);
			src = list.get(index);
			destName = String.valueOf(rand.nextInt(size));
			dest = new File(TO, destName.concat(src.getName()));
			try {
				//System.out.format("copying %s to %s\n", src.getPath(), dest.getPath());
				copy(src, dest);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return;
			}
			
			list.remove(index);
			--size;
		}

	}

	static ArrayList<File> getMusicFiles(File path) {
		ArrayList<File> list = new ArrayList<File>();
		File files[] = path.listFiles();
		String name;

		for (int i = 0; i < files.length; ++i) {
			if (files[i].isFile()) {
				name = files[i].getName();
				if (name.endsWith(".mp3") || name.endsWith(".ogg")) {
					list.add(files[i]);
				} // else skip
			} else if (files[i].isDirectory()) {
				list.addAll(getMusicFiles(files[i]));
			}
		}

		return list;
	}

	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	static void copy(File src, File dest) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
}
