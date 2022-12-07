class Appointment {
  late String kode;
  late String dokter;
  late String pasien;
  late String waktuAwal; // 'dd MMMM yyyy HH:mm'
  late String status; // 'Sudah Selesai' or 'Belum Dimulai'

  Appointment(
      {required this.kode,
      required this.dokter,
      required this.pasien,
      required this.waktuAwal,
      required this.status});

  factory Appointment.fromJson(Map<String, dynamic> json) {
    return Appointment(
        kode: json['kode'],
        dokter: json['dokter'],
        pasien: json['pasien'],
        waktuAwal: json['waktuAwal'],
        status: json['status']);
  }
}
