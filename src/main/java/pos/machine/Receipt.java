package pos.machine;

import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private final List<ReceiptItem> receiptItems;

    static final String RECEIPT_HEADER = "***<store earning no money>Receipt***\n";
    static final String RECEIPT_FOOTER_TEMPLATE =
            "\n" +
            "----------------------\n" +
            "Total: %d (yuan)\n" +
            "**********************";

    public Receipt(List<String> allBarcodesInReceipt) {
        this.receiptItems = createReceiptItems(allBarcodesInReceipt);
    }

    private List<ReceiptItem> createReceiptItems(List<String> allBarcodesInReceipt) {
        AllBarcodes allBarcodes = new AllBarcodes(allBarcodesInReceipt);
        AllItemInfosInReceipt allItemInfosInReceipt = new AllItemInfosInReceipt(allBarcodes.getDistinctBarcodes());

        List<String> distinctBarcodes = allBarcodes.getDistinctBarcodes();
        return distinctBarcodes.stream().map(distinctBarcode ->
                new ReceiptItem(allItemInfosInReceipt.get(distinctBarcode), allBarcodes.quantityOf(distinctBarcode)))
                .collect(Collectors.toList());
    }

    public String generateReceipt() {
        String receipt = RECEIPT_HEADER;
        List<String> receiptItemLines = receiptItems.stream().map(ReceiptItem::generateReceiptLine).collect(Collectors.toList());
        receipt += String.join("\n", receiptItemLines);
        receipt += generateReceiptFooter();
        return receipt;
    }

    private String generateReceiptFooter() {
        Integer total = calculateTotal();
        return String.format(RECEIPT_FOOTER_TEMPLATE, total);
    }

    private Integer calculateTotal() {
        return receiptItems.stream().map(ReceiptItem::getSubtotal).reduce(0, Integer::sum);
    }

}
