package let;
import aerodrom.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Let {
    private Aerodrom pocetniAerodrom;
    private Aerodrom krajnjiAerodrom;
    private LocalTime vremePoletanja;
    private int trajanjeLeta;
    final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Let(Aerodrom pocetniAerodrom, Aerodrom krajnjiAerodrom, LocalTime vremePoletanja, int trajanjeLeta) throws IllegalFlightDataException {
        setPocetniAerodrom(pocetniAerodrom);
        setKrajnjiAerodrom(krajnjiAerodrom);
        this.vremePoletanja = vremePoletanja;
        setTrajanjeLeta(trajanjeLeta);
        proveriAerodrome(pocetniAerodrom, krajnjiAerodrom);
    }

    public Let(Aerodrom pocetni, Aerodrom krajnji, String vremePoletanja, int trajanjeLeta) throws IllegalFlightDataException, DateTimeParseException {
        setPocetniAerodrom(pocetni);
        setKrajnjiAerodrom(krajnji);
        dodajVrenePoletenjaizStringa(vremePoletanja);
        proveriAerodrome(pocetni, krajnji);
        setTrajanjeLeta(trajanjeLeta);
    }

    public void dodajVrenePoletenjaizStringa(String vremePoletenja) throws DateTimeParseException {
        LocalTime vremePoletanjaLocalTime = LocalTime.parse(vremePoletenja,TIME_FORMATTER);
        this.vremePoletanja = vremePoletanjaLocalTime;
    }

    public Aerodrom getKrajnjiAerodrom() {
        return krajnjiAerodrom;
    }

    public void setKrajnjiAerodrom(Aerodrom krajnjiAerodrom) {
        this.krajnjiAerodrom = krajnjiAerodrom;
    }

    public Aerodrom getPocetniAerodrom() {
        return pocetniAerodrom;
    }

    public void setPocetniAerodrom(Aerodrom pocetniAerodrom) {
        this.pocetniAerodrom = pocetniAerodrom;
    }

    public LocalTime getVremePoletanja() {
        return vremePoletanja;
    }

    public void setVremePoletanja(LocalTime vremePoletanja) {
        this.vremePoletanja = vremePoletanja;
    }

    public int getTrajanjeLeta() {
        return trajanjeLeta;
    }

    private void proveriAerodrome(Aerodrom a, Aerodrom b) throws IllegalFlightDataException {
        if(a == null || b == null) {
            throw new IllegalFlightDataException("Pocetni i krajnji aerodrom se moraju uneti");
        }
        if(a.equals(b)) {
            throw new IllegalFlightDataException("Pocetni i krajnji aerodrom moraju biti razliciti");
        }
    }

    public void setTrajanjeLeta(int trajanjeLeta) throws IllegalFlightDataException{
        if (trajanjeLeta <= 0){
            throw new IllegalFlightDataException("Trajanje leta mora biti vece od nule.");
        }
        this.trajanjeLeta = trajanjeLeta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Let)) return false;
        Let let = (Let) o;
        return vremePoletanja.equals(let.vremePoletanja) && trajanjeLeta == let.trajanjeLeta && Objects.equals(pocetniAerodrom, let.pocetniAerodrom) && Objects.equals(krajnjiAerodrom, let.krajnjiAerodrom);
    }

    @Override
    public String toString() {
        String vreme = vremePoletanja.format(TIME_FORMATTER);
        return pocetniAerodrom.getKod() + ',' + krajnjiAerodrom.getKod() + ',' + vreme + ',' + trajanjeLeta;
    }
    public LocalTime getVremeSletanja() {

        Duration duration = Duration.ofMinutes(this.trajanjeLeta);
        return this.vremePoletanja.plus(duration);
    }


}
