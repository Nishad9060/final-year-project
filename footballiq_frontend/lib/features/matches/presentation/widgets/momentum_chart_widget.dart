import 'package:flutter/material.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_details_entity.dart';

class MomentumChartWidget extends StatelessWidget {
  final List<MomentumEntity> momentumData;

  const MomentumChartWidget({super.key, required this.momentumData});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 120,
      padding: const EdgeInsets.symmetric(vertical: 16.0),
      child: CustomPaint(
        painter: MomentumPainter(data: momentumData),
      ),
    );
  }
}

class MomentumPainter extends CustomPainter {
  final List<MomentumEntity> data;

  MomentumPainter({required this.data});

  @override
  void paint(Canvas canvas, Size size) {
    if (data.isEmpty) return;

    final maxMinute = 90; // Assume 90 mins for now
    final barWidth = size.width / maxMinute;
    
    // Draw center line
    final linePaint = Paint()
      ..color = Colors.grey.withOpacity(0.5)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.0;
    final centerY = size.height / 2;
    canvas.drawLine(Offset(0, centerY), Offset(size.width, centerY), linePaint);

    final homePaint = Paint()..color = const Color(0xFF1DB954); // Green
    final awayPaint = Paint()..color = const Color(0xFFE53935); // Red

    for (var m in data) {
      if (m.minute > maxMinute) continue;
      
      final dx = m.minute * barWidth;
      // Value is between -50 and 50
      final normalizedHeight = (m.value.abs() / 50) * (size.height / 2);
      
      Paint paintToUse;
      Rect barRect;
      if (m.value > 0) {
        paintToUse = homePaint;
        barRect = Rect.fromLTWH(dx, centerY - normalizedHeight, barWidth - 1, normalizedHeight);
      } else {
        paintToUse = awayPaint;
        barRect = Rect.fromLTWH(dx, centerY, barWidth - 1, normalizedHeight);
      }
      
      canvas.drawRect(barRect, paintToUse);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
