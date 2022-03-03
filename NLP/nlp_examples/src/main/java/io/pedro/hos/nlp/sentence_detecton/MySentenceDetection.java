package io.pedro.hos.nlp.sentence_detecton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MySentenceDetection {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {

		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "pt-api-sent.bin")) {
			
			SentenceModel model = new SentenceModel(modelIn);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			
			String text = "Primeira Sentença. Segunda Sentença";
			
			// The result array now contains two entries. The first String is "First sentence." and the second String is "Second sentence." The whitespace before, between and after the input String is removed.
			String sentences[] = sentenceDetector.sentDetect(text);
			
			for (int i = 0; i < sentences.length; i++) {
				System.out.println(sentences[i]);
			}
			
			// The API also offers a method which simply returns the span of the sentence in the input string.
			Span sentences2[] = sentenceDetector.sentPosDetect(text);
			
			for (int i = 0; i < sentences2.length; i++) {
				Span span = sentences2[i];
				System.out.println(span.getProb() + " - " + text.substring(span.getStart(), span.getEnd()));
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
