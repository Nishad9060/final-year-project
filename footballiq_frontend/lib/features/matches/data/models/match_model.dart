import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';

class MatchModel extends MatchEntity {
  MatchModel({
    required super.id,
    required super.homeTeam,
    required super.awayTeam,
    required super.startTime,
    required super.status,
    required super.homeScore,
    required super.awayScore,
    required super.minute,
  });

  factory MatchModel.fromJson(Map<String, dynamic> json) {
    return MatchModel(
      id: json['id'],
      homeTeam: TeamModel.fromJson(json['homeTeam']),
      awayTeam: TeamModel.fromJson(json['awayTeam']),
      startTime: DateTime.parse(json['startTime']),
      status: json['status'],
      homeScore: json['homeScore'],
      awayScore: json['awayScore'],
      minute: json['minute'],
    );
  }
}

class TeamModel extends TeamEntity {
  TeamModel({
    required super.id,
    required super.name,
    required super.logoUrl,
    required super.shortName,
  });

  factory TeamModel.fromJson(Map<String, dynamic> json) {
    return TeamModel(
      id: json['id'],
      name: json['name'],
      logoUrl: json['logoUrl'],
      shortName: json['shortName'],
    );
  }
}
