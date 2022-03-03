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
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.doccat.NGramFeatureGenerator;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class DocClassificationNGramFeaturesTrainer {

	public static void main(String[] args) {

		try {

			MarkableFileInputStreamFactory factory = new MarkableFileInputStreamFactory(new File(Utils.PATH_TEST + "ge-doc-cat.train"));
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(new PlainTextByLineStream(factory, StandardCharsets.UTF_8));

			// setting the parameters for training
			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, 100);
			params.put(TrainingParameters.CUTOFF_PARAM, 0);
			
			// feature generators - N-gram feature generators
            FeatureGenerator[] featureGenerators = { new NGramFeatureGenerator(1,1), new NGramFeatureGenerator(2,3) };
            DoccatFactory dcFactory = new DoccatFactory(featureGenerators);
			
			DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, params, dcFactory);

			OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(Utils.PATH_MODEL + "ge-soccer-clubs-doccat-ngram.bin"));
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
