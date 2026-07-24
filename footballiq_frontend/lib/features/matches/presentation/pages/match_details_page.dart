import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:footballiq_frontend/features/matches/domain/entities/match_entity.dart';
import 'package:footballiq_frontend/features/matches/presentation/providers/match_provider.dart';
import 'package:footballiq_frontend/features/matches/presentation/widgets/heat_map_widget.dart';
import 'package:footballiq_frontend/features/matches/presentation/widgets/momentum_chart_widget.dart';
import 'package:footballiq_frontend/features/matches/presentation/widgets/shot_map_widget.dart';

class MatchDetailsPage extends ConsumerWidget {
  final MatchEntity match;

  const MatchDetailsPage({super.key, required this.match});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final shotsAsync = ref.watch(matchShotsProvider(match.id));
    final momentumAsync = ref.watch(matchMomentumProvider(match.id));
    final tacticalSummaryAsync = ref.watch(tacticalSummaryProvider(match.id));
    
    // For heatmap, hardcoding player p1 for demonstration
    final heatMapAsync = ref.watch(playerHeatMapProvider({'matchId': match.id, 'playerId': 'p1'}));

    return Scaffold(
      appBar: AppBar(
        title: Text('${match.homeTeam.shortName} vs ${match.awayTeam.shortName}'),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _buildSectionTitle(context, 'AI Tactical Summary', icon: Icons.auto_awesome),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: tacticalSummaryAsync.when(
                    data: (summary) => Text(summary, style: const TextStyle(fontSize: 16, height: 1.5)),
                    loading: () => const Center(child: CircularProgressIndicator()),
                    error: (e, st) => Text('Error: $e'),
                  ),
                ),
              ),
              const SizedBox(height: 24),

              _buildSectionTitle(context, 'Match Momentum', icon: Icons.show_chart),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: momentumAsync.when(
                    data: (data) => MomentumChartWidget(momentumData: data),
                    loading: () => const Center(child: CircularProgressIndicator()),
                    error: (e, st) => Text('Error: $e'),
                  ),
                ),
              ),
              const SizedBox(height: 24),
              
              _buildSectionTitle(context, 'Shot Map'),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: shotsAsync.when(
                    data: (data) => ShotMapWidget(shots: data),
                    loading: () => const Center(child: CircularProgressIndicator()),
                    error: (e, st) => Text('Error: $e'),
                  ),
                ),
              ),
              const SizedBox(height: 24),

              _buildSectionTitle(context, 'Player Heat Map (Vinicius Jr)'),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: heatMapAsync.when(
                    data: (data) => PlayerHeatMapWidget(heatMapData: data),
                    loading: () => const Center(child: CircularProgressIndicator()),
                    error: (e, st) => Text('Error: $e'),
                  ),
                ),
              ),
              const SizedBox(height: 32),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSectionTitle(BuildContext context, String title, {IconData? icon}) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8.0, left: 4.0),
      child: Row(
        children: [
          if (icon != null) ...[
            Icon(icon, color: Theme.of(context).primaryColor),
            const SizedBox(width: 8),
          ],
          Text(
            title,
            style: Theme.of(context).textTheme.titleLarge?.copyWith(
              fontWeight: FontWeight.bold,
              color: Theme.of(context).primaryColor,
            ),
          ),
        ],
      ),
    );
  }
}
