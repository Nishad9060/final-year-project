import urllib.request
import json

LEAGUES = {
    "eng.1": "Premier League",
    "esp.1": "La Liga",
    "ger.1": "Bundesliga",
    "ita.1": "Serie A",
    "fra.1": "Ligue 1",
    "uefa.champions": "Champions League"
}

total = 0
for league_code, league_name in LEAGUES.items():
    url = f"https://site.api.espn.com/apis/site/v2/sports/soccer/{league_code}/scoreboard"
    try:
        req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
        resp = urllib.request.urlopen(req)
        data = json.loads(resp.read())
        events = data.get("events", [])
        total += len(events)
        print(f"\n=== {league_name} ({league_code}) - {len(events)} match(es) ===")
        for e in events[:5]:
            comps = e.get("competitions", [{}])
            competitors = comps[0].get("competitors", []) if comps else []
            home_team = away_team = "?"
            home_score = away_score = "0"
            for c in competitors:
                tn = c.get("team", {}).get("displayName", "?")
                sc = c.get("score", "0")
                if c.get("homeAway") == "home":
                    home_team, home_score = tn, sc
                else:
                    away_team, away_score = tn, sc
            status = e.get("status", {}).get("type", {}).get("description", "?")
            print(f"  {home_team} {home_score} - {away_score} {away_team}  [{status}]")
    except Exception as ex:
        print(f"\n=== {league_name} ({league_code}) - ERROR: {ex} ===")

print(f"\n{'='*50}")
print(f"TOTAL MATCHES ACROSS ALL LEAGUES: {total}")
