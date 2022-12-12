import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:rumahsehat_app/login_screen.dart';
import 'package:rumahsehat_app/models/appointment_pasien_card.dart';
import 'package:rumahsehat_app/models/pasienmodel.dart';
import 'package:rumahsehat_app/profilepage.dart';
import 'package:rumahsehat_app/providers/auth.dart';
import 'appointment_index.dart';
import 'navbar.dart';
import 'appointment_form.dart';
import 'package:rumahsehat_app/splash_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (ctx) => Authentication(),
        ),
        ChangeNotifierProxyProvider<Authentication, Appointment>(
            create: (context) => Appointment(),
            update: (context, auth, appointment) =>
                appointment!..updateData(auth.token)),
        ChangeNotifierProxyProvider<Authentication, PasienNotifier>(
            create: (context) => PasienNotifier(),
            update: (context, auth, pasien) => pasien!..updateData(auth.token)),
      ],
      builder: (context, child) => Consumer<Authentication>(
        builder: (context, auth, child) => MaterialApp(
          title: 'Rumah Sehat',
          debugShowCheckedModeBanner: false,
          theme: ThemeData(
            primarySwatch: Colors.lightBlue,
          ),
          // home: SplashScreen(),
          home: auth.isAuth ? MyHomePage() : LoginScreen(),
          // routes: {
          //   AddProductPage.route (ctx) => AddProductPage(),
          // },
        ),
      ),
    );
  }
}

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Rumah Sehat"),
      ),
      drawer: const NavigationDrawer(),
      body: Container(
        child: Padding(
          padding: const EdgeInsets.all(10.0),
          child: GridView(
            children: [
              InkWell(
                onTap: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => AppointmentForm()));
                },
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    color: Color.fromARGB(255, 119, 176, 233),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: const [
                      Icon(
                        Icons.book,
                        size: 50,
                        color: Colors.white,
                      ),
                      Text(
                        "Buat Jadwal Appointment",
                        style: TextStyle(color: Colors.white, fontSize: 20),
                      )
                    ],
                  ),
                ),
              ),
              InkWell(
                onTap: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => AppointmentViewAll()));
                },
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    color: Color.fromARGB(255, 119, 176, 233),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: const [
                      Icon(
                        Icons.book,
                        size: 50,
                        color: Colors.white,
                      ),
                      Text(
                        "Lihat Jadwal Appointment",
                        style: TextStyle(color: Colors.white, fontSize: 20),
                      )
                    ],
                  ),
                ),
              ),
              InkWell(
                onTap: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => MyHomePage()));
                },
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    color: Color.fromARGB(255, 119, 176, 233),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: const [
                      // Icon(
                      //   Icons.add_card,
                      //   size: 50,
                      //   color: Colors.white,
                      // ),
                      Text(
                        "Lihat Daftar Tagihan",
                        style: TextStyle(color: Colors.white, fontSize: 20),
                      )
                    ],
                  ),
                ),
              ),
            ],
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2, mainAxisSpacing: 10, crossAxisSpacing: 10),
          ),
        ),
      ),
    );
  }
}