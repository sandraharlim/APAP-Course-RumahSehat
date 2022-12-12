class TagihanModel {
  final String kode;
  final String tanggalTerbuat;
  final String tanggalBayar;
  final bool isPaid;
  final int jumlahTagihan;

  const TagihanModel({
    required this.kode,
    required this.tanggalTerbuat,
    required this.tanggalBayar,
    required this.isPaid,
    required this.jumlahTagihan,
  });

  factory TagihanModel.fromJson(Map<String, dynamic> json) {
    return TagihanModel(
      kode: json['kode'],
      tanggalTerbuat: json['tanggalTerbuat'],
      tanggalBayar: json['tanggalBayar'] ?? "Belum dibayar",
      isPaid: json['isPaid'],
      jumlahTagihan: json['jumlahTagihan'],
    );
  }
}
