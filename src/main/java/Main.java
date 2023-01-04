import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    ItemMerger itemMerger = new ItemMerger();
    Map<String, Integer> series = itemMerger.mergeItems("src/main/resources/raw_input_items.txt");
    List<DocumentPosition> documentPositions = itemMerger.getDocumentPositions("src/main/resources/wfmag_export.txt", series);
    itemMerger.writeToCSV(documentPositions, "src/main/resources/output_to_pdf.csv");
  }
}
