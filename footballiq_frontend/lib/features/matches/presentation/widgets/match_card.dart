import 'package:flutter/material.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';
import 'package:footballiq_frontend/features/matches/presentation/pages/match_details_page.dart';

class MatchCard extends StatelessWidget {
  final MatchEntity match;

  const MatchCard({super.key, required this.match});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => MatchDetailsPage(match: match),
          ),
        );
      },
      child: Card(
        margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _buildTeamInfo(match.homeTeam),
              _buildScoreInfo(context),
              _buildTeamInfo(match.awayTeam),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTeamInfo(TeamEntity team) {
    return Column(
      children: [
        // Using a placeholder icon for now since we don't have network images yet
        const CircleAvatar(
          backgroundColor: Colors.grey,
          child: Icon(Icons.sports_soccer, color: Colors.white),
        ),
        const SizedBox(height: 8),
        Text(
          team.shortName,
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
      ],
    );
  }

  Widget _buildScoreInfo(BuildContext context) {
    return Column(
      children: [
        Text(
          match.status == 'LIVE' ? '${match.minute}\'' : match.status,
          style: TextStyle(
            color: match.status == 'LIVE' ? Theme.of(context).primaryColor : Colors.grey,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 4),
        Text(
          '${match.homeScore} - ${match.awayScore}',
          style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                fontWeight: FontWeight.bold,
              ),
        ),
      ],
    );
  }
}
