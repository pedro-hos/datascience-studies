package io.pedro.hos.nlp.doccat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import io.pedro.hos.utils.Utils;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class DocClassificationNaiveBayesTrainer {

	public static void main(String[] args) {

		try {

			MarkableFileInputStreamFactory factory = new MarkableFileInputStreamFactory(new File(Utils.PATH_TEST + "tw-sentimental-doccat.train"));
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(new PlainTextByLineStream(factory, StandardCharsets.UTF_8));

			// setting the parameters for training
			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, 100);
			params.put(TrainingParameters.CUTOFF_PARAM, 0);
			
			DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, params, new DoccatFactory());

			OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(Utils.PATH_MODEL + "tw-sentimental-doccat-naive.bin"));
			model.serialize(modelOut);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
