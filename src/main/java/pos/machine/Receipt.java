package pos.machine;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Receipt {
    private List<String> barcodes;
    private Map<String, ItemInfo> itemsDetail;

    final String receiptHeader = "***<store earning no money>Receipt***\n";

    public Receipt(List<String> barcodes) {
        this.barcodes = barcodes;
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        itemInfos.stream().filter(itemInfo -> barcodes.contains(itemInfo.getBarcode())).forEach(itemInfo -> itemsDetail.put(itemInfo.getBarcode(), itemInfo));
    }

    public String generateReceipt() {

        String receipt = receiptHeader;
        List<String> distinctBarcodes = barcodes.stream().distinct().collect(Collectors.toList());
        List<QuantifiedItem> quantifiedItems = distinctBarcodes.stream().map(distinctBarcode -> {
                int frequency = Collections.frequency(barcodes, distinctBarcode);
                return new QuantifiedItem(itemsDetail.get(distinctBarcode), frequency);
            }
        ).collect(Collectors.toList());
        List receiptLines = quantifiedItems.stream().map(quantifiedItem -> quantifiedItem.generateReceiptLine()).collect(Collectors.toList());
        receipt += String.join("\n", receiptLines);
        receipt += generateReceiptFooter(quantifiedItems);
        return receipt;
    }

    private String generateReceiptFooter(List<QuantifiedItem> quantifiedItems) {
        Integer total = calculateTotal(quantifiedItems);
        return String.format(
                "----------------------\n" +
                        "Total: %d (yuan)\n" +
                        "**********************", total);
    }

    private Integer calculateTotal(List<QuantifiedItem> quantifiedItems) {
        return quantifiedItems.stream().map(quantifiedItem -> quantifiedItem.getSubtotal()).reduce(0, Integer::sum);
    }

}
