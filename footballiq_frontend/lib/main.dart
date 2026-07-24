import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:footballiq_frontend/core/theme/app_theme.dart';
import 'package:footballiq_frontend/features/matches/presentation/pages/home_page.dart';

void main() {
  runApp(const ProviderScope(child: FootballIQApp()));
}

class FootballIQApp extends StatelessWidget {
  const FootballIQApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FootballIQ',
      theme: AppTheme.darkTheme,
      home: const HomePage(),
      debugShowCheckedModeBanner: false,
    );
  }
}
