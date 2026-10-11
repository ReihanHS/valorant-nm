/*
 * One Night Market card: back image, reveal, styled confirm, purchase.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class StoreItem extends JPanel {

    private static final int CARD_W = 148;
    private static final int CARD_H = 210;

    private String name;
    private String weaponType;
    private String tier;
    private Color tierColor;
    private int originalPrice;
    private int discountPercent;
    private int discountedPrice;

    private boolean revealed = false;
    private boolean purchased = false;

    private String frontImagePath;
    private JPanel cardFace;
    private JLabel cardImageLabel;
    private JLabel discountBadge;
    private JLabel priceCorner;
    private JLabel purchasedStamp;
    private JButton actionButton;

    public StoreItem(String name, String weaponType, String tier, Color tierColor,
            int originalPrice, int discountPercent, String assetPath) {

        this.name = name;
        this.weaponType = weaponType;
        this.tier = tier;
        this.tierColor = tierColor;
        this.originalPrice = originalPrice;
        this.discountPercent = discountPercent;
        this.frontImagePath = assetPath;
        this.discountedPrice = (int) Math.round(originalPrice * (1.0 - discountPercent / 100.0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        cardFace = new JPanel(null);
        cardFace.setPreferredSize(new Dimension(CARD_W + 4, CARD_H + 4));
        cardFace.setMaximumSize(new Dimension(CARD_W + 4, CARD_H + 4));
        cardFace.setBackground(new Color(14, 18, 26));
        cardFace.setBorder(BorderFactory.createLineBorder(tierColor, 2));

        cardImageLabel = new JLabel("", SwingConstants.CENTER);
        cardImageLabel.setBounds(2, 2, CARD_W, CARD_H);
        setLabelImage(cardImageLabel, pickCardBackPath(tier), CARD_W, CARD_H);
        cardFace.add(cardImageLabel);

        discountBadge = new JLabel("-" + discountPercent + "%");
        discountBadge.setFont(new Font("SansSerif", Font.BOLD, 10));
        discountBadge.setForeground(Color.WHITE);
        discountBadge.setOpaque(true);
        discountBadge.setBackground(new Color(30, 30, 35, 220));
        discountBadge.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        discountBadge.setBounds(4, 4, 44, 18);
        discountBadge.setVisible(false);
        cardFace.add(discountBadge);

        priceCorner = new JLabel("");
        priceCorner.setFont(new Font("SansSerif", Font.BOLD, 10));
        priceCorner.setForeground(Color.WHITE);
        priceCorner.setHorizontalAlignment(SwingConstants.RIGHT);
        priceCorner.setBounds(CARD_W - 72, 4, 68, 18);
        priceCorner.setVisible(false);
        cardFace.add(priceCorner);

        purchasedStamp = new JLabel("PURCHASED");
        purchasedStamp.setFont(new Font("SansSerif", Font.BOLD, 13));
        purchasedStamp.setForeground(new Color(60, 240, 140));
        purchasedStamp.setHorizontalAlignment(SwingConstants.CENTER);
        purchasedStamp.setBounds(10, CARD_H / 2 - 10, CARD_W - 16, 24);
        purchasedStamp.setVisible(false);
        cardFace.add(purchasedStamp);

        cardFace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!revealed && !purchased) {
                    revealOffer();
                }
            }
        });

        actionButton = new JButton("CLICK TO REVEAL");
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionButton.setMaximumSize(new Dimension(CARD_W + 20, 32));
        actionButton.setFont(new Font("SansSerif", Font.BOLD, 10));
        actionButton.setBackground(new Color(30, 40, 52));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(false);
        actionButton.setOpaque(true);

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!revealed) {
                    revealOffer();
                } else if (!purchased) {
                    tryPurchase();
                }
            }
        });

        add(cardFace);
        add(Box.createVerticalStrut(4));
        add(actionButton);
    }

    public void revealOffer() {
        if (revealed || purchased) {
            return;
        }
        revealed = true;
        setLabelImage(cardImageLabel, frontImagePath, CARD_W, CARD_H);
        discountBadge.setVisible(true);
        priceCorner.setText(discountedPrice + " VP");
        priceCorner.setVisible(true);
        actionButton.setText("PURCHASE  " + discountedPrice + " VP");
    }

    public void resetCard() {
        revealed = false;
        purchased = false;
        setLabelImage(cardImageLabel, pickCardBackPath(tier), CARD_W, CARD_H);
        discountBadge.setVisible(false);
        priceCorner.setVisible(false);
        purchasedStamp.setVisible(false);
        cardFace.setBorder(BorderFactory.createLineBorder(tierColor, 2));
        actionButton.setEnabled(true);
        actionButton.setText("CLICK TO REVEAL");
        actionButton.setBackground(new Color(30, 40, 52));
    }

    private String pickCardBackPath(String tierName) {
        if (tierName.equals("EXCLUSIVE")) {
            return "assets" + File.separator + "card_back_gold.png";
        }
        if (tierName.equals("PREMIUM")) {
            return "assets" + File.separator + "card_back_pink.png";
        }
        return "assets" + File.separator + "card_back.png";
    }

    private void setLabelImage(JLabel label, String path, int w, int h) {
        File imageFile = new File(path);
        if (imageFile.exists()) {
            ImageIcon raw = new ImageIcon(imageFile.getAbsolutePath());
            Image scaled = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setText("");
        } else {
            label.setIcon(null);
            label.setText("?");
        }
    }

    private void tryPurchase() {
        int price = discountedPrice;

        if (ValorantNightMarket.balance < price) {
            int need = price - ValorantNightMarket.balance;
            Store.showBanner("Not enough VP! (Need " + need + " more VP to purchase " + name + ")", false);
            return;
        }

        if (!showConfirmDialog()) {
            return;
        }

        ValorantNightMarket.balance = ValorantNightMarket.balance - price;
        purchased = true;

        purchasedStamp.setVisible(true);
        cardFace.setBorder(BorderFactory.createLineBorder(new Color(40, 160, 80), 2));
        actionButton.setText("OWNED");
        actionButton.setBackground(new Color(40, 160, 80));
        actionButton.setEnabled(false);

        Store.updateBalanceLabels();

        Store.showBanner("PURCHASE SUCCESSFUL: Unlocked " + name + " for " + price
                + " VP! Remaining: " + ValorantNightMarket.balance + " VP", true);
    }

    private boolean showConfirmDialog() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(owner, "Confirm Purchase", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setSize(440, 230);

        Color accent = tierColor;
        if (tier.equals("EXCLUSIVE")) {
            accent = new Color(231, 184, 36);
        }

        JPanel root = new JPanel(null);
        root.setBackground(new Color(11, 15, 22));
        root.setBorder(BorderFactory.createLineBorder(accent, 2));
        dialog.setContentPane(root);

        JLabel title = new JLabel("CONFIRM PURCHASE");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(accent);
        title.setBounds(18, 12, 220, 22);
        root.add(title);

        JLabel bal = new JLabel("Balance: " + ValorantNightMarket.balance + " VP");
        bal.setFont(new Font("SansSerif", Font.PLAIN, 11));
        bal.setForeground(new Color(155, 175, 200));
        bal.setHorizontalAlignment(SwingConstants.RIGHT);
        bal.setBounds(240, 12, 180, 22);
        root.add(bal);

        JPanel box = new JPanel(null);
        box.setBackground(new Color(17, 24, 34));
        box.setBorder(BorderFactory.createLineBorder(new Color(40, 50, 65)));
        box.setBounds(16, 44, 408, 110);
        root.add(box);

        JLabel itemName = new JLabel(name);
        itemName.setFont(new Font("SansSerif", Font.BOLD, 15));
        itemName.setForeground(Color.WHITE);
        itemName.setBounds(12, 8, 380, 22);
        box.add(itemName);

        JLabel sub = new JLabel(weaponType + "  •  " + tier + " EDITION");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 10));
        sub.setForeground(new Color(155, 175, 200));
        sub.setBounds(12, 28, 380, 18);
        box.add(sub);

        JLabel orig = new JLabel("<html><strike>" + originalPrice + " VP</strike></html>");
        orig.setFont(new Font("SansSerif", Font.PLAIN, 10));
        orig.setForeground(new Color(130, 140, 155));
        orig.setBounds(12, 52, 100, 18);
        box.add(orig);

        JLabel off = new JLabel("-" + discountPercent + "%");
        off.setFont(new Font("SansSerif", Font.BOLD, 10));
        off.setForeground(Color.WHITE);
        off.setOpaque(true);
        off.setBackground(new Color(50, 160, 90));
        off.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        off.setBounds(12, 72, 40, 18);
        box.add(off);

        JLabel deal = new JLabel(discountedPrice + " VP");
        deal.setFont(new Font("SansSerif", Font.BOLD, 20));
        deal.setForeground(accent);
        deal.setBounds(58, 66, 160, 28);
        box.add(deal);

        int after = ValorantNightMarket.balance - discountedPrice;
        JLabel remain = new JLabel("Remaining after purchase: " + after + " VP");
        remain.setFont(new Font("SansSerif", Font.PLAIN, 10));
        remain.setForeground(new Color(80, 190, 120));
        remain.setBounds(12, 88, 380, 18);
        box.add(remain);

        final boolean[] confirmed = new boolean[1];

        JButton cancel = new JButton("CANCEL");
        cancel.setBounds(16, 168, 200, 36);
        cancel.setFont(new Font("SansSerif", Font.BOLD, 11));
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(new Color(30, 40, 52));
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setOpaque(true);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed[0] = false;
                dialog.dispose();
            }
        });
        root.add(cancel);

        JButton buy = new JButton("PURCHASE NOW");
        buy.setBounds(224, 168, 200, 36);
        buy.setFont(new Font("SansSerif", Font.BOLD, 11));
        buy.setForeground(Color.WHITE);
        buy.setBackground(accent);
        buy.setFocusPainted(false);
        buy.setBorderPainted(false);
        buy.setOpaque(true);
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed[0] = true;
                dialog.dispose();
            }
        });
        root.add(buy);

        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
        return confirmed[0];
    }
}
