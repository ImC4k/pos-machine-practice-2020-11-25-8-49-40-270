package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AllBarcodes extends ArrayList<String> {
    private final List<String> barcodes;

    public AllBarcodes(List<String> barcodes) {
        this.barcodes = barcodes;
    }

    public List<String> getDistinctBarcodes() {
        return barcodes.stream().distinct().collect(Collectors.toList());
    }

    public Integer quantityOf(String distinctBarcode) {
        return Collections.frequency(barcodes, distinctBarcode);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AllBarcodes) {
            return barcodes.equals(((AllBarcodes) o).barcodes);
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
