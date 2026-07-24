import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:footballiq_frontend/features/matches/presentation/providers/match_provider.dart';
import 'package:footballiq_frontend/features/matches/presentation/widgets/match_card.dart';
import 'package:footballiq_frontend/features/assistant/presentation/pages/assistant_page.dart';

class HomePage extends ConsumerWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final liveMatchesAsyncValue = ref.watch(liveMatchesProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('FootballIQ', style: TextStyle(fontWeight: FontWeight.bold)),
        actions: [
          IconButton(
            icon: const Icon(Icons.smart_toy), // AI icon
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const AssistantPage()),
              );
            },
          ),
          IconButton(
            icon: const Icon(Icons.search),
            onPressed: () {},
          )
        ],
      ),
      body: liveMatchesAsyncValue.when(
        data: (matches) {
          if (matches.isEmpty) {
            return const Center(child: Text('No live matches'));
          }
          return RefreshIndicator(
            onRefresh: () => ref.refresh(liveMatchesProvider.future),
            child: ListView.builder(
              itemCount: matches.length,
              itemBuilder: (context, index) {
                return MatchCard(match: matches[index]);
              },
            ),
          );
        },
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (error, stack) => Center(child: Text('Error: $error')),
      ),
    );
  }
}
