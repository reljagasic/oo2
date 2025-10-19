package aerodrom;

import java.util.Objects;

public class Aerodrom {
    private String naziv;
    private String kod;
    private int x, y;

    public Aerodrom(String naziv, String kod, int x, int y) throws IllegalAirportDataException {
        setX(x);
        setY(y);
        setKod(kod);

        this.naziv = naziv;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws IllegalAirportDataException {
        if (y < -90 || y > 90) {
            throw new IllegalAirportDataException("Nepravilan unos koordinata: opseg mora biti (-90, 90). ");
        }
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws IllegalAirportDataException {
        if(x< -90 || x>90) {
            throw new IllegalAirportDataException("Nepravilan unos koordinata: opseg mora biti (-90, 90). ");
        }
        this.x = x;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) throws IllegalAirportDataException {
        if (kod == null || kod.length() != 3 || !kod.equals(kod.toUpperCase())) {
            throw new IllegalAirportDataException("Nepravilan unos koda aerodroma: kod mora sadrzati 3 uppercase slova. ");
        }
        this.kod = kod;

    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aerodrom)) return false;
        Aerodrom aerodrom = (Aerodrom) o;
        return x == aerodrom.x && y == aerodrom.y && Objects.equals(naziv, aerodrom.naziv) && Objects.equals(kod, aerodrom.kod);
    }

    // metoda za proveru da li su na istom mestu

    public boolean isteKoordinate(Object o){
        if (!(o instanceof Aerodrom)) return false;
        Aerodrom aerodrom = (Aerodrom) o;
        return x == aerodrom.x && y == aerodrom.y;
    }

    @Override
    public String toString() {
        return naziv + ',' + kod + ',' + x + ',' + y;
    }
}

