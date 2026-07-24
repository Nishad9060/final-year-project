class ShotEntity {
  final String id;
  final int minute;
  final double x;
  final double y;
  final double expectedGoals;
  final String result;

  ShotEntity({
    required this.id,
    required this.minute,
    required this.x,
    required this.y,
    required this.expectedGoals,
    required this.result,
  });

  factory ShotEntity.fromJson(Map<String, dynamic> json) {
    return ShotEntity(
      id: json['id'],
      minute: json['minute'],
      x: json['x'].toDouble(),
      y: json['y'].toDouble(),
      expectedGoals: json['expectedGoals'].toDouble(),
      result: json['result'],
    );
  }
}

class MomentumEntity {
  final String id;
  final int minute;
  final int value;

  MomentumEntity({
    required this.id,
    required this.minute,
    required this.value,
  });

  factory MomentumEntity.fromJson(Map<String, dynamic> json) {
    return MomentumEntity(
      id: json['id'],
      minute: json['minute'],
      value: json['value'],
    );
  }
}

class HeatMapEntity {
  final String id;
  final double x;
  final double y;
  final int intensity;

  HeatMapEntity({
    required this.id,
    required this.x,
    required this.y,
    required this.intensity,
  });

  factory HeatMapEntity.fromJson(Map<String, dynamic> json) {
    return HeatMapEntity(
      id: json['id'],
      x: json['x'].toDouble(),
      y: json['y'].toDouble(),
      intensity: json['intensity'],
    );
  }
}
