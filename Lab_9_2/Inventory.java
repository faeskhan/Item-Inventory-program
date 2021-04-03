package Lab_9_2;


import BasicIO.ASCIIDataFile;
import BasicIO.ASCIIOutputFile;
import BasicIO.BasicForm;
import BasicIO.ReportPrinter;


/**
 * This class is a program to do a simple inventory control producing a report.
 *
 * @author Fahad Khan
 * @version 1.1 (December 2019)
 */

public class Inventory {

    private BasicForm form;
    private ASCIIDataFile invData;     // data file for inventory info
    private ASCIIOutputFile newInvData;  // new (updated) inventory file
    private ReportPrinter report;      // printer for report


    /**
     * The constructor does the day-end inventory control for a small company
     * generating a report.
     */

    public Inventory() {

        Item anItem;         //itemNum, reorder, quantity
        int oQuant;      // old quantity on hand
        int sold;        // number sold
        int numReorder;  // number of items requiring reorder

        invData = new
                ASCIIDataFile();
        form = new BasicForm();
        buildForm();
        newInvData = new ASCIIOutputFile();
        report = new ReportPrinter();
        setUpReport();
        numReorder = 0;
        for (; ; ) {
            anItem = new Item(invData);
            if (invData.isEOF()) break;
            fillForm(anItem);
            form.accept(); //waits for the user to press OK button
            sold = form.readInt("sold");
            oQuant = anItem.getQuantity();
            anItem.sell(sold);
            if (anItem.shouldReorder()) {
                writeDetail(anItem, oQuant, sold);
                numReorder = numReorder + 1;
            }
            ;
            anItem.write(newInvData);
        }
        ;
        writeSummary(numReorder);
        invData.close();
        newInvData.close();
        form.close();
        report.close();

    }

    ;  // constructor

    public static void main(String[] args) {
        Inventory i = new Inventory();
    }

    ;  // setUpReport

    /**
     * This method sets up the report, adding all fields.
     */

    private void setUpReport() {

        report.setTitle("National Widgets", "Inventory Control");
        report.addField("itemNum", "Item #", 6);
        report.addField("reorder", "Reorder Pt", 10);
        report.addField("oQuant", "Old", 8);
        report.addField("sold", "Sold", 8);
        report.addField("nQuant", "New", 8);

    }

    ;  // writeDetail

    /**
     * This method generates a report detail line.
     *
     * @param itemNum item number
     * @param reorder reorder point
     * @param oQuant  old quantity
     * @param sold    items sold
     * @param nQuant  new quantity
     */

    private void writeDetail(Item anItem, int oQuant,
                             int sold) {

        report.writeString("itemNum", anItem.getItemNum());
        report.writeInt("reorder", anItem.getReorder());
        report.writeInt("oQuant", oQuant);
        report.writeInt("sold", sold);
        report.writeInt("nQuant", anItem.getQuantity());

    }

    ;  // writeSummary

    /**
     * This method generates the report summary.
     *
     * @param numReorder number of items requiring reorder
     */

    private void writeSummary(int numReorder) {

        report.writeLine("# Items to Reorder: " + numReorder);

    }

    ;  // writeInvData

    /**
     * This method writes a record to the new inventory file.
     *
     * @param itemNum item number
     * @param reorder reorder point
     * @param quant   quantity
     */

    private void writeInvData(String itemNum, int reorder, int quant) {

        newInvData.writeString(itemNum);
        newInvData.writeInt(reorder);
        newInvData.writeInt(quant);
        newInvData.newLine();

    }

    ;

    public void buildForm() {

        form.setTitle("Inventory");
        //addTextField("varName", "Field title", width, x, y);
        form.addTextField("itemNum", "Item#", 8, 10, 10);
        form.setEditable("itemNum", false);
        form.addTextField("reorder", "Reorder Point", 5, 10, 40);
        form.setEditable("reorder", false);
        form.addTextField("nQuant", "Quantity on Hand", 5, 10, 70);
        form.setEditable("nQuant", false);
        form.addTextField("sold", "Number Sold", 5, 10, 100);
        form.setEditable("sold", true);

    }

    ;

    public void fillForm(Item anItem) {

        form.clearAll(); //erase the text in the textboxes
        form.writeString("itemNum", anItem.getItemNum());
        form.writeInt("reorder", anItem.getReorder());
        form.writeInt("nQuant", anItem.getQuantity());
        form.clear("sold");
    }

    ;


}  // Inventory