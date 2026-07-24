import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';

abstract class MatchRepository {
  Future<List<MatchEntity>> getLiveMatches();
}
