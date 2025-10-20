package sistem;

import let.Let;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class PrikazLetovaPanel extends Panel {

    private Sistem parentSistem;
    private Choice izbor;
    public PrikazLetovaPanel(Sistem parentSistem) {
        this.parentSistem = parentSistem;
        setLayout(new BorderLayout());
        izbor = new Choice();
        izbor.add("Domaci");
        izbor.add("Medjunarodni");
        izbor.add("Svi");
        add(izbor, BorderLayout.NORTH);
        ArrayList<Let> filtriraniLetovi =  new ArrayList<>();
        izbor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ArrayList<Let> filtriraniLetovi =  new ArrayList<>();
                String selektovano = izbor.getSelectedItem().toString();
                if(selektovano.equals("Domaci")){
                    for (Let l : parentSistem.letovi) {
                        if (l.getTip() == Let.tipLeta.DOMACI){
                            filtriraniLetovi.add(l);
                        }
                    }
                } else if (selektovano.equals("Medjunarodni")){
                    for (Let l : parentSistem.letovi) {
                        if (l.getTip() == Let.tipLeta.MEDJUNARODNI){
                            filtriraniLetovi.add(l);
                        }
                    }
                } else{
                    filtriraniLetovi = parentSistem.letovi;
                }
                osveziPanel(filtriraniLetovi);
            }
        });
    }
    public void osveziPanel(ArrayList<Let> letovi) {
        removeAll();
//        ArrayList<Let> letovi = parentSistem.letovi;
        add(izbor,  BorderLayout.NORTH);
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
