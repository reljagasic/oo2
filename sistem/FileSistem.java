package sistem;

import aerodrom.Aerodrom;
import aerodrom.IllegalAirportDataException;
import let.IllegalFlightDataException;
import let.Let;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FileSistem {

    public static List<Aerodrom> ucitajAerodrome(Sistem parentSistem) throws IOException {
        List<Aerodrom> aerodromi = new ArrayList<>();
        File fajl = new File("aerodromi.txt");

//        System.out.println("Trenutni direktorijum: " + new File(".").getAbsolutePath());
//        System.out.println("Da li fajl postoji: " + fajl.exists());

        if (!fajl.exists()) {
            new ErrorDialog(parentSistem, "Fajl " + fajl.getAbsolutePath() + " nije pronadjen!");
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fajl))) {
            String linija;
            int brojLinija = 0;

            while ((linija = br.readLine()) != null) {
                brojLinija++;
                String[] delovi = linija.split(",");

                if (delovi.length != 4) {
                    throw new IOException("Greska u liniji " + brojLinija + ": neispravan broj kolona, treba biti 4");
                }

                String naziv = delovi[0].trim();
                String kod = delovi[1].trim();
                int x, y;
                try {
                    x = Integer.parseInt(delovi[2].trim());
                    y = Integer.parseInt(delovi[3].trim());
                } catch (NumberFormatException ex) {
                    throw new IOException("Greska u liniji " + brojLinija + ": koordinate nisu brojevi");
                }

                Aerodrom a = new Aerodrom(naziv, kod, x, y);
                aerodromi.add(a);

            }
            if (brojLinija == 0) {
                throw new IOException("Fajl " + fajl.getAbsolutePath() + " je prazan");
            }

        } catch (IOException | IllegalAirportDataException e) {
            new ErrorDialog(parentSistem, e.getMessage());
            return new ArrayList<>();
        }

        return aerodromi;
    }

    public static List<Let> ucitajLetove(List<Aerodrom> postojeciAerodromi, Sistem parentSistem) throws Exception {
        List<Let> letovi = new ArrayList<>();
        File fajl = new File("letovi.txt");

        System.out.println("Trenutni direktorijum: " + new File(".").getAbsolutePath());
        System.out.println("Da li fajl postoji: " + fajl.exists());

        if (!fajl.exists()) {
            new ErrorDialog(parentSistem, "Fajl " + fajl.getAbsolutePath() + " nije pronadjen!");
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fajl))) {
            String linija;
            int brojLinija = 0;

            while ((linija = br.readLine()) != null) {
                brojLinija++;
                String[] delovi = linija.split(",");

                if (delovi.length != 4) {
                    throw new IOException("Greska u liniji " + brojLinija + ": neispravan broj kolona, treba biti 4");
                }

                String pocetniAerodrom = delovi[0].trim();
                String krajnjiAerodrom = delovi[1].trim();
                String vremePoletanja = delovi[2].trim();
                String trajanjeLeta = delovi[3].trim();
                //int x, y;
                final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
                Aerodrom pocetni, krajnji;
                int trajanjeLetaMinuti;
                LocalTime vremePoletanjaLocalTime;
                try{
                    trajanjeLetaMinuti = Integer.parseInt(trajanjeLeta);
                } catch (NumberFormatException e) {
                    throw new IOException("Greska u liniji " + brojLinija + ": neispravan format trajanja leta, mora biti ceo broj");
                }
                pocetni = dohvatiAerodromPoKodu(pocetniAerodrom, postojeciAerodromi);
                krajnji = dohvatiAerodromPoKodu(krajnjiAerodrom, postojeciAerodromi);
                Let noviLet = new Let(pocetni, krajnji, vremePoletanja, trajanjeLetaMinuti);
                letovi.add(noviLet);
            }
        } catch (Exception e) {
            new ErrorDialog(parentSistem, e.getMessage());
            return new ArrayList<>();
        }

        return letovi;
    }

    public static Aerodrom dohvatiAerodromPoKodu(String kod, List<Aerodrom> aerodromi) throws IllegalAirportDataException {
        for (Aerodrom a : aerodromi){
            if (a.getKod().equals(kod)){
                return a;
            }
        }
        throw new IllegalAirportDataException("Aerodrom " + kod + " nije unet u sistem");
    }

    public static void ispisiAerodrome(List<Aerodrom> aerodromi){
        // pravi se buffered writer u nekoj while petlji
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("aerodromi.txt"))){
            int cnt = aerodromi.size();
            for (Aerodrom a : aerodromi){
                cnt--;
                String aerodrom = a.toString();
                bw.write(aerodrom);
                if(cnt != 0){
                    bw.newLine();
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void ispisiLetove(List<Let> letovi){
        // pravi se buffered writer u nekoj while petlji
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("letovi.txt"))){
            int cnt = letovi.size();
            for (Let a : letovi){
                cnt--;
                String let = a.toString();
                bw.write(let);
                if(cnt != 0){
                    bw.newLine();
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
