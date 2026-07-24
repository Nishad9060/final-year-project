class TeamEntity {
  final String id;
  final String name;
  final String logoUrl;
  final String shortName;

  TeamEntity({
    required this.id,
    required this.name,
    required this.logoUrl,
    required this.shortName,
  });
}

class MatchEntity {
  final String id;
  final TeamEntity homeTeam;
  final TeamEntity awayTeam;
  final DateTime startTime;
  final String status;
  final int homeScore;
  final int awayScore;
  final int minute;

  MatchEntity({
    required this.id,
    required this.homeTeam,
    required this.awayTeam,
    required this.startTime,
    required this.status,
    required this.homeScore,
    required this.awayScore,
    required this.minute,
  });
}
