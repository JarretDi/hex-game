package model.Inventory;

// Represents a sort with a sorting type and an order,
// With a special case of null if there is no sort (inventory unsorted)
// Order represents the order of sort
// true is descending sort, i.e. A->Z, Weapon->Armor->Consumable->Misc->Currency, 100->0

public class Sort {
    private SortType sort;
    private Boolean order;

    public enum SortType {
        Name, Type, Value, Weight
    }

    // REQUIRES: sort is one of "Name", "Type", "Value", "Weight"
    // EFFECT: constructs a sort with given sort and order
    public Sort(SortType sort, Boolean order) {
        this.sort = sort;
        this.order = order;
    }

    // EFFECT: constructs an Unsort (empty sort)
    public Sort() {
        this.sort = null;
        this.order = null;
    }

    // MODIFIES: this
    // EFFECT: turns sort into the Unsort (empty sort)
    public void setUnsorted() {
        this.sort = null;
        this.order = null;
    }

    public Boolean isUnsorted() {
        return sort == null;
    }

    public SortType getSort() {
        return this.sort;
    }

    public Boolean getOrder() {
        return this.order;
    }
}
