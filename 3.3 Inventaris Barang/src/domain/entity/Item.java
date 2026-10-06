package domain.entity;

public class Item {
    private final int id;
    private final String name;
    private long stock;
    private final String category;

    public Item(int id, String name, long stock, String category) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public long getStock() { return stock; }
    public String getCategory() { return category; }
    public void setStock(long stock) { this.stock = stock; }
}
