public class Livre {
    // Attributs
    private String title;
    private String author;
    private String edition;
    private int year;
    private String genre;
    private String location;

    public Livre(String title, String author, String edition, int year, String genre, String location) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.year = year;
        this.genre = genre;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Titre: " + title + ", Auteur: " + author + ", Edition: " + edition + ", Année: " + year + ", Genre: "
                + genre + ", Emplacement: " + location;
    }
}
