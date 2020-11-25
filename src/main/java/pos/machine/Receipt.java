package pos.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Receipt {
    private final AllBarcodes allBarcodesInReceipt;
    private final List<ReceiptItem> receiptItems;

    static final String RECEIPT_HEADER = "***<store earning no money>Receipt***\n";

    public Receipt(List<String> allBarcodesInReceipt) {
        this.allBarcodesInReceipt = new AllBarcodes(allBarcodesInReceipt);
        this.receiptItems = createReceiptItems();
    }

    public String generateReceipt() {
        String receipt = RECEIPT_HEADER;
        List<String> receiptItemLines = receiptItems.stream().map(ReceiptItem::generateReceiptLine).collect(Collectors.toList());
        receipt += String.join("\n", receiptItemLines);
        receipt += generateReceiptFooter();
        return receipt;
    }

    private Map<String, ItemInfo> createBarcodeToItemInfoMap() {
        Map<String, ItemInfo> barcodeToItemInfoMap = new HashMap<>();
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        itemInfos.forEach(itemInfo -> barcodeToItemInfoMap.put(itemInfo.getBarcode(), itemInfo));
        return barcodeToItemInfoMap;
    }

    private List<ReceiptItem> createReceiptItems() {
        Map<String, ItemInfo> itemsDetail = createBarcodeToItemInfoMap();
        List<String> distinctBarcodes = allBarcodesInReceipt.getDistinctBarcodes();
        return distinctBarcodes.stream().map(distinctBarcode ->
                new ReceiptItem(itemsDetail.get(distinctBarcode), allBarcodesInReceipt.quantityOf(distinctBarcode)))
                .collect(Collectors.toList());
    }

    private String generateReceiptFooter() {
        Integer total = calculateTotal();
        return String.format(
                "\n" +
                "----------------------\n" +
                "Total: %d (yuan)\n" +
                "**********************", total);
    }

    private Integer calculateTotal() {
        return receiptItems.stream().map(ReceiptItem::getSubtotal).reduce(0, Integer::sum);
    }

}
