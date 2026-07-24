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

  Future<List<ShotModel>> getMatchShots(String matchId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/shots'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => ShotModel.fromJson(json)).toList();
    }
    throw Exception('Failed to load shots');
  }

  Future<List<MomentumModel>> getMatchMomentum(String matchId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/momentum'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => MomentumModel.fromJson(json)).toList();
    }
    throw Exception('Failed to load momentum');
  }

  Future<List<HeatMapModel>> getPlayerHeatMap(String matchId, String playerId) async {
    final response = await http.get(Uri.parse('$baseUrl/$matchId/heatmap/$playerId'));
    if (response.statusCode == 200) {
      final List<dynamic> jsonList = json.decode(response.body);
      return jsonList.map((json) => HeatMapModel.fromJson(json)).toList();
    }
    throw Exception('Failed to load heat map');
  }
}

class ShotModel {
  // Define your shot properties here
  // This is a placeholder - adjust based on your API response
  
  ShotModel();
  
  factory ShotModel.fromJson(Map<String, dynamic> json) {
    return ShotModel();
  }
}

class MomentumModel {
  // Define your momentum properties here
  // This is a placeholder - adjust based on your API response
  
  MomentumModel();
  
  factory MomentumModel.fromJson(Map<String, dynamic> json) {
    return MomentumModel();
  }
}

class HeatMapModel {
  // Define your heat map properties here
  // This is a placeholder - adjust based on your API response
  
  HeatMapModel();
  
  factory HeatMapModel.fromJson(Map<String, dynamic> json) {
    return HeatMapModel();
  }
}
