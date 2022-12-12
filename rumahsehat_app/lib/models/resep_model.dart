class Resep{
  final String id;
  final String tanggalWaktu;
  final String dokter;
  final String pasien;
  final String status;
  final String apoteker;
  final List<String> jumlah;

  Resep(
    {required this.id,
    required this.tanggalWaktu,
    required this.dokter,
    required this.pasien,
    required this.status,
    required this.apoteker,
    required this.jumlah});


  factory Resep.fromJson(Map<String, dynamic> parsedJson) {
    var jumlahFromJson= parsedJson['jumlah'];
    List<String> jumlah= jumlahFromJson.cast<String>();

    return Resep(
        id: parsedJson['id'],
        tanggalWaktu: parsedJson['tanggalWaktu'],
        dokter: parsedJson['dokter'],
        pasien: parsedJson['pasien'],
        status: parsedJson['status'],
        apoteker: parsedJson['apoteker'],
        jumlah: jumlah);
  }
}