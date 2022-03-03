package io.pedro.hos.nlp.doccat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.nlp.tokenization.MyTokenization;
import io.pedro.hos.utils.Utils;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyDocCat {

	public static void main(String[] args) {

		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "ge-doc-cat-naive.bin")) {

			DoccatModel model = new DoccatModel(modelIn);

			DocumentCategorizer doccat = new DocumentCategorizerME(model);
			String text = "Escalação: Taison fica fora da estreia na Copa do Brasil";
			String[] docWords = new MyTokenization().runToken(text);
			double[] aProbs = doccat.categorize(docWords);

			// print the probabilities of the categories
			System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
			for (int i = 0; i < doccat.getNumberOfCategories(); i++) {
				System.out.println(doccat.getCategory(i) + " : " + aProbs[i]);
			}
			
			System.out.println("---------------------------------");

			System.out.println(
					"\n" + doccat.getBestCategory(aProbs) + " : is the predicted category for the given sentence.");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
