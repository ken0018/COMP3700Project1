import javax.swing.*;
import java.util.Scanner;

public class StoreManager {
    public static final String DBMS_SQ_LITE = "SQLite";
    //public static final
    //public static final String DB_FILE = "C:/Users/ttn0007/Documents/store.db";
    public static final String DB_FILE = "~/home/ken0018/Academics/COMP3700/Project1/store.db";

    IDataAdapter adapter = null;
    private static StoreManager instance = null;

    public static StoreManager getInstance() {
        if (instance == null) {

            String dbfile = DB_FILE;
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                dbfile = fc.getSelectedFile().getAbsolutePath();
            System.out.println("Please type the database you wish to connect to");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            instance = new StoreManager(input, dbfile);
        }
        return instance;
    }

    private StoreManager(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            adapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            adapter = new SQLiteDataAdapter();

        adapter.connect(dbfile);
    }

    public IDataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(IDataAdapter a) {
        adapter = a;
    }


    public void run() {
        MainUI ui = new MainUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
//        StoreManager.getInstance().init();
        StoreManager.getInstance().run();
    }

}
