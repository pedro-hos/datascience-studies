package io.pedro.hos.nlp.language_detector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.utils.Utils;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyLanguageDetector {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {

		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + "langdetect-183.bin")) {
			
			String text_pt = "Nesse novo artigo, falo um pouco mais sobre uns conceitos mais avançados de Openshift Pipelines como Trigger, "
					+ "TriggerBindings, Interceptors entre outros, e com isso escrever um pipeline genérico o suficiente para ser executado "
					+ "em workflows que utilizem multi-feature branchs como parte do desenvolvimento";
			
			String text_en = "Data? trivago! We're home to a diverse team of data experts from different corners of the globe operating "
					+ "large sets of data on daily basis to ensure our product’s quality is 1% better than what it was the previous day. "
					+ "💪 https://lnkd.in/daNCn4Mf";
			
			LanguageDetectorModel model = new LanguageDetectorModel(modelIn);
			LanguageDetector myCategorizer = new LanguageDetectorME(model);
			
			// Get the most probable language
			Language bestLanguage = myCategorizer.predictLanguage(text_en);
			
			System.out.println("Best language: " + bestLanguage.getLang());
			System.out.println("Best language confidence: " + bestLanguage.getConfidence());

			// Get an array with the most probable languages
			Language[] languages = myCategorizer.predictLanguages(text_pt);
			
			for (int i = 0; i < languages.length; i++) {
				System.out.println(languages[i]);
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
