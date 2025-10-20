package sistem;
import aerodrom.*;
import let.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sistem extends Frame {

    ArrayList<Aerodrom> aerodromi = new ArrayList<>();
    ArrayList<Let> letovi = new ArrayList<>();
    private PrikazLetovaPanel prikazLetovaPanel;
    private PrikazAerodromaPanel prikazAerodromaPanel;
    private Timer timer;
    FileSistem fileSistem;

    public Aerodrom dohvatiAerodromPoKodu(String kod) throws IllegalAirportDataException {
        for (Aerodrom a : aerodromi){
            if (a.getKod().equals(kod)){
                return a;
            }
        }
        throw new IllegalAirportDataException("Aerodrom sa kodom " + kod + " nije u sistemu");
    }

    private void resetTimer(){
        if(timer != null){
            timer.activity();
        }
    }

    private void setupActivityListeners(){
        MouseAdapter mouseListener = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                resetTimer();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                resetTimer();
            }
        };
        KeyAdapter keyListener = new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                resetTimer();
            }
        };
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        prikazLetovaPanel.addMouseListener(mouseListener);
        prikazLetovaPanel.addMouseMotionListener(mouseListener);
        prikazLetovaPanel.addKeyListener(keyListener);

        this.setFocusable(true);
        prikazLetovaPanel.setFocusable(true);
    }

    void dodajAerodrom(Aerodrom aerodrom) throws IllegalDialogInputException {
        if (aerodromi.contains(aerodrom)) {
            throw new IllegalDialogInputException("Aerodrom je vec dodat u sistem.");
        }
        for (Aerodrom a : aerodromi){
            if (a.getKod().equals(aerodrom.getKod())){
                throw new IllegalDialogInputException("Uneti kod je vec dodat u sistem.");
            }
            if (a.getNaziv().equals(aerodrom.getNaziv())){
                throw new IllegalDialogInputException("Uneti naziv je vec dodat u sistem.");
            }
            if (a.getX() == aerodrom.getX() && a.getY() == aerodrom.getY()){
                throw new IllegalDialogInputException("Unete koordinate su vec u sistemu.");
            }
        }
        aerodromi.add(aerodrom);
        System.out.println("Dodat aerodrom: " + aerodrom.toString());
        if (prikazAerodromaPanel != null) {
            prikazAerodromaPanel.osveziPanel();
        }
        Point trenutnaLokacija = getLocation();
        setLocation(trenutnaLokacija);
        pack();
        validate();
        repaint();
    }
    void dodajLet(Let let) throws IllegalDialogInputException {
        if (letovi.contains(let)) {
            throw new IllegalDialogInputException("Let je vec dodat u sistem.");
        }
        letovi.add(let);
        System.out.println("Dodat let: "+ let.toString());
        if(prikazLetovaPanel != null){
            prikazLetovaPanel.osveziPanel(this.letovi);
        }
        // prozor stoji u istom mestu sve vreme
        Point trenutnaLokacija = getLocation();
        setLocation(trenutnaLokacija);
        pack();
        validate();
        repaint();
    }

    private void populateWindow(){
        // prozorce neko
        // ima menu bar na kom su opcije da se dodaje let
        // u njemu panel koji pravi tabele za podatke o letovima
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuDodaj = new Menu("Dodaj");
        MenuItem dodajAerodrom = new MenuItem("Dodaj Aerodrom");
        MenuItem dodajLet = new MenuItem("Dodaj Let");
        MenuItem importAerodromi = new MenuItem("Import aerodromi.txt");
        MenuItem importLetovi = new MenuItem("Import letovi.txt");
        MenuItem exportAerodromi = new MenuItem("Export aerodromi.txt");
        MenuItem exportLetovi = new MenuItem("Export letovi.txt");
        Button Aerodromi = new Button("Aerodromi");
        Button Letovi = new Button("Letovi");
        menuDodaj.add(dodajAerodrom);
        menuDodaj.add(dodajLet);
        menuFile.add(importAerodromi);
        menuFile.add(importLetovi);
        menuFile.add(exportAerodromi);
        menuFile.add(exportLetovi);
        menuBar.add(menuDodaj);
        menuBar.add(menuFile);
        setMenuBar(menuBar);
        CardLayout layout = new CardLayout();
        Panel cardContainer = new Panel(layout);
        prikazLetovaPanel = new PrikazLetovaPanel(this);
        prikazAerodromaPanel = new PrikazAerodromaPanel(this);
        cardContainer.add(prikazLetovaPanel, "Letovi");
        cardContainer.add(prikazAerodromaPanel,  "Aerodromi");
        setLayout(new BorderLayout());
        add(cardContainer, BorderLayout.CENTER);
        Panel dugmad = new Panel(new FlowLayout());
        dugmad.add(Aerodromi);
        dugmad.add(Letovi);
        add(dugmad, BorderLayout.SOUTH);
        //add(prikazLetovaPanel, BorderLayout.CENTER);
        dodajAerodrom.addActionListener(e -> {
            new DodajAerodromDijalog(this);
        });
        dodajLet.addActionListener(e -> {
            new DodajLetDIjalog(this);
        });
        Aerodromi.addActionListener(e -> {
            layout.show(cardContainer, "Aerodromi");
            prikazAerodromaPanel.osveziPanel();
            pack();
            validate();
            resetTimer();
        });
        Letovi.addActionListener(e -> {
            layout.show(cardContainer, "Letovi");
            prikazLetovaPanel.osveziPanel(this.letovi);
            pack();
            validate();
            resetTimer();
        });
        importAerodromi.addActionListener(e -> {
            try {
                List<Aerodrom> ucitaniAerodromi = FileSistem.ucitajAerodrome(this);
                for (Aerodrom a : ucitaniAerodromi) {
                    dodajAerodrom(a);
                }
            } catch (IllegalDialogInputException | IOException ex) {
                new ErrorDialog(this, ex.getMessage());
            }
        });
        importLetovi.addActionListener(e -> {
            try {
                List<Let> ucitaniLetovi = FileSistem.ucitajLetove(aerodromi, this);
                for (Let a : ucitaniLetovi) {
                    dodajLet(a);
                }
            } catch (IOException | IllegalDialogInputException ex) {
                new ErrorDialog(this, ex.getMessage());
            } catch (Exception ex) {
                new ErrorDialog(this, ex.getMessage());
            }
        });
        exportAerodromi.addActionListener(e -> {
            try {
                FileSistem.ispisiAerodrome(aerodromi);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        exportLetovi.addActionListener(e -> {
            try {
                FileSistem.ispisiLetove(letovi);
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public Sistem() {

        setBounds(700, 200, 600, 600);
        setResizable(true);
        setTitle("Sistem za kontrolu leta");
        populateWindow();
        prikazLetovaPanel.osveziPanel(this.letovi);
        timer = new Timer(this);
        timer.start();
        setupActivityListeners();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (timer != null) {
                    timer.shutdown();
                }
                dispose();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Sistem sistem = new Sistem();
    }

}
