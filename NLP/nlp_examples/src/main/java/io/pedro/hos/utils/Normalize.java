package io.pedro.hos.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

/**
 * @author Pedro Silva <pesilva@redhat.com>
 *
 */
public class Normalize {

	public static void main(String[] args) {
		Normalize normalize = new Normalize();
		
		//normalize.normalizeNames();
		normalize.createCatDocTrainingFileByCSV(2, 3, "tw-sentimental-doccat.train", Utils.PATH_DATASET + "twiter_sentimental/twitter_training.csv");
	}

	public void createCatDocTrainingFileByCSV(int posCat, int posTxt, String trainName, String csvPath) {

		try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).withSkipLines(1).build()) {

			PrintWriter pw = new PrintWriter(Utils.PATH_TEST + trainName, "UTF-8");
			List<String[]> r = reader.readAll();
			
			r.forEach(x -> {
				
				String cat = x[posCat];
				String text = x[posTxt];
				
				if(!cat.isBlank() && !text.isBlank()) {
					pw.write(cat + "\t" + text + "\n");
				}
				
			});
			
			pw.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void normalizeNames() {

		System.out.println("... starting normalize");

		try (Stream<String> input = Files.lines(Paths.get(Utils.PATH_TEST + "pt-names.train"))) {

			PrintWriter pw = new PrintWriter(Utils.PATH_TEST + "pt-names-normalized.train", "ISO-8859-1");

			input.forEach(s -> {
				String name = s.substring(0, s.indexOf(","));
				String newName = "<START:person> " + name + " <END> ";
				String replaceAll = s.replaceAll(name, newName) + "\n";

				pw.write(replaceAll);
			});

			pw.close();

			System.out.println("... finish");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
