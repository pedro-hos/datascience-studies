package io.pedro.hos.nlp.name_finder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyNameFinder {
	
	public static void main(String[] args) {
		
		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "pt-ner-text-person-api.bin")) {
			
			TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
			NameFinderME nameFinder = new NameFinderME(model);
			
			//String text = Files.readString(Paths.get(Utils.PATH_TEST + "pt-name-finder.input"));
			//String[] sentence = new MyTokenization().runToken(text);
			String[] sentence ={"Maria", "do", "Carmo", "roubou", "pão", "na", "casa", "do", "João", "da", "Mata", "#", "Lidia", "Campelo"};
			
			Span nameSpans[] = nameFinder.find(sentence);
			
			// nameSpans contain all the possible entities detected
	        for(Span s: nameSpans){
	            System.out.print("Prob ");
	            System.out.print(s.getProb());
	            System.out.print("  :  ");
	            // s.getStart() : contains the start index of possible name in the input string array
	            // s.getEnd() : contains the end index of the possible name in the input string array
	            for(int index = s.getStart(); index < s.getEnd(); index++){
	                System.out.print(sentence[index] + " ");
	            }
	            
	            System.out.println();
	        }
	        
	        //After every document clearAdaptiveData must be called to clear the adaptive data in the feature generators.
	        nameFinder.clearAdaptiveData();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
