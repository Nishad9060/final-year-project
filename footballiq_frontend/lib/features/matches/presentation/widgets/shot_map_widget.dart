import 'package:flutter/material.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_details_entity.dart';

class ShotMapWidget extends StatelessWidget {
  final List<ShotEntity> shots;

  const ShotMapWidget({super.key, required this.shots});

  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1.5,
      child: CustomPaint(
        painter: PitchPainter(shots: shots),
      ),
    );
  }
}

class PitchPainter extends CustomPainter {
  final List<ShotEntity> shots;

  PitchPainter({required this.shots});

  @override
  void paint(Canvas canvas, Size size) {
    final pitchPaint = Paint()
      ..color = const Color(0xFF2E7D32) // Pitch green
      ..style = PaintingStyle.fill;

    final linePaint = Paint()
      ..color = Colors.white.withOpacity(0.5)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2.0;

    // Draw Pitch
    final pitchRect = Rect.fromLTWH(0, 0, size.width, size.height);
    canvas.drawRect(pitchRect, pitchPaint);

    // Draw Halfway Line
    canvas.drawLine(
        Offset(size.width / 2, 0), Offset(size.width / 2, size.height), linePaint);

    // Draw Center Circle
    canvas.drawCircle(Offset(size.width / 2, size.height / 2), size.height / 5, linePaint);

    // Draw Penalty Areas
    canvas.drawRect(Rect.fromLTWH(0, size.height * 0.2, size.width * 0.15, size.height * 0.6), linePaint);
    canvas.drawRect(Rect.fromLTWH(size.width * 0.85, size.height * 0.2, size.width * 0.15, size.height * 0.6), linePaint);

    // Draw Shots
    for (var shot in shots) {
      final shotPaint = Paint()
        ..color = _getShotColor(shot.result)
        ..style = PaintingStyle.fill;

      // x and y from backend are 0-100 percentages
      final dx = (shot.x / 100) * size.width;
      final dy = (shot.y / 100) * size.height;
      final radius = (shot.expectedGoals * 20).clamp(4.0, 15.0); // Size based on xG

      canvas.drawCircle(Offset(dx, dy), radius, shotPaint);
      
      // Draw border around shot
      canvas.drawCircle(Offset(dx, dy), radius, Paint()..color=Colors.white..style=PaintingStyle.stroke..strokeWidth=1);
    }
  }

  Color _getShotColor(String result) {
    switch (result) {
      case 'GOAL':
        return Colors.greenAccent;
      case 'SAVED':
        return Colors.orangeAccent;
      case 'MISSED':
        return Colors.redAccent;
      default:
        return Colors.white;
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
