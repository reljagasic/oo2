package sistem;

import aerodrom.Aerodrom;
import let.Let;

import java.awt.*;
import java.util.ArrayList;

public class PrikazAerodromaPanel extends Panel {
    private Sistem parentSistem;
    public PrikazAerodromaPanel(Sistem parentSistem) {
        this.parentSistem = parentSistem;
        setLayout(new BorderLayout());
    }
    public void osveziPanel(){
        removeAll();
        ArrayList<Aerodrom> aerodromi = parentSistem.aerodromi;
        int rows = aerodromi.size()+1;
        Panel data = new Panel(new GridLayout(rows, 4, 10, 6));
        data.add(new Label("NAZIV", Label.CENTER));
        data.add(new Label("KOD", Label.CENTER));
        data.add(new Label("X", Label.CENTER));
        data.add(new Label("Y", Label.CENTER));
        for  (Aerodrom aerodrom : aerodromi) {
                data.add(new Label(aerodrom.getNaziv(), Label.CENTER));
                data.add(new Label(aerodrom.getKod(), Label.CENTER));
                data.add(new Label(String.valueOf(aerodrom.getX()), Label.CENTER));
                data.add(new Label(String.valueOf(aerodrom.getY()), Label.CENTER));
        }
        add(data, BorderLayout.CENTER);
        validate();
        repaint();
        parentSistem.pack();
    }
}
