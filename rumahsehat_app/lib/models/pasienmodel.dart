class Pasien {
  late String uuid;
  late String nama;
  late String email;
  late String username;
  late int saldo;

  Pasien(
      {required this.uuid,
      required this.nama,
      required this.email,
      required this.username,
      required this.saldo});

  factory Pasien.fromJson(Map<String, dynamic> json) {
    return Pasien(
        uuid: json['uuid'],
        nama: json['nama'],
        email: json['email'],
        username: json['username'],
        saldo: json['saldo']);
  }
}
