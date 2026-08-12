import urllib.request
import re
import json

url = "https://understat.com/match/26498"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"})
try:
    resp = urllib.request.urlopen(req)
    html = resp.read().decode('utf-8')
    
    # Extract shotsData
    match = re.search(r"var shotsData \= JSON.parse\('([^']+)'\)", html)
    if match:
        # The JSON inside is hex encoded, e.g. \x22 instead of "
        raw_json = match.group(1).encode('utf-8').decode('unicode_escape')
        data = json.loads(raw_json)
        print("Successfully extracted shotsData!")
        print("Home shots:", len(data.get('h', [])))
        print("Away shots:", len(data.get('a', [])))
        if data.get('h'):
            print("Sample shot:", data['h'][0])
    else:
        print("Could not find shotsData in HTML")
except Exception as e:
    print(f"Error: {e}")
