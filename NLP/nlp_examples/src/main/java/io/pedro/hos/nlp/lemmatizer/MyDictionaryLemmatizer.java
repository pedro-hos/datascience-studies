package io.pedro.hos.nlp.lemmatizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.nlp.pos.MyPOS;
import io.pedro.hos.utils.Utils;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyDictionaryLemmatizer {

	public static void main(String[] args) {

		try (InputStream dictLemmatizer = new FileInputStream(Utils.PATH_DICT + "en-lemmatizer.dict")) {
			
		String sent[] = new String[]{"Most", "large", "cities", "in", "the", "US", "had", "morning", "and", "afternoon", "newspapers", "."};
		String tags[] = (String[]) MyPOS.createPOS(sent).get("tags");
		
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		 
        // finding the lemmas
        String[] lemmas = lemmatizer.lemmatize(sent, tags);

        // printing the results
        System.out.println("\nPrinting lemmas for the given sentence...\n");
        System.out.println("WORD -POSTAG : LEMMA");
        
		for (int i = 0; i < sent.length; i++) {
			System.out.println(sent[i] + " -" + tags[i] + " : " + lemmas[i]);
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
