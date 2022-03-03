package io.pedro.hos.nlp.pos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import io.pedro.hos.utils.Utils;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyPOS {
	
	public static void main(String[] args) {

		String sent[] = new String[]{"O", "Pedro", "roubou", "pão", "na", "casa", "do", "João", "!", "Quem", ",", "eu", "?", "eu", "não", "!"};	
		Map<String, Object> pos = createPOS(sent);
		
		String tags[] = (String[]) pos.get("tags");
		double probs[] = (double[]) pos.get("probs");
		
		for (int i = 0; i < tags.length; i++) {
			System.out.println(sent[i] + " é " + tags[i] + " com probabilidade de " + probs[i]);
		}
	}

	public static Map<String, Object> createPOS(String sent[]) {
		
		//String modelBin = "en-pos-perceptron.bin";
		String modelBin = "pt-pos-perceptron.bin";
		
		try (InputStream modelIn = new FileInputStream(Utils.PATH_MODEL + modelBin)) {
			
			POSModel model = new POSModel(modelIn);
			POSTaggerME tagger = new POSTaggerME(model);
			
			String tags[] = tagger.tag(sent);
			String upperTags[] = Arrays.asList(tags).stream().map(s -> s.toUpperCase()).toArray(String[]::new);
			
			double probs[] = tagger.probs();
			
			return Map.of("tags", upperTags, "probs", probs);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
