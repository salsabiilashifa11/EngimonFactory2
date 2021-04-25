package Game;

import java.awt.Image;

public interface Inventoryable {
    public void invDisplay();
    public int getQuantity();
    public String getName();
    public void addQuantity(int n);
    public Image getInvIcon();
}