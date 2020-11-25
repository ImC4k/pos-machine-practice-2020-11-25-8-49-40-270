package pos.machine;

public class QuantifiedItem {
    ItemInfo itemInfo;
    Integer quantity;
    Integer subtotal;

    public QuantifiedItem(ItemInfo itemInfo, Integer quantity) {
        this.itemInfo = itemInfo;
        this.quantity = quantity;
        this.subtotal = itemInfo.getPrice() * quantity;
    }

    public String generateReceiptLine() {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)", itemInfo.getName(), quantity, itemInfo.getPrice(), subtotal);
    }

    public Integer getSubtotal() {
        return subtotal;
    }
}
