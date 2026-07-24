import 'package:flutter/material.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_details_entity.dart';
import 'dart:ui' as ui;

class PlayerHeatMapWidget extends StatelessWidget {
  final List<HeatMapEntity> heatMapData;

  const PlayerHeatMapWidget({super.key, required this.heatMapData});

  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1.5,
      child: CustomPaint(
        painter: HeatMapPainter(data: heatMapData),
      ),
    );
  }
}

class HeatMapPainter extends CustomPainter {
  final List<HeatMapEntity> data;

  HeatMapPainter({required this.data});

  @override
  void paint(Canvas canvas, Size size) {
    // Draw basic pitch outline
    final pitchPaint = Paint()
      ..color = const Color(0xFF2E7D32)
      ..style = PaintingStyle.fill;
    canvas.drawRect(Rect.fromLTWH(0, 0, size.width, size.height), pitchPaint);

    final linePaint = Paint()
      ..color = Colors.white.withOpacity(0.3)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.0;
    canvas.drawLine(Offset(size.width / 2, 0), Offset(size.width / 2, size.height), linePaint);
    canvas.drawCircle(Offset(size.width / 2, size.height / 2), size.height / 5, linePaint);

    if (data.isEmpty) return;

    // Draw Heat Map blobs
    // We use a combination of radial gradients and blur to simulate heat
    for (var point in data) {
      final dx = (point.x / 100) * size.width;
      final dy = (point.y / 100) * size.height;
      final radius = 20.0 + (point.intensity * 2);

      final rect = Rect.fromCircle(center: Offset(dx, dy), radius: radius);
      
      final gradientPaint = Paint()
        ..shader = ui.Gradient.radial(
          Offset(dx, dy),
          radius,
          [
            Colors.redAccent.withOpacity(0.8),
            Colors.orangeAccent.withOpacity(0.5),
            Colors.yellowAccent.withOpacity(0.2),
            Colors.transparent
          ],
          [0.0, 0.4, 0.7, 1.0],
        )
        ..blendMode = BlendMode.screen;

      canvas.drawRect(rect, gradientPaint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
