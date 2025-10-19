package sistem;

import aerodrom.Aerodrom;
import aerodrom.IllegalAirportDataException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DodajAerodromDijalog extends Dialog {


    private TextField poljeNaziv, poljeKod, poljeX, poljeY;
    private Sistem parentSistemFrame;
    private Button dugmeDodaj;
    private Label labelaGreska;

    private void populateDialog(){
        poljeNaziv = new TextField(10);
        poljeKod = new TextField(10);
        poljeX = new TextField(10);
        poljeY = new TextField(10);
        dugmeDodaj = new Button("Dodaj");

        labelaGreska = new Label();
        labelaGreska.setForeground(Color.RED);
        labelaGreska.setAlignment(Label.CENTER);

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridLayout(4,2,10,5));

        inputPanel.add(new Label("Naziv:"));
        inputPanel.add(poljeNaziv);
        inputPanel.add(new Label("Kod:"));
        inputPanel.add(poljeKod);
        inputPanel.add(new Label("X:"));
        inputPanel.add(poljeX);
        inputPanel.add(new Label("Y:"));
        inputPanel.add(poljeY);
        //inputPanel.add(dugmeDodaj);
        //inputPanel.add(labelaGreska);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);

        Panel bottomPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(dugmeDodaj);
        //bottomPanel.add(labelaGreska);

        add(bottomPanel, BorderLayout.SOUTH);

        dugmeDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pokusajDodavanjeAerodroma();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void pokusajDodavanjeAerodroma() throws Exception {
        String naziv = poljeNaziv.getText();
        String kod = poljeKod.getText();
        String xString = poljeX.getText();
        String yString = poljeY.getText();

        int x,y;

        try {
            try{
                if (naziv.length() == 0 || kod.length() == 0 || xString.length() == 0 || yString.length() == 0){
                    throw new IllegalAirportDataException("Polja za unos ne smeju biti prazna");
                }
                x = Integer.parseInt(xString);
                y = Integer.parseInt(yString);
            } catch(NumberFormatException e){
                throw new Exception("Koordinate x i y moraju biti unete kao celi brojevi");
            }
            Aerodrom noviAerodrom = new Aerodrom(naziv, kod, x, y);
            parentSistemFrame.dodajAerodrom(noviAerodrom);
            dispose();
        } catch (IllegalAirportDataException | IllegalDialogInputException e){
            new ErrorDialog(this, e.getMessage());

        } catch (Exception e) {
            new ErrorDialog(this, e.getMessage());
        }

    }

    public DodajAerodromDijalog(Sistem parent) {
        super(parent, "Dodaj novi aerodrom", true);
        this.parentSistemFrame = parent;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        //setLayout(new BorderLayout(5, 5));
        populateDialog();
        pack();
        //setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);

    }
}
