package pos.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllItemInfosInReceipt {
    private final Map<String, ItemInfo> allItemInfosMap = new HashMap<>();

    public AllItemInfosInReceipt(List<String> distinctBarcodes) {
        List<ItemInfo> allItemInfosList = ItemDataLoader.loadAllItemInfos();
        allItemInfosList.forEach(itemInfo -> allItemInfosMap.put(itemInfo.getBarcode(), itemInfo));
    }

    public ItemInfo get(String barcode) {
        if (allItemInfosMap.containsKey(barcode)) {
            return allItemInfosMap.get(barcode);
        }
        return null;
    }
}
