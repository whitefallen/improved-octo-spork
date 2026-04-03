# improved-octo-spork

A full-stack application for managing **Recipes**, **Songs**, and **Games** — built with Spring Boot, SQLite, and a React/TypeScript frontend, fully deployable via Docker Compose.

## Stack

| Layer      | Technology                                  |
|------------|---------------------------------------------|
| Backend    | Java 17, Spring Boot 3, Spring Data JPA     |
| Database   | SQLite (via Hibernate Community Dialects)   |
| Frontend   | React 18, TypeScript, Vite, React Router    |
| Deployment | Docker Compose                              |

## Modules

### 🍳 Recipes (Baking / Cooking)
- Create, edit, and delete recipes
- Fields: title, description, instructions, ingredients, frontend URL

### 🎵 Songs (Music)
- Create, edit, and delete songs
- Fields: title, artist, album, genre, duration

### 🎮 Games
- Create, edit, and delete games
- Fields: title, genre, platform, developer, release year, description

## REST API

All endpoints are prefixed with `/api`:

| Method | Endpoint            | Description         |
|--------|---------------------|---------------------|
| GET    | `/api/recipes`      | List all recipes    |
| GET    | `/api/recipes/{id}` | Get recipe by ID    |
| POST   | `/api/recipes`      | Create a recipe     |
| PUT    | `/api/recipes/{id}` | Update a recipe     |
| DELETE | `/api/recipes/{id}` | Delete a recipe     |
| GET    | `/api/songs`        | List all songs      |
| GET    | `/api/songs/{id}`   | Get song by ID      |
| POST   | `/api/songs`        | Create a song       |
| PUT    | `/api/songs/{id}`   | Update a song       |
| DELETE | `/api/songs/{id}`   | Delete a song       |
| GET    | `/api/games`        | List all games      |
| GET    | `/api/games/{id}`   | Get game by ID      |
| POST   | `/api/games`        | Create a game       |
| PUT    | `/api/games/{id}`   | Update a game       |
| DELETE | `/api/games/{id}`   | Delete a game       |

## Getting Started

### Run with Docker Compose (recommended)

```bash
docker compose up --build
```

- Frontend: http://localhost:80
- Backend API: http://localhost:8080

### Run locally

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

Frontend dev server: http://localhost:5173  
Backend API: http://localhost:8080

## Architecture

The backend follows **Domain-Driven Design** with a modular package structure:

```
com.example.app
├── config/          # CORS configuration
├── domain/          # Shared base entity
├── recipe/
│   ├── domain/      # Recipe entity
│   ├── repository/  # Spring Data JPA repository
│   ├── service/     # Service interface + implementation
│   └── controller/  # REST controller
├── song/            # Same structure as recipe
└── game/            # Same structure as recipe
```

Adding a new module: create a new package following the same pattern.

## Testing

```bash
cd backend
mvn test
```

21 unit tests covering all service operations for all three modules.

