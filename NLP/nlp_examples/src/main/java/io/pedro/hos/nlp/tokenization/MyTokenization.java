package io.pedro.hos.nlp.tokenization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyTokenization {

	public void runWhitespaceTokenizer(final String text) {

		Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		String tokens[] = tokenizer.tokenize(text);

		System.out.println("Token\n----------------");
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i]);
		}

	}

	public void runSimpleTokenizer(final String text) {

		Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
		String tokens[] = tokenizer.tokenize(text);

		System.out.println("Token\n----------------");
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i]);
		}

	}

	public Span[] runTokenSpan(final String text) {

		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin")) {
			
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			
			return tokenizer.tokenizePos(text);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public String[] runToken(final String text) {

		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin")) {

			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			
			return tokenizer.tokenize(text);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		String text = "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.\n"
				+ "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group.\n"
				+ "Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields\n"
				+ "    PLC, was named a director of this British industrial conglomerate.";

		new MyTokenization().runWhitespaceTokenizer(text);
		
		/** for (int i = 0; i < tokenSpans.length; i++) {
		Span span = tokenSpans[i];

		// probabilities for the detected tokens the value is between 0 and 1, where 1
		// is the highest possible probability and 0 the lowest possible probability.
		System.out.println("Prob: " + span.getProb() + " : " + text.substring(span.getStart(), span.getEnd()));

		// System.out.println(span.getCoveredText(text));
		} **/

	}
}
