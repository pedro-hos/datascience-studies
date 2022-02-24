package io.pedro.hos.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

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
		normalize.createCatDocTrainingFileByCSV(5, 2, "ge-doc-cat.train", Utils.PATH_DATASET + "ge_soccer_clubs/ge_news.csv");
	}

	public void createCatDocTrainingFileByCSV(int posCat, int posTxt, String binName, String csvPath) {

		try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).withSkipLines(1).build()) {

			PrintWriter pw = new PrintWriter(Utils.PATH_TEST + "ge_soccer_clubs_doccat.train", "UTF-8");
			List<String[]> r = reader.readAll();
			
			r.forEach(x -> {
				String text = x[posCat] + "\t" + x[posTxt] + "\n";
				pw.write(text);
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
