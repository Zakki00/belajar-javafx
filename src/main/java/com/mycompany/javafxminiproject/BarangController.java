package com.mycompany.javafxminiproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class BarangController implements Initializable {

    @FXML private TableView<Barang> tableBarang;
    @FXML private TableColumn<Barang, String> colKode;
    @FXML private TableColumn<Barang, String> colNama;
    @FXML private TableColumn<Barang, String> colKategori;
    @FXML private TableColumn<Barang, String> colHarga;
    @FXML private TableColumn<Barang, Integer> colStok;
    @FXML private TableColumn<Barang, Void> colAksi;
    @FXML private TextField searchField;
    @FXML private Label labelInfo;

    private ObservableList<Barang> dataBarang = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupKolom();
        setupKolomAksi();
        loadDummyData();
        setupSearch();
    }

    private void setupKolom() {
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
    }

    private void setupKolomAksi() {
        colAksi.setCellFactory(col -> new TableCell<>() {
            final Button btnEdit  = new Button("Edit");
            final Button btnHapus = new Button("Hapus");
            final HBox box = new HBox(6, btnEdit, btnHapus);

            {
                btnEdit.getStyleClass().add("btnEdit");
                btnHapus.getStyleClass().add("btnHapus");

                btnEdit.setOnAction(e -> {
                    Barang b = getTableView().getItems().get(getIndex());
                    handleEdit(b);
                });

                btnHapus.setOnAction(e -> {
                    Barang b = getTableView().getItems().get(getIndex());
                    handleHapus(b);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void loadDummyData() {
        dataBarang.addAll(
            new Barang("BRG-001", "Laptop ASUS",    "Elektronik", "Rp 8.500.000", 15),
            new Barang("BRG-002", "Kursi Gaming",   "Furnitur",   "Rp 1.200.000",  4),
            new Barang("BRG-003", "Mouse Logitech", "Elektronik", "Rp 350.000",    0),
            new Barang("BRG-004", "Meja Kerja",     "Furnitur",   "Rp 950.000",    8)
        );
        tableBarang.setItems(dataBarang);
        updateLabel();
    }

    private void setupSearch() {
        searchField.textProperty().addListener((obs, lama, baru) -> {
            if (baru.isEmpty()) {
                tableBarang.setItems(dataBarang);
            } else {
                ObservableList<Barang> hasil = FXCollections.observableArrayList();
                for (Barang b : dataBarang) {
                    if (b.getNama().toLowerCase().contains(baru.toLowerCase())
                     || b.getKode().toLowerCase().contains(baru.toLowerCase())) {
                        hasil.add(b);
                    }
                }
                tableBarang.setItems(hasil);
            }
            updateLabel();
        });
    }

    @FXML
    private void handleTambah() {
        // TODO: buka dialog form tambah barang
        showDialog("Tambah", null);
    }

    private void handleEdit(Barang b) {
        // TODO: buka dialog form edit barang
        showDialog("Edit", b);
    }

    private void handleHapus(Barang b) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Hapus Barang");
        confirm.setContentText("Hapus " + b.getNama() + "?");
        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                dataBarang.remove(b);
                updateLabel();
            }
        });
    }

    private void showDialog(String mode, Barang b) {
        // Contoh sederhana — bisa diganti dengan FXML dialog
        System.out.println(mode + ": " + (b != null ? b.getNama() : "baru"));
    }

    @FXML
    private void handleSearch() { /* ditangani listener */ }

    @FXML
    private void keDashboard() throws IOException {
        searchField.getScene().setRoot(
            FXMLLoader.load(getClass().getResource(
                "/com/mycompany/javafxminiproject/primary.fxml"))
        );
    }

    @FXML private void handlePrev()  { /* TODO: logika pagination */ }
    @FXML private void handleNext()  { /* TODO: logika pagination */ }
    @FXML private void handlePage2() { /* TODO: logika pagination */ }

    private void updateLabel() {
        labelInfo.setText("Menampilkan " + tableBarang.getItems().size() + " data");
    }

    /* ===== MODEL BARANG (inner class) ===== */
    public static class Barang {
        private final StringProperty  kode, nama, kategori, harga;
        private final IntegerProperty stok;

        public Barang(String kode, String nama, String kategori,
                      String harga, int stok) {
            this.kode     = new SimpleStringProperty(kode);
            this.nama     = new SimpleStringProperty(nama);
            this.kategori = new SimpleStringProperty(kategori);
            this.harga    = new SimpleStringProperty(harga);
            this.stok     = new SimpleIntegerProperty(stok);
        }

        public String  getKode()     { return kode.get(); }
        public String  getNama()     { return nama.get(); }
        public String  getKategori() { return kategori.get(); }
        public String  getHarga()    { return harga.get(); }
        public int     getStok()     { return stok.get(); }

        public void setKode(String v)     { kode.set(v); }
        public void setNama(String v)     { nama.set(v); }
        public void setKategori(String v) { kategori.set(v); }
        public void setHarga(String v)    { harga.set(v); }
        public void setStok(int v)        { stok.set(v); }

        public StringProperty  kodeProperty()     { return kode; }
        public StringProperty  namaProperty()     { return nama; }
        public StringProperty  kategoriProperty() { return kategori; }
        public StringProperty  hargaProperty()    { return harga; }
        public IntegerProperty stokProperty()     { return stok; }
    }
}