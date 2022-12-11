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


  factory Resep.fromJson(Map<String, dynamic> json) {
    return Resep(
        id: json['id'],
        tanggalWaktu: json['tanggalWaktu'],
        dokter: json['dokter'],
        pasien: json['pasien'],
        status: json['status'],
        apoteker: json['apoteker'],
        jumlah: json['jumlah']);
  }
}