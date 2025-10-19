package sistem;

import aerodrom.Aerodrom;
import let.Let;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DodajLetDIjalog extends Dialog {

    private TextField pocetniAerodrom, krajnjiAerodrom, vremePoletanja, trajanjeLeta;
    private Sistem parentSistemFrame;
    private Button dugmeDodaj;
    private Label labelaGreska;

    private void populateDialog(){
        pocetniAerodrom = new TextField(10);
        krajnjiAerodrom = new TextField(10);
        vremePoletanja = new TextField(10);
        trajanjeLeta = new TextField(10);
        dugmeDodaj = new Button("Dodaj");

        labelaGreska = new Label();
        labelaGreska.setForeground(Color.RED);
        labelaGreska.setAlignment(Label.CENTER);

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 5));
        inputPanel.add(new Label("Pocetni aerodrom:"));
        inputPanel.add(pocetniAerodrom);
        inputPanel.add(new Label("Krajnji aerodrom:"));
        inputPanel.add(krajnjiAerodrom);
        inputPanel.add(new Label("Vreme poletanja:"));
        inputPanel.add(vremePoletanja);
        inputPanel.add(new Label("Trajanje leta:"));
        inputPanel.add(trajanjeLeta);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
        Panel bottomPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(dugmeDodaj);
        add(bottomPanel, BorderLayout.SOUTH);
        dugmeDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pokusajDodavanjeLeta();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void pokusajDodavanjeLeta() throws Exception {

        String kodPocetniAerodrom = pocetniAerodrom.getText();
        String kodKrajnjiAerodrom = krajnjiAerodrom.getText();
        String stringVremePoletanja = vremePoletanja.getText().trim();
        String stringTrajanjeLeta = trajanjeLeta.getText();

        final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        Aerodrom pocetni, krajnji;
        int trajanjeLetaMinuti;
        LocalTime vremePoletanjaLocalTime;

        try{
            try{
                trajanjeLetaMinuti = Integer.parseInt(stringTrajanjeLeta);
            } catch (NumberFormatException e) {
                throw new Exception("Trajanje leta mora biti uneto kao ceo broj, predstavlja minute");
            }
            pocetni = parentSistemFrame.dohvatiAerodromPoKodu(kodPocetniAerodrom);
            krajnji = parentSistemFrame.dohvatiAerodromPoKodu(kodKrajnjiAerodrom);
            Let noviLet = new Let(pocetni, krajnji, stringVremePoletanja, trajanjeLetaMinuti);
            parentSistemFrame.dodajLet(noviLet);
            dispose();
        }catch (DateTimeParseException e){
            new ErrorDialog(this, "Pogresan format vremena poletanja, HH:mm");
        }
        catch(Exception e){
            new ErrorDialog(this, e.getMessage());
        }
    }

    public DodajLetDIjalog(Sistem parent) {
        super(parent, "Dodaj novi aerodrom", true);
        this.parentSistemFrame = parent;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        populateDialog();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

}
