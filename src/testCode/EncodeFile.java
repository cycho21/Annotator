package testCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import edu.stanford.smi.protegex.owl.repository.util.FileInputSource;

public class EncodeFile {
	public static void main(String[] args) {
		try {
			File input = new File("result.txt");
			File output = new File("result16.txt");
			if(!output.exists()){
				output.createNewFile();
			}
			InputStreamReader isr = new InputStreamReader(new FileInputStream(input), Charset.forName("UTF-8").newDecoder());
			OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(output), Charset.forName("UTF-16").newEncoder());
			int temp;
			while((temp = isr.read())!=-1){
				osr.write(temp);
			}
			isr.close();
			osr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
