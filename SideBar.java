/*
 * Right panel: list of skins purchased this session.
 */
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SideBar extends JPanel {

    public static final Color BG = Color.decode("#0F1723");
    public static final Color BORDER = Color.decode("#2A3548");

    public static JPanel panel;

    public static HashMap<String, Integer> purchases = new HashMap<>();
    public static HashMap<String, Integer> itemPrices = new HashMap<>();

    public SideBar() {
        setPreferredSize(new Dimension(170, 480));
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER));

        JLabel title = new JLabel(" OWNED", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setForeground(new Color(150, 165, 180));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 6));

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(panel), BorderLayout.CENTER);

        updatePurchasePanel();
    }

    public static void addPurchase(String s, int price) {
        purchases.put(s, 1);
        itemPrices.put(s, price);
        updatePurchasePanel();
    }

    public static void updatePurchasePanel() {
        panel.removeAll();

        if (purchases.isEmpty()) {
            JLabel empty = new JLabel("None yet");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 10));
            empty.setForeground(new Color(120, 135, 150));
            panel.add(empty);
        } else {
            for (String s : purchases.keySet()) {
                int qty = purchases.get(s);
                int totalPrice = qty * itemPrices.get(s);

                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(BG);
                row.setMaximumSize(new Dimension(150, 36));

                JLabel nameLabel = createLabel(s);
                nameLabel.setForeground(Color.WHITE);
                JLabel priceLabel = createLabel(totalPrice + " VP");
                priceLabel.setForeground(new Color(0, 240, 215));

                row.add(nameLabel, BorderLayout.NORTH);
                row.add(priceLabel, BorderLayout.SOUTH);
                panel.add(row);
                panel.add(Box.createVerticalStrut(4));
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 10));
        return label;
    }
}
