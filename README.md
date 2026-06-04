# REALMATCH Development Setup

This repository is scaffolded from the REALMATCH PDF design documents. It is
ready for implementation work, but it does not contain product feature logic yet.

## Backend

- Java 21
- Spring Boot 4.0.6
- Gradle wrapper
- PostgreSQL
- Flyway
- Redis support for queues, realtime routing, and dedupe/cache use cases

Run locally:

```bash
cd backend
docker compose up -d
./gradlew bootRun
```

Spring runs on `http://localhost:8080`.

Local infrastructure ports:

- PostgreSQL: `127.0.0.1:55432`
- Redis: `127.0.0.1:6380`

## Frontend

- React 19
- Vite
- `/api` proxy to the Spring backend

Run locally:

```bash
cd frontend
npm install
npm run dev
```

Vite runs on `http://localhost:5173`.

## Database Schema

Flyway migration:

```text
backend/src/main/resources/db/migration/V1__init_realmatch_schema.sql
```

The initial schema covers the document-backed development tables for:

- auth, social providers, devices, terms, profiles, preferences
- recommendations, reactions, matches, blocks, reports
- chat rooms, messages, read status, message reports
- random call queue, sessions, events, user call state
- coin products, payments, receipts, wallets, ledger, reservations, refunds
- community categories, posts, comments, likes, reports, hidden items
- admin users, roles, moderation cases, sanctions, action logs, audit logs
