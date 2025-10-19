package sistem;

import let.Let;

import java.awt.*;
import java.util.ArrayList;

public class PrikazLetovaPanel extends Panel {

    private Sistem parentSistem;
    public PrikazLetovaPanel(Sistem parentSistem) {
        this.parentSistem = parentSistem;
        setLayout(new BorderLayout());
    }
    public void osveziPanel(){
        removeAll();
        ArrayList<Let> letovi = parentSistem.letovi;
        int rows = letovi.size()+1;
        Panel data = new Panel(new GridLayout(rows, 5, 10, 6));
        data.add(new Label("POLAZNI", Label.CENTER));
        data.add(new Label("KRAJNJI", Label.CENTER));
        data.add(new Label("POLETANJE", Label.CENTER));
        data.add(new Label("TRAJANJE", Label.CENTER));
        data.add(new Label("SLETANJE", Label.CENTER));
        for (Let let : letovi) {
                String polazniKod = let.getPocetniAerodrom().getKod();
                String krajnjiKod = let.getKrajnjiAerodrom().getKod();
                String vremePoletanja = let.getVremePoletanja().toString();
                String trajanje = String.valueOf(let.getTrajanjeLeta());
                String sletanje = let.getVremeSletanja().toString();
                data.add(new Label(polazniKod,  Label.CENTER));
                data.add(new Label(krajnjiKod, Label.CENTER));
                data.add(new Label(vremePoletanja,  Label.CENTER));
                data.add(new Label(trajanje,  Label.CENTER));
                data.add(new Label(sletanje,   Label.CENTER));
        }
        add(data, BorderLayout.CENTER);
        validate();
        repaint();
        parentSistem.pack();
    }
}
