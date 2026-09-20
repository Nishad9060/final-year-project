# Footonomy Backend — Local Setup

Spring Boot (Java 17) + PostgreSQL + Flyway backend for Footonomy V1. Read `docs/CLAUDE.md`
first if you haven't — it has the hard constraints this codebase follows.

## Prerequisites

- Java 17 (JDK). If your machine's default `java`/`mvn` resolve to a different version (check
  with `mvn -version`), point `JAVA_HOME` at a JDK 17 install for build/run commands, e.g.:
  `JAVA_HOME=/path/to/jdk-17 mvn spring-boot:run`. On macOS with multiple JDKs installed via the
  official installers, `/usr/libexec/java_home -V` lists them.
- Maven (no need to install separately — use the included `./mvnw` if present, otherwise a
  local `mvn` 3.9+)
- PostgreSQL 14+ running locally (or via Docker — see below)
- A free API key from [football-data.org](https://www.football-data.org/client/register) (needed
  for the sync job to actually pull data; the app still boots and serves cached-empty responses
  without one)

## 1. Database

Create a local Postgres database and user (adjust to taste — these are just the defaults baked
into `application.yml`):

```bash
createdb footonomy_db
psql footonomy_db -c "CREATE USER footonomy WITH PASSWORD 'footonomy';"
psql footonomy_db -c "GRANT ALL PRIVILEGES ON DATABASE footonomy_db TO footonomy;"
```

Or with Docker:

```bash
docker run --name footonomy-postgres -e POSTGRES_DB=footonomy_db \
  -e POSTGRES_USER=footonomy -e POSTGRES_PASSWORD=footonomy \
  -p 5432:5432 -d postgres:16
```

No manual migration step is needed — Flyway runs automatically against `db/migration/` on
application startup (`spring.flyway.enabled: true` in `application.yml`). To add a schema change
later, add a new `V<n>__description.sql` file in `src/main/resources/db/migration/`; never edit
an already-applied migration file.

## 2. Environment variables

All of these have working local defaults in `application.yml` except the football-data.org token,
which is only required if you want real sync data. **Never commit real values for any of these —
use your shell profile, a local `.env` you don't check in, or your platform's secret manager.**

| Variable | Default | Purpose |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/footonomy_db` | JDBC connection string |
| `DB_USERNAME` | `footonomy` | DB user |
| `DB_PASSWORD` | `footonomy` | DB password |
| `JWT_SECRET` | (insecure dev default) | HMAC signing key for JWTs — **set a real random value outside local dev** |
| `JWT_EXPIRATION_MS` | `86400000` (24h) | JWT lifetime |
| `FOOTBALL_DATA_API_TOKEN` | *(empty)* | Your football-data.org free-tier token |
| `FOOTBALL_DATA_BASE_URL` | `https://api.football-data.org/v4` | Rarely needs changing |
| `FOOTBALL_DATA_SYNC_INTERVAL_MS` | `420000` (7 min) | Sync job cadence — keep this high enough that 2 requests × 6 competitions stays under the 10 req/min free-tier cap (see `FootballDataRateLimiter`) |
| `FOOTBALL_DATA_SYNC_ENABLED` | `true` | Set `false` to disable the scheduled job entirely (e.g. local dev without a token) |
| `ALLOWED_ORIGINS` | `http://localhost:5173` | Comma-separated CORS origins allowed to call the API — set to your deployed frontend URL(s) |
| `SERVER_PORT` | `8080` | |

## 3. Running it

```bash
mvn spring-boot:run
```

or build a jar and run it directly:

```bash
mvn clean package
java -jar target/footonomy-be-0.1.0-SNAPSHOT.jar
```

The app starts on `http://localhost:8080`. Public endpoints (`/api/matches`, `/api/tournaments`,
`/api/search`, `/api/auth/**`) work immediately with no setup beyond the DB being up. `/api/users/me/**`
endpoints need a JWT from `POST /api/auth/signup` or `/login` first.

## 4. Tests

```bash
mvn test
```

Tests run against an in-memory H2 database (`src/test/resources/application-test.yml`) with
Flyway disabled and Hibernate managing the schema directly, so they don't need a running Postgres.
`SecurityBoundaryTest` is the one enforcing the public/auth boundary — keep it passing.

Note: the test H2 URL sets `NON_KEYWORDS=KEY,VALUE` because `key`/`value` (the Preference table's
column names, per the data dictionary) are reserved words in H2 but not in Postgres. If a future
migration adds another column name that collides with an H2 reserved word, add it to that list
rather than renaming the column.

## Notable implementation decisions (not in the original docs, or deviating from them)

- **Following is two tables, not one.** `docs/07_Data_Dictionary.md`'s original polymorphic
  `Following(entity_type, entity_id)` table was split into `FollowedTeam` and
  `FollowedTournament`, each with a real FK — this was the doc's own recommended fix for a known
  data-integrity gap. The public API shape didn't change. See the data dictionary and
  `V1__init_schema.sql` header comment for details.
- **Package base is `com.footonomy.*`**, even though `docs/CLAUDE.md` cautions against baking the
  placeholder app name into package names. This was confirmed explicitly rather than assumed — if
  the app gets a real name later, expect a repo-wide package rename.
- **Search never returns players.** There's no `Player` entity in the data dictionary and the SRS
  leaves player search as an unconfirmed open item; `GET /api/search` always returns an empty
  `players` array until that's resolved and a Player entity/table is added.
- **`POST /api/auth/logout` is a no-op success response.** JWTs here are stateless and there's no
  token blacklist/session store specified anywhere in the docs — the client is expected to discard
  the token. If server-side invalidation becomes a requirement, that's a real design addition, not
  a small tweak.
- **`PUT /api/users/me` only updates email.** The data dictionary's User table has no other
  editable profile fields, and there's no separate change-password endpoint in the frozen contract.

## Known gaps / open items carried over from the docs (not resolved here)

- Hosting platform (Railway vs Render) — undecided per `docs/03_TRD.md` §7.
- API contract owner — unassigned per `docs/03_TRD.md` §2.
- `docs/06_ER_Diagram.mermaid` still shows the old single-table `FOLLOWING` entity; it wasn't
  updated as part of this scaffold (only the data dictionary was, per instructions) — reconcile it
  when convenient.
- Frontend (React/Vite) isn't part of this repo/task.
