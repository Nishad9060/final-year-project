import 'package:footballiq_frontend/features/matches/data/datasources/match_remote_data_source.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';
import 'package:footballiq_frontend/features/matches/domain/repositories/match_repository.dart';

class MatchRepositoryImpl implements MatchRepository {
  final MatchRemoteDataSource remoteDataSource;

  MatchRepositoryImpl(this.remoteDataSource);

  @override
  Future<List<MatchEntity>> getLiveMatches() async {
    return await remoteDataSource.getLiveMatches();
  }
}
