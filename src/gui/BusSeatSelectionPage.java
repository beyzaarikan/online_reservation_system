package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// Observer sınıflarınızı içe aktarıyoruz
import observer.Observer;
import observer.SeatManager;
// import observer.SeatObserver; // Eğer her koltuğa özel bir Observer eklemek isterseniz kullanabilirsiniz.

public class BusSeatSelectionPage extends BasePanel implements Observer { // Observer arayüzünü uyguluyor
    private String busCompany;
    private String fromCity;
    private String toCity;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private String basePrice;
    private int passengerCount;
    private String amenities;

    private JPanel seatMapPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private JButton confirmButton;
    private JButton backButton;

    private List<BusSeatButton> selectedSeats;
    private double basePriceValue;

    // Tüm koltuk butonları için tek bir SeatManager örneği kullanacağız
    private SeatManager seatManager;

    public BusSeatSelectionPage(String busCompany, String fromCity, String toCity, String returnDate, // 'date' yerine 'returnDate' vardı, düzeltildi
                               String departureTime, String arrivalTime, String basePrice,
                               int passengerCount, String amenities) {
        super("Bus Seat Selection", 1400, 800);
        this.busCompany = busCompany;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = returnDate; // returnDate olarak gelen parametreyi date'e atadım
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.passengerCount = passengerCount;
        this.amenities = amenities;
        this.selectedSeats = new ArrayList<>();
        this.seatManager = new SeatManager(); // SeatManager'ı başlat

        // Bu sayfayı (BusSeatSelectionPage) SeatManager'ın bir observer'ı olarak kaydet
        this.seatManager.addObserver(this); // 'this' BusSeatSelectionPage örneğini temsil eder

        // Base fiyatı ayrıştır
        try {
            this.basePriceValue = Double.parseDouble(basePrice.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            this.basePriceValue = 45.0; // Fiyat dönüştürme hatası olursa varsayılan değer
        }
    }

    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();

        // Başlık paneli
        JPanel headerPanel = createHeaderPanel();

        // Ana içerik paneli (yatay düzen)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(PageComponents.BACKGROUND_COLOR);

        // Ortada - Otobüs koltuk düzeni (yatay)
        createHorizontalBusSeatMapPanel();

        // Sağ kenar çubuğu - Seçim kontrolü
        JPanel sidebarPanel = createSidebarPanel();

        contentPanel.add(seatMapPanel, BorderLayout.CENTER);
        contentPanel.add(sidebarPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Başlık
        JLabel titleLabel = new JLabel("Select Seat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);

        // Seyahat bilgi kartı
        JPanel infoCard = new JPanel(new GridLayout(1, 4, 30, 0));
        infoCard.setBackground(PageComponents.CARD_COLOR);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        // Bilgi öğeleri
        JPanel companyInfo = createInfoItem("🚌", busCompany, "Company");
        JPanel routeInfo = createInfoItem("📍", fromCity + " → " + toCity, "Root");
        JPanel dateInfo = createInfoItem("📅", date + " date " + departureTime, "Departure");
        JPanel passengerInfo = createInfoItem("👥", passengerCount + " passenger", "Passengers");

        infoCard.add(companyInfo);
        infoCard.add(routeInfo);
        infoCard.add(dateInfo);
        infoCard.add(passengerInfo);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(Box.createVerticalStrut(20), BorderLayout.CENTER);
        headerPanel.add(infoCard, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createInfoItem(String icon, String value, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.CARD_COLOR);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(PageComponents.TEXT_COLOR);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelLabel = new JLabel(label, SwingConstants.CENTER);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(PageComponents.SECONDARY_COLOR);
        labelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(labelLabel);

        return panel;
    }

    private void createHorizontalBusSeatMapPanel() {
        seatMapPanel = new JPanel(new BorderLayout());
        seatMapPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        seatMapPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Modern görünümlü otobüs konteyneri
        JPanel busContainer = new JPanel(new BorderLayout());
        busContainer.setBackground(PageComponents.CARD_COLOR);
        busContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // Otobüs başlığı
        JPanel busHeader = new JPanel(new BorderLayout());
        busHeader.setBackground(PageComponents.CARD_COLOR);

        JLabel busLabel = new JLabel(busCompany.toUpperCase() + " BUS ", SwingConstants.CENTER);
        busLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        busLabel.setForeground(PageComponents.TEXT_COLOR);


        busHeader.add(busLabel, BorderLayout.CENTER);


        // Yatay koltuk düzeni
        JPanel seatsPanel = createHorizontalSeatLayout();

        // Lejant (açıklama)
        JPanel legendPanel = createModernLegendPanel();

        busContainer.add(busHeader, BorderLayout.NORTH);
        busContainer.add(seatsPanel, BorderLayout.CENTER);
        busContainer.add(legendPanel, BorderLayout.SOUTH);

        seatMapPanel.add(busContainer, BorderLayout.CENTER);
    }

    private JPanel createHorizontalSeatLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PageComponents.CARD_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Yatay otobüs düzenini oluştur
        JPanel busLayout = new JPanel();
        busLayout.setLayout(new BoxLayout(busLayout, BoxLayout.Y_AXIS));
        busLayout.setBackground(PageComponents.CARD_COLOR);

        Random random = new Random(42);
        int seatNumber = 1;

        // 10 sütun (otobüs uzunluğunu temsil eder) ve her biri 4 koltuk (2-2 yapılandırma)
        for (int row = 0; row < 4; row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            rowPanel.setBackground(PageComponents.CARD_COLOR);

            for (int col = 0; col < 10; col++) {
                if (row == 1 || row == 2 && col == 0) { // İlk sıradaki koridor boşluğu için ek kontrol
                    // Ortadaki koridor boşluğu
                    JLabel aisleLabel = new JLabel( (row == 1) ? "Corridor" : ""); // İlk koridorun label'ı
                    aisleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 8));
                    aisleLabel.setForeground(PageComponents.SECONDARY_COLOR);
                    aisleLabel.setPreferredSize(new Dimension(50, 20));
                    aisleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    rowPanel.add(aisleLabel);
                } else {
                    boolean isWindow = (row == 0 || row == 3);
                    boolean isOccupied = random.nextDouble() > 0.75; // Rastgele koltukları dolu yap
                    boolean isPremium = col < 3; // İlk 3 sütun premium

                    BusSeatButton seat = new BusSeatButton(seatNumber++, isOccupied, isWindow, isPremium);
                    // BusSeatButton'a SeatManager örneğini aktarıyoruz
                    seat.setSeatManager(seatManager);
                    rowPanel.add(seat);

                    // İsteğe bağlı: Her koltuk için ayrı bir SeatObserver eklemek isterseniz:
                    // seatManager.addObserver(new SeatObserver(String.valueOf(seat.getSeatNumber())));
                }
            }

            busLayout.add(rowPanel);
        }

        mainPanel.add(busLayout, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createModernLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        legendPanel.setBackground(PageComponents.CARD_COLOR);
        legendPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Modern görünümlü lejant öğeleri
        JPanel availableItem = createLegendItem("Available", PageComponents.ACCENT_COLOR, "12");
        JPanel selectedItem = createLegendItem("Selected", PageComponents.PRIMARY_COLOR, "12");
        JPanel occupiedItem = createLegendItem("Occupied", new Color(220, 53, 69), "X"); // Kırmızı renk
        JPanel premiumItem = createLegendItem("Premium (+30%)", new Color(255, 193, 7), "P1"); // Sarı renk

        legendPanel.add(availableItem);
        legendPanel.add(selectedItem);
        legendPanel.add(occupiedItem);
        legendPanel.add(premiumItem);

        return legendPanel;
    }

    private JPanel createLegendItem(String label, Color color, String seatText) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        item.setBackground(PageComponents.CARD_COLOR); // Kartın arka plan rengi

        // Koltuk örneği (örnek buton)
        JButton sampleSeat = new JButton(seatText);
        sampleSeat.setBackground(color); // Butonun arka plan rengi
        sampleSeat.setForeground(isLightColor(color) ? Color.BLACK : Color.WHITE); // Yazı rengi
        sampleSeat.setEnabled(false); // Tıklanamaz
        sampleSeat.setFont(new Font("Segoe UI", Font.BOLD, 11));
        sampleSeat.setPreferredSize(new Dimension(30, 30)); // Küçük boyut
        sampleSeat.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));

        // Açıklama yazısı
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelText.setForeground(PageComponents.TEXT_COLOR);

        item.add(sampleSeat);
        item.add(labelText);

        return item;
    }

    private boolean isLightColor(Color color) {
        // YIQ formülü - kontrast kontrolü için basit bir model
        int yiq = ((color.getRed() * 299) +
                   (color.getGreen() * 587) +
                   (color.getBlue() * 114)) / 1000;
        return yiq >= 180; // Açık renk kabul eşiği
    }


    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PageComponents.CARD_COLOR);
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(30, 25, 30, 25)
        ));

        // Seçim bilgisi
        selectedSeatsLabel = new JLabel("No seat selected");
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedSeatsLabel.setForeground(PageComponents.SECONDARY_COLOR);
        selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel needSeatsLabel = new JLabel(passengerCount + " seat(s)");
        needSeatsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        needSeatsLabel.setForeground(PageComponents.ACCENT_COLOR);
        needSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fiyat gösterimi
        totalPriceLabel = new JLabel("Total: 0.00 TL ");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalPriceLabel.setForeground(PageComponents.ACCENT_COLOR);
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // İşlem butonları
        confirmButton = createModernButton("Confirm", PageComponents.ACCENT_COLOR, true);
        confirmButton.setEnabled(false); // Başlangıçta pasif

        JButton clearButton = createModernButton("Clear", new Color(255, 121, 121), false);
        backButton = createModernButton("← Back", PageComponents.SECONDARY_COLOR, false);

        // Seyahat özellikleri
        JPanel featuresPanel = createFeaturesPanel();

        // Düzen
        sidebar.add(Box.createVerticalStrut(25));
        sidebar.add(selectedSeatsLabel);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(needSeatsLabel);
        sidebar.add(Box.createVerticalStrut(25));
        sidebar.add(totalPriceLabel);
        sidebar.add(Box.createVerticalStrut(35));
        sidebar.add(confirmButton);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(clearButton);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(backButton);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(featuresPanel);

        // Buton eylemleri
        clearButton.addActionListener(e -> clearSelection());
        backButton.addActionListener(e -> {
            dispose(); // Mevcut sayfayı kapat
            new SearchBusTripPage().display(); // Arama sayfasına dön
        });
        confirmButton.addActionListener(e -> {
            // Rezervasyonu onaylama veya bir sonraki adıma geçme mantığı buraya gelir
            PageComponents.showStyledMessage("Success!","Reserving successful ", this);
            // Örneğin: Yeni bir ödeme sayfasına geçiş
            // dispose();
            // new PaymentPage(selectedSeats, totalPrice).display();
        });


        return sidebar;
    }

    private JButton createModernButton(String text, Color color, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(isPrimary ? Color.WHITE : PageComponents.TEXT_COLOR);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(280, 50));
        button.setPreferredSize(new Dimension(280, 50));

        // Hover efekti
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color);
                }
            }
        });

        return button;
    }

    private JPanel createFeaturesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Bus Features");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(PageComponents.TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] features = {
            "• Comfortable reclining seats",
            "• " + amenities,
            "• Professional driver service",
            "• Air conditioning",
            "• Safety certified"
        };

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            featureLabel.setForeground(PageComponents.SECONDARY_COLOR);
            featureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(featureLabel);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }


    private void clearSelection() {
        for (BusSeatButton seat : selectedSeats) {
            seat.setSelected(false);
        }
        selectedSeats.clear();
        updateSelectionInfo(); // Doğrudan çağrı çünkü bu içsel bir eylem
    }

    @Override
    public void update() {
        // Bu metot, SeatManager tarafından observer'ları (bu sayfa dahil) bilgilendirildiğinde çağrılır.
        updateSelectionInfo(); // Koltuk seçimi değişikliklerine göre UI'yı güncelle
    }

    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("No seat selection ");
            totalPriceLabel.setText("Total : 0.00 TL");
            confirmButton.setEnabled(false);
        } else {
            StringBuilder seatNumbers = new StringBuilder();
            double totalPrice = 0;

            for (int i = 0; i < selectedSeats.size(); i++) {
                if (i > 0) seatNumbers.append(", ");
                seatNumbers.append(selectedSeats.get(i).getSeatNumber());
                totalPrice += selectedSeats.get(i).getPrice();
            }

            selectedSeatsLabel.setText("Koltuklar: " + seatNumbers.toString());
            totalPriceLabel.setText(String.format("Total : $%.2f", totalPrice));

            confirmButton.setEnabled(selectedSeats.size() == passengerCount);
        }
    }

    // Modern otobüs koltuk butonları için iç sınıf
    private class BusSeatButton extends JButton {
        private int seatNumber;
        private boolean isOccupied;
        private boolean isSelected;
        private boolean isWindow;
        private boolean isPremium;
        private double price;
        private SeatManager seatManager; // SeatManager referansı

        public BusSeatButton(int seatNumber, boolean isOccupied, boolean isWindow, boolean isPremium) {
            this.seatNumber = seatNumber;
            this.isOccupied = isOccupied;
            this.isSelected = false;
            this.isWindow = isWindow;
            this.isPremium = isPremium;
            this.price = calculateSeatPrice();

            setupButton();
        }

        // SeatManager için setter metodu
        public void setSeatManager(SeatManager seatManager) {
            this.seatManager = seatManager;
        }

        private void setupButton() {
            setPreferredSize(new Dimension(48, 48));
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (isOccupied) {
                setText("X");
                setBackground(new Color(220, 53, 69)); // Kırmızı (dolu)
                setForeground(Color.WHITE);
                setEnabled(false);
                setToolTipText("Seat " + seatNumber + " occupied");
            } else {
                setText(isPremium ? "P" + seatNumber : String.valueOf(seatNumber));
                setBackground(isPremium ? new Color(255, 193, 7) : new Color(50, 205, 50)); // Sarı (premium) ya da Yeşil (mevcut)
                setForeground(Color.BLACK);
                setToolTipText(String.format("Seat %d - $%.2f%s%s",
                    seatNumber, price,
                    isWindow ? " (Window)" : "",
                    isPremium ? " (Premium)" : ""
                ));

                addActionListener(e -> toggleSelection());
            }

            setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1, true));
        }


        private double calculateSeatPrice() {
            double multiplier = 1.0;

            if (isPremium) multiplier += 0.3; // %30 premium ücreti
            if (isWindow) multiplier += 0.1;   // %10 pencere ücreti

            return basePriceValue * multiplier;
        }

        private void toggleSelection() {
            if (isSelected) {
                setSelected(false);
                selectedSeats.remove(this); // Ana sayfanın listesinden kaldır
            } else {
                if (selectedSeats.size() >= passengerCount) {
                    PageComponents.showStyledMessage("Warning",
                        "You can only select  " + passengerCount + " seat(s) !",
                        BusSeatSelectionPage.this);
                    return;
                }
                setSelected(true);
                selectedSeats.add(this); // Ana sayfanın listesine ekle
            }
            if (seatManager != null) {
                seatManager.update(); // SeatManager'ı bilgilendir, o da BusSeatSelectionPage'i bilgilendirecek
            }
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;

            if (selected) {
                setBackground(new Color(0, 180, 180)); // Seçili renk (turkuaz benzeri)
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(new Color(0, 140, 140), 2, true));
            } else {
                setBackground(isPremium ? new Color(255, 193, 7) : new Color(50, 205, 50)); // Normal veya premium renk
                setForeground(Color.BLACK);
                setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 1, true));
            }
        }


        public int getSeatNumber() {
            return seatNumber;
        }

        public double getPrice() {
            return price;
        }
    }
}