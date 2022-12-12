import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:rumahsehat_app/profilepage.dart';
<<<<<<< HEAD
import 'package:rumahsehat_app/saldoform.dart';
import 'package:rumahsehat_app/splash_screen.dart';
=======
import 'package:rumahsehat_app/providers/auth.dart';
import 'package:rumahsehat_app/viewall_tagihan.dart';
>>>>>>> 90a5f0c71b4d0b8026a20290c5c23812d102b88b
import 'appointment_form.dart';
import 'appointment_index.dart';
import 'main.dart';

class NavigationDrawer extends StatelessWidget {
  const NavigationDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          DrawerHeader(child: Text("Navigation Menu")),
          ListTile(
            leading: Icon(Icons.home),
            title: Text('Home'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => MyHomePage()),
              );
            },
          ),
          ListTile(
<<<<<<< HEAD
                leading: Icon(Icons.person),
                title: Text("Profile"),
                onTap: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const ProfilePageState()));
                },
              ),
          ListTile(
            leading: Icon(Icons.attach_money_outlined),
            title: Text('Saldo'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) =>
                        FormSaldo()), // harusnya classnya Home()
              );
=======
            leading: Icon(Icons.person),
            title: Text("Profile"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const ProfilePageState()));
            },
          ),
          ListTile(
            leading: Icon(Icons.money),
            title: Text("View All Tagihan"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const ViewAllTagihanScreen()));
>>>>>>> 90a5f0c71b4d0b8026a20290c5c23812d102b88b
            },
          ),
          ExpansionTile(
            title: Text("Appointment"),
            leading: Icon(Icons.calendar_today_sharp),
            children: <Widget>[
              ListTile(
                leading: Icon(Icons.add),
                title: Text('Create'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => AppointmentForm()),
                  );
                },
              ),
              ListTile(
                leading: Icon(Icons.calendar_today_sharp),
                title: Text('View'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => AppointmentViewAll()),
                  );
                },
              ),
<<<<<<< HEAD
                
            ],
          ),
          Container(
                          padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                          child: ElevatedButton(
                            child: const Text(
                              "Logout",
                            ),
                            onPressed: () {
                              Navigator.push(context,
                              MaterialPageRoute(builder: (context) => SplashScreen()));
                            },
                          ),
                        ),
=======
            ],
          ),
          Container(
            padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
            child: ElevatedButton(
              child: const Text(
                "Logout",
              ),
              onPressed: () =>
                  Provider.of<Authentication>(context, listen: false).logout(),
            ),
          ),
>>>>>>> 90a5f0c71b4d0b8026a20290c5c23812d102b88b
        ],
      ),
    );
  }
}
