import 'package:flutter/material.dart';
// import 'create_appointment_form.dart';
import 'date_time_picker.dart';

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
                MaterialPageRoute(builder: (context) => AppointmentForm()), // harusnya classnya Home()
              );
            },
          ),
          ExpansionTile(
            title: Text("Appointment"),
            // leading: FaIcon(FontAwesomeIcons.syringe),
            leading: Icon(Icons.calendar_today_sharp),
            children: <Widget>[
              ListTile(
                // leading: FaIcon(FontAwesomeIcons.syringe),
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
                    MaterialPageRoute(builder: (context) => AppointmentForm()), // harusnya class index aja
                  );
                },
              ),
            ],
          )
        ],
      ),
    );
  }
}
