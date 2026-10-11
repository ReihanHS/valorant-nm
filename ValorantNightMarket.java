/*
 * Valorant Night Market store recreation (main frame).
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ValorantNightMarket extends JFrame {

    public static JPanel STORE;
    public static JPanel BOTBAR;

    public static final Color BG = Color.decode("#0F1723");

    public static ArrayList<StoreItem> STOREITEMS = new ArrayList<>();

    // Player balance in Valorant Points (VP)
    public static int balance = 5000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ValorantNightMarket app = new ValorantNightMarket();
        });
    }

    public ValorantNightMarket() {
        setTitle("VALORANT // NIGHT.MARKET - Store Recreation");
        setSize(1180, 650);
        setPreferredSize(new Dimension(1180, 650));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        STORE = new Store();
        BOTBAR = new BottomBar();

        add(STORE, BorderLayout.CENTER);
        add(BOTBAR, BorderLayout.SOUTH);

        setStoreUp();
        setVisible(true);
    }

    public static void setStoreUp() {
        STOREITEMS.clear();

        STOREITEMS.add(new StoreItem("NEPTUNE VANDAL", "VANDAL", "PREMIUM",
                Color.decode("#EF489C"), 1775, 48, "assets/card_neptune_vandal.png"));
        STOREITEMS.add(new StoreItem("PRIME//2.0 PHANTOM", "PHANTOM", "PREMIUM",
                Color.decode("#EF489C"), 1775, 36, "assets/card_prime_phantom.png"));
        STOREITEMS.add(new StoreItem("PRIME//2.0 KARAMBIT", "MELEE", "EXCLUSIVE",
                Color.decode("#E7B824"), 3550, 19, "assets/card_prime_karambit.png"));
        STOREITEMS.add(new StoreItem("PRIME VANDAL", "VANDAL", "PREMIUM",
                Color.decode("#EF489C"), 1775, 37, "assets/card_prime_vandal.png"));
        STOREITEMS.add(new StoreItem("REAVER KNIFE", "MELEE", "EXCLUSIVE",
                Color.decode("#E7B824"), 3550, 45, "assets/card_reaver_knife.png"));
        STOREITEMS.add(new StoreItem("ONI PHANTOM", "PHANTOM", "PREMIUM",
                Color.decode("#EF489C"), 1775, 34, "assets/card_oni_phantom.png"));

        if (STORE instanceof Store) {
            ((Store) STORE).clearCards();
        }
        for (StoreItem item : STOREITEMS) {
            STORE.add(item);
        }
        Store.updateBalanceLabels();
    }

    public static void resetMarket() {
        balance = 5000;
        for (StoreItem item : STOREITEMS) {
            item.resetCard();
        }
        Store.updateBalanceLabels();
        Store.showBanner("Night Market reset! Balance restored to 5,000 VP.", true);
    }
}
