package io.pedro.hos.nlp.sentence_detecton;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import io.pedro.hos.utils.Utils;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.sentdetect.SentenceDetectorFactory;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MySentenceDetectionTraining {

	public static void main(String[] args) {

		try {
			
			MarkableFileInputStreamFactory factory = new MarkableFileInputStreamFactory(new File(Utils.PATH_TEST + "pt-sent.train"));
			ObjectStream<String> lineStream = new PlainTextByLineStream(factory, StandardCharsets.UTF_8);
			ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream);
			
			// parameters used by machine learning algorithm, Maxent, to train its weights
	        TrainingParameters mlParams = new TrainingParameters();
	        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(15));
	        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));
	 
	        // train the model
	        SentenceDetectorFactory sdFactory =  SentenceDetectorFactory.create(null, "pt", true, new Dictionary(false), null);
			SentenceModel sentdetectModel = SentenceDetectorME.train("pt", sampleStream, sdFactory, mlParams);
	        
	        // save the model, to a file, "en-sent-custom.bin", in the destDir : "custom_models"
	        OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(Utils.PATH_MODEL + "pt-api-sent.bin"));
	        sentdetectModel.serialize(modelOut);
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
