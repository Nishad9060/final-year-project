import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:footballiq_frontend/features/matches/data/models/match_model.dart';

class MatchRemoteDataSource {
  final String baseUrl = 'http://localhost:8080/api/v1/matches';

  Future<List<MatchModel>> getLiveMatches() async {
    final response = await http.get(Uri.parse('$baseUrl/live'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => MatchModel.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load live matches');
    }
  }

  Future<List<ShotEntity>> getMatchShots(String matchId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/shots'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => ShotEntity.fromJson(json)).toList();
    }
    throw Exception('Failed to load shots');
  }

  Future<List<MomentumEntity>> getMatchMomentum(String matchId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/momentum'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => MomentumEntity.fromJson(json)).toList();
    }
    throw Exception('Failed to load momentum');
  }

  Future<List<HeatMapEntity>> getPlayerHeatMap(String matchId, String playerId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/heatmap/$playerId'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => HeatMapEntity.fromJson(json)).toList();
    }
    throw Exception('Failed to load heat map');
  }
}
