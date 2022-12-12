class AppointmentModel {
  late String kode;
  late String dokter;
  late String pasien;
  late String waktuAwal; // 'dd MMMM yyyy HH:mm'
  late String status; // 'Sudah Selesai' or 'Belum Dimulai'
  late String resepId;

  AppointmentModel(
      {required this.kode,
      required this.dokter,
      required this.pasien,
      required this.waktuAwal,
      required this.status,
      required this.resepId});

  factory AppointmentModel.fromJson(Map<String, dynamic> json) {
    return AppointmentModel(
        kode: json['kode'],
        dokter: json['dokter'],
        pasien: json['pasien'],
        waktuAwal: json['waktuAwal'],
        status: json['status'],
        resepId: json['resepId']);
  }
}
