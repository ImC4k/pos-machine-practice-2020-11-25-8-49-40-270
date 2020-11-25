package pos.machine;

import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        Receipt receipt = new Receipt(barcodes);
        return receipt.generateReceipt();
    }
}
