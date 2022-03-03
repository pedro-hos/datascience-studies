package io.pedro.hos.nlp.chunking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.pedro.hos.nlp.pos.MyPOS;
import io.pedro.hos.utils.Utils;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class MyChunking {

	public static void main(String[] args) {

		try (InputStream is = new FileInputStream(Utils.PATH_MODEL + "en-chunker.bin")) {

			//String[] tokens = new String[] { "Most", "large", "cities", "in", "the", "US", "had", "morning", "and", "afternoon", "newspapers", "." };
			String[] tokens = new String[] { "the", "little", "yellow", "dog", "barked", "at", "the", "cat"};
			
			String tags[] = (String[]) MyPOS.createPOS(tokens).get("tags");
			
			ChunkerModel chunkerModel = new ChunkerModel(is);
			ChunkerME chunker = new ChunkerME(chunkerModel);
			String[] chunks = chunker.chunk(tokens, tags);

			// printing the results
			System.out.println("\nChunker Example in Apache OpenNLP\nPrinting chunks for the given sentence...\n");
			System.out.println("\nTOKEN - POS_TAG - CHUNK_ID\n-------------------------");
			
			for (int i = 0; i < chunks.length; i++) {
				System.out.println(tokens[i] + " - " + tags[i] + " - " + chunks[i]);
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
