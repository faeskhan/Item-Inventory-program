package Lab_9_2;

import BasicIO.ASCIIDataFile;
import BasicIO.ASCIIOutputFile;


public class Item {

    private String itemNum;
    private int reorder;
    private int quantity;


    public Item(ASCIIDataFile input) {
        itemNum = input.readString();
        if (input.isEOF() == false) {
            reorder = input.readInt();
            quantity = input.readInt();
        }

    }

    public String getItemNum() {

        return itemNum;
    }

    ;

    public int getReorder() {

        return reorder;
    }

    ;

    public void setReorder(int newReorder) {

        reorder = newReorder;
    }

    ;

    public int getQuantity() {

        return quantity;
    }

    ;

    public void sell(int num) {

        quantity = quantity - num;

    }

    ;

    public boolean shouldReorder() {
        //check if we need to reorder
        //compare quantity and reorder
        if (quantity <= reorder) {
            return true;
        } else {
            return false;
        }
    }

    ;

    public void write(ASCIIOutputFile output) {
        //write teh itemNum, reorder, quantity
        output.writeString(itemNum);
        output.writeInt(reorder);
        output.writeInt(quantity);
        output.newLine();

    }

    ;


}  // Item