import com.sun.javadoc.Doc;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMerger {


  public Map<String, Integer> mergeItems(String filepath) {
    FileInputStream inputStream;
    try {
      inputStream = new FileInputStream(filepath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
    Map<String, Integer> items = new HashMap<>();
    int mergeCounter = 0;
    int linesCounter = 0;
    int damagedLines = 0;
    int cleanedLines = 0;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (!line.contains(">")) {
          damagedLines++;
          continue;
        }
        //cleanup start line
        String[] regex = {":", "'", "]", "\\", "."};
        for (String i : regex) {
          if (line.startsWith(i)) {
            line = line.replace(i, "");
            cleanedLines++;
          }
        }
        //end
        if (line.split(">").length < 2) {
          damagedLines++;
          continue;
        }
        String code = line.trim().split(">")[0];
        Integer quantity = Integer.valueOf(line.trim().split(">")[1].trim());
        if (items.containsKey(code)) {
          items.put(code, items.get(code) + quantity);
          mergeCounter++;
        } else {
          items.put(code, quantity);
        }
        linesCounter++;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    System.out.println("Summary:");
    System.out.println("Items damaged: " + damagedLines);
    System.out.println("Items cleaned: " + cleanedLines);
    System.out.println("Items input: " + linesCounter);
    System.out.println("Items merged: " + mergeCounter);
    System.out.println("Items output: " + items.size());
    return items;
  }

  public void writeToCSV(Map<String, Integer> input, String outputPath) {
    String eol = System.getProperty("line.separator");
    try (Writer writer = new FileWriter(outputPath)) {
      for (Map.Entry<String, Integer> entry : input.entrySet()) {
        writer.append(entry.getKey())
            .append(';')
            .append(String.valueOf(entry.getValue()))
            .append(eol);
      }
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  public void writeToCSV(List<DocumentPosition> input, String outputPath) {
    String eol = System.getProperty("line.separator");
    try (Writer writer = new FileWriter(outputPath)) {
      writer.append("Lp.").append(';')
          .append("nazwa").append(';')
          .append("kod kreskowy").append(';')
          .append("j.m.").append(';')
          .append("cena zakupu netto").append(';')
          .append("ilość").append(';')
          .append("wartość")
          .append(eol);
      for (DocumentPosition i : input) {
        writer.append(String.valueOf(i.getLp())).append(';')
            .append(i.getName()).append(';')
            .append(i.getCode()).append(';')
            .append(i.getUnit()).append(';')
            .append(i.getPurchasePrice().toString().replace(".", ",")).append(';')
            .append(String.valueOf(i.getQuantity())).append(';')
            .append(i.getTotalValue().toString().replace(".", ","))
            .append(eol);
      }
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  public List<DocumentPosition> getDocumentPositions(String filepath, Map<String, Integer> series) {
    FileInputStream inputStream;
    try {
      inputStream = new FileInputStream(filepath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
    List<DocumentPosition> documentPositions = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      int lp = 0;
      String line;
      int skippedItemsCounter = 0;
      while ((line = br.readLine()) != null) {
        String[] s = line.split(";");
        String code = s[8];
        if (series.containsKey(code)) {
          DocumentPosition documentPosition = new DocumentPosition();
          documentPosition.setLp(++lp);
          documentPosition.setName(s[0].trim());
          documentPosition.setUnit(s[5].trim().replace(".", ""));
          documentPosition.setPurchasePrice(new BigDecimal(s[6].replace(",", ".")));
          documentPosition.setCode(code);
          documentPosition.setQuantity(series.get(code));
          documentPosition.setTotalValue(new BigDecimal(s[6].replace(",", ".")).multiply(new BigDecimal(series.get(code))));
          documentPositions.add(documentPosition);
        } else {
          skippedItemsCounter++;
        }
      }
      System.out.println("Skipped items: " + skippedItemsCounter);
      System.out.println("Generated document positions: " + lp);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return documentPositions;
  }


}
