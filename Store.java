/*
 * Main store view: top nav, title, banner, horizontal card row, background.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Store extends JPanel {

    public static final Color BACKGROUND = Color.decode("#0A0F18");
    public static final String BG_IMAGE = "assets" + File.separator + "bg.jpg";
    public static final String TITLE_IMAGE = "assets" + File.separator + "title_header.png";

    private static JLabel navBalanceLabel;
    private static JLabel bannerLabel;

    private Image backgroundImage;
    private JPanel cardRow;

    public Store() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        backgroundImage = loadBackground(BG_IMAGE);

        add(buildTopNav(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
    }

    private JPanel buildTopNav() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(new Color(11, 17, 24));
        nav.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 50, 65)));
        nav.setPreferredSize(new Dimension(900, 48));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        left.setOpaque(false);
        JLabel crumb = new JLabel("STORE // NIGHT.MARKET");
        crumb.setFont(new Font("SansSerif", Font.BOLD, 12));
        crumb.setForeground(new Color(170, 185, 200));
        left.add(crumb);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        right.setOpaque(false);

        JPanel vpBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        vpBox.setBackground(new Color(24, 32, 44));
        vpBox.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 110)));
        JLabel vIcon = new JLabel(" V ");
        vIcon.setForeground(new Color(255, 75, 95));
        vIcon.setFont(new Font("SansSerif", Font.BOLD, 11));
        navBalanceLabel = new JLabel(formatVp(ValorantNightMarket.balance));
        navBalanceLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        navBalanceLabel.setForeground(Color.WHITE);
        vpBox.add(vIcon);
        vpBox.add(navBalanceLabel);

        JButton addVp = new JButton("+");
        styleNavButton(addVp);
        addVp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addVp.setToolTipText("Add 1,000 VP");
        addVp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValorantNightMarket.balance = ValorantNightMarket.balance + 1000;
                Store.updateBalanceLabels();
                Store.showBanner("Added 1,000 VP. Balance: " + ValorantNightMarket.balance + " VP", true);
            }
        });

        right.add(vpBox);
        right.add(addVp);

        nav.add(left, BorderLayout.WEST);
        nav.add(right, BorderLayout.EAST);
        return nav;
    }

    private JLabel makeTab(String text, boolean active) {
        return makeTab(text, active, Color.WHITE);
    }

    private JLabel makeTab(String text, boolean active, Color color) {
        JLabel tab = new JLabel("  " + text + "  ");
        tab.setFont(new Font("SansSerif", Font.BOLD, 11));
        tab.setForeground(color);
        if (active) {
            tab.setOpaque(true);
            tab.setBackground(new Color(255, 70, 85));
        }
        return tab;
    }

    private void styleNavButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setForeground(new Color(160, 180, 200));
        btn.setBackground(new Color(20, 28, 38));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 68, 88)));
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(30, 8, 8, 8));

        // -- NIGHT.MARKET title --
        JLabel titleLabel = new JLabel("NIGHT.MARKET", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 42));
        titleLabel.setForeground(new Color(225, 95, 255));

        // -- Dynamic countdown --
        long daysLeft = calculateDaysLeft();
        String countdownText = (daysLeft > 0)
                ? "ENDS IN " + daysLeft + " DAY" + (daysLeft != 1 ? "S" : "")
                : "NIGHT MARKET HAS ENDED";
        JLabel countdownLabel = new JLabel(countdownText, SwingConstants.CENTER);
        countdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        countdownLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        countdownLabel.setForeground(new Color(180, 140, 255));

        // -- Title container --
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(countdownLabel);

        bannerLabel = new JLabel(" ", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        bannerLabel.setForeground(Color.WHITE);
        bannerLabel.setOpaque(true);
        bannerLabel.setBackground(new Color(0, 0, 0, 0));
        bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bannerLabel.setMaximumSize(new Dimension(900, 28));
        bannerLabel.setPreferredSize(new Dimension(900, 28));

        cardRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        cardRow.setOpaque(false);
        cardRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(titlePanel);
        center.add(bannerLabel);
        center.add(Box.createVerticalStrut(4));
        center.add(cardRow);

        return center;
    }

    public static String formatVp(int amount) {
        return amount + " VP";
    }

    public static void updateBalanceLabels() {
        if (navBalanceLabel != null) {
            navBalanceLabel.setText(formatVp(ValorantNightMarket.balance));
        }
        BottomBar.updateBalance();
    }

    public static void showBanner(String message, boolean success) {
        if (bannerLabel == null) {
            return;
        }
        bannerLabel.setText(message);
        if (success) {
            bannerLabel.setBackground(new Color(10, 36, 24));
            bannerLabel.setForeground(new Color(120, 240, 170));
            bannerLabel.setBorder(BorderFactory.createLineBorder(new Color(40, 220, 130)));
        } else {
            bannerLabel.setBackground(new Color(48, 14, 20));
            bannerLabel.setForeground(new Color(255, 200, 205));
            bannerLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 65, 85)));
        }
    }

    public void clearCards() {
        if (cardRow != null) {
            cardRow.removeAll();
            cardRow.revalidate();
            cardRow.repaint();
        }
    }

    public static void revealAllCards() {
        for (StoreItem item : ValorantNightMarket.STOREITEMS) {
            item.revealOffer();
        }
    }

    private Image loadBackground(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath()).getImage();
        }
        return null;
    }

    /**
     * Calculate days remaining in the night market.
     * The market runs for 14 days starting from today.
     */
    private static long calculateDaysLeft() {
        // Night market lasts 14 days. Adjust the end date as needed.
        LocalDate endDate = LocalDate.now().plusDays(14);
        long days = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
        return Math.max(days, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(6, 10, 16, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(BACKGROUND);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public Component add(Component comp) {
        if (cardRow != null && comp instanceof StoreItem) {
            cardRow.add(comp);
            return comp;
        }
        return super.add(comp);
    }
}
