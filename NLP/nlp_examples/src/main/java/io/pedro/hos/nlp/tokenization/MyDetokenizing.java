package io.pedro.hos.nlp.tokenization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.tokenize.DetokenizationDictionary;
import opennlp.tools.tokenize.DetokenizationDictionary.Operation;
import opennlp.tools.tokenize.Detokenizer;
import opennlp.tools.tokenize.DictionaryDetokenizer;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyDetokenizing {
	
	String[] tokens = new String[]{".", "!", "(", ")", "\"", "-"};
	
	Operation[] operations = new Operation[]{
	    Operation.MOVE_LEFT,
	    Operation.MOVE_LEFT,
	    Operation.MOVE_RIGHT,
	    Operation.MOVE_LEFT,
	    Operation.RIGHT_LEFT_MATCHING,
	    Operation.MOVE_BOTH};
	
	DetokenizationDictionary dict = new DetokenizationDictionary(tokens, operations);
	
	public static void main(String[] args) {
		
		try (InputStream dictIn = new FileInputStream(Utils.PATH_TEST + "pt-detokenizer.xml")) {
			
			DetokenizationDictionary dict = new DetokenizationDictionary(dictIn);
			Detokenizer detokenizer = new DictionaryDetokenizer(dict);
			
			String[] tokens = new String[]{"Pedro", "roubou", "pão", "na", "casa", "do", "João!", "#", "ladrão"};
			String sentence = detokenizer.detokenize(tokens, null);
			
			System.out.println(sentence);
			System.out.println("Pedro roubou pão na casa do João! #ladrão".equals(sentence));

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
					

}
