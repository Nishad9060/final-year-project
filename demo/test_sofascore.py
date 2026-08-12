import urllib.request
import json
import ssl

ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

url = "https://api.sofascore.com/api/v1/search/events?q=Arsenal"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"})
try:
    resp = urllib.request.urlopen(req, context=ctx)
    data = json.loads(resp.read())
    print("Found events:")
    for result in data.get('results', [])[:5]:
        event = result.get('entity', {})
        print(f"ID: {event.get('id')}, Name: {event.get('slug')}")
except Exception as e:
    print(f"Error: {e}")
