import 'appointment_model.dart';

class TagihanModel {
  final int id;
  final String kode;
  final String tanggalTerbuat;
  final String tanggalBayar;
  final bool isPaid;
  final int jumlahTagihan;
  // final Appointment appointmentId;

  const TagihanModel({
    required this.id,
    required this.kode,
    required this.tanggalTerbuat,
    required this.tanggalBayar,
    required this.isPaid,
    required this.jumlahTagihan,
    // required this.appointmentId
  });

  factory TagihanModel.fromJson(Map<String, dynamic> json) {
    return TagihanModel(
      id: json['id'],
      kode: json['kode'],
      tanggalTerbuat: json['tanggalTerbuat'],
      tanggalBayar: json['tanggalBayar'],
      isPaid: json['isPaid'],
      jumlahTagihan: json['jumlahTagihan'],
      // appointmentId: json['appointmentId']
    );
  }
}
