package io.pedro.hos.nlp.name_finder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import io.pedro.hos.utils.Utils;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class NERTrainingExample {
	
	public static void main(String[] args) {

		try {
			
			MarkableFileInputStreamFactory factory = new MarkableFileInputStreamFactory(new File(Utils.PATH_TEST + "pt-name-finder-text.input"));
			ObjectStream<NameSample> sampleStream = new NameSampleDataStream(new PlainTextByLineStream(factory, StandardCharsets.UTF_8));
			
			// setting the parameters for training
	        TrainingParameters params = new TrainingParameters();
	        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
	        params.put(TrainingParameters.CUTOFF_PARAM, 0);
	        
	        TokenNameFinderFactory tnffd = TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec());
			TokenNameFinderModel nameFinderModel = NameFinderME.train("pt", null, sampleStream, params, tnffd);
			
			OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(Utils.PATH_MODEL + "pt-ner-text-person-api.bin"));
			nameFinderModel.serialize(modelOut);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
