/*
 * Bottom bar: info text and demo controls for testing purchases.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottomBar extends JPanel {

    public static final Color BACKGROUND = Color.decode("#0A0F18");
    public static final Color BORDER = Color.decode("#2A3548");

    private static JLabel balanceLabel;

    public BottomBar() {
        setPreferredSize(new Dimension(1000, 52));
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER));

        JLabel info = new JLabel(
                "  [INFO] Click any card to reveal your mystery discount. Click PURCHASE to acquire it directly.");
        info.setFont(new Font("SansSerif", Font.PLAIN, 11));
        info.setForeground(new Color(160, 180, 200));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        controls.setOpaque(false);

        JLabel demo = new JLabel("DEMO CONTROLS:");
        demo.setFont(new Font("SansSerif", Font.BOLD, 9));
        demo.setForeground(new Color(130, 150, 170));

        JButton addVp = makeButton("+ 1,000 VP", new Color(0, 200, 175), Color.BLACK);
        addVp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValorantNightMarket.balance = ValorantNightMarket.balance + 1000;
                Store.updateBalanceLabels();
                Store.showBanner("Added 1,000 VP. Balance: " + ValorantNightMarket.balance + " VP", true);
            }
        });

        JButton lowVp = makeButton("Set 500 VP", new Color(30, 40, 52), Color.WHITE);
        lowVp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValorantNightMarket.balance = 500;
                Store.updateBalanceLabels();
                Store.showBanner("Balance set to 500 VP (test failed purchases).", false);
            }
        });

        JButton revealAll = makeButton("Reveal All", new Color(30, 40, 52), Color.WHITE);
        revealAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Store.revealAllCards();
            }
        });

        JButton reset = makeButton("Reset Market", new Color(30, 40, 52), Color.WHITE);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValorantNightMarket.resetMarket();
            }
        });

        balanceLabel = new JLabel("");
        balanceLabel.setVisible(false);

        controls.add(demo);
        controls.add(addVp);
        controls.add(lowVp);
        controls.add(revealAll);
        controls.add(reset);

        add(info, BorderLayout.WEST);
        add(controls, BorderLayout.EAST);
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return btn;
    }

    public static void updateBalance() {
        if (balanceLabel != null) {
            balanceLabel.setText(String.valueOf(ValorantNightMarket.balance));
        }
    }
}
