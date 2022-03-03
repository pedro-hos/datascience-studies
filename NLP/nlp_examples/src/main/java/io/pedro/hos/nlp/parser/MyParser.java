package io.pedro.hos.nlp.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;


/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyParser {

	public static void main(String[] args) {

		try (InputStream is = new FileInputStream(Utils.PATH_MODEL + "en-parser-chunking.bin")) {

			//String sentence = "The quick brown fox jumps over the lazy dog .";
			String sentence = "The cellphone was broken in two days.";
			
			ParserModel model = new ParserModel(is);
			Parser parser = ParserFactory.create(model);

			Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
			
			for(Parse p : topParses) {
				p.show();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
