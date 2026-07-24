import 'dart:convert';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:http/http.dart' as http;
import 'package:footballiq_frontend/features/matches/data/datasources/match_remote_data_source.dart';
import 'package:footballiq_frontend/features/matches/data/repositories/match_repository_impl.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_details_entity.dart';
import 'package:footballiq_frontend/features/matches/domain/repositories/match_repository.dart';

final matchRemoteDataSourceProvider = Provider<MatchRemoteDataSource>((ref) {
  return MatchRemoteDataSource();
});

final matchRepositoryProvider = Provider<MatchRepository>((ref) {
  final remoteDataSource = ref.watch(matchRemoteDataSourceProvider);
  return MatchRepositoryImpl(remoteDataSource);
});

final liveMatchesProvider = FutureProvider<List<MatchEntity>>((ref) async {
  final repository = ref.watch(matchRepositoryProvider);
  return repository.getLiveMatches();
});

final matchShotsProvider = FutureProvider.family<List<ShotEntity>, String>((ref, matchId) async {
  final dataSource = ref.watch(matchRemoteDataSourceProvider);
  return dataSource.getMatchShots(matchId);
});

final matchMomentumProvider = FutureProvider.family<List<MomentumEntity>, String>((ref, matchId) async {
  final dataSource = ref.watch(matchRemoteDataSourceProvider);
  return dataSource.getMatchMomentum(matchId);
});

final playerHeatMapProvider = FutureProvider.family<List<HeatMapEntity>, Map<String, String>>((ref, params) async {
  final dataSource = ref.watch(matchRemoteDataSourceProvider);
  return dataSource.getPlayerHeatMap(params['matchId']!, params['playerId']!);
});

final tacticalSummaryProvider = FutureProvider.family<String, String>((ref, matchId) async {
  final response = await http.post(
    Uri.parse('http://localhost:8080/api/v1/ai/summary/$matchId'),
    headers: {'Content-Type': 'application/json'},
    body: json.encode({"score": "2-1", "possession": 60}), // Dummy data payload
  );
  if (response.statusCode == 200) {
    return json.decode(response.body)['summary'];
  }
  return "Could not generate summary.";
});
