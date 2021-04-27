package Game;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import java.awt.Graphics;
import java.io.Serializable;

public class Inventory <T extends Inventoryable & Serializable> implements Serializable {
    private ArrayList<T> container = new ArrayList<>();

    public T get(int i) {
        return container.get(i);
    }

    public T deleteAt(int i) {
        if (container.get(0) instanceof SkillItems) {
            T temp = container.get(i);
            if (temp.getQuantity() == 1) {
                container.remove(i);
            }
            else {
                container.get(i).addQuantity(-1);
            }
            return temp;
        }
        else {
            return container.remove(i);
        }
    }

    public void append(T el) {
        if (el instanceof SkillItems){
            int idx = indexByName(el.getName());
            if (idx != -1) {
                container.get(idx).addQuantity(1);
            }
            else {
                container.add(el);
            }
            container.sort(comparing(o -> ((SkillItems) o).getBasePower(),reverseOrder()));
        }
        else if (el instanceof OwnedEngimon){
            container.add(el);
        }
    }

    public int n_elmt() {
        int sum = 0;
        for (T el : container) {
            sum += el.getQuantity();
        }

        return sum;
    }

    public void displayAll() {
        if (n_elmt() == 0) {
            System.out.println("Inventory kosong");
            return;
        }
        if (container.get(0) instanceof OwnedEngimon) {
            int n = 1;
            ArrayList<T> sorted = new ArrayList<>();
            String elements[] = {"fire", "water", "electric", "ground", "ice"};

            for (String el : elements) {
                List<T> engElemen = container.stream()
                        .filter(e -> ((OwnedEngimon) (e)).getElements().get(0).equals(el))
                        .sorted(comparing(o -> ((OwnedEngimon) o).getLevel()).reversed())
                        .collect(Collectors.toList());

                for (T eng : engElemen) {
                    System.out.print(n + ". ");
                    ((OwnedEngimon) eng).invDisplay();
                    n++;
                }
                sorted.addAll(engElemen);
            }

            this.container = sorted;
        }
        else {
            for (int i = 0; i < container.size(); i++) {
                System.out.print((i+1) + ". ");
                container.get(i).invDisplay();
            }
        }
    }

    public int indexByName(String name) {
        for (int i = 0; i < container.size(); i++) {
            if (container.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public void drawInventory(Graphics g, int y) {
        for (int i = 0; i < container.size(); i++) {
            g.drawImage(container.get(i).getInvIcon(), (i)*32+15, y, null);
        }
    }
}