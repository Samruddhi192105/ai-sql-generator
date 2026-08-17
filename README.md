AI SQL Generator

An AI-powered web application that converts natural-language requests into SQL queries using Java Spring Boot, Next.js, PostgreSQL, and Ollama.
The project is designed to let users describe what they want in plain English instead of writing SQL manually.

Features
-User registration and login

-JWT-based authentication

-Protected Dashboard, Query, and History pages

-Natural-language to SQL generation

-SQL explanation in simple language

-PostgreSQL database integration

-Query history

-AI-powered SQL generation using Ollama

-Qwen2.5-Coder 1.5B local model

-Dockerized frontend, backend, and PostgreSQL

-Docker Compose for running the application stack

-Ollama runs locally on Windows and is accessed by the backend container

Tech Stack

Backend

-Java 25

-Spring Boot

-Spring Web MVC

-Spring JDBC / JdbcTemplate

-Spring Security

-JWT

-PostgreSQL JDBC Driver

-JSQLParser

Frontend


-Next.js 16

-React 19

-Tailwind CSS

-JavaScript

Database

-PostgreSQL 17


AI

-Ollama

-Qwen2.5-Coder 1.5B


Development & DevOps

-Maven

-Docker

-Docker Compose

Git / GitHub

VS Code

Thunder Client

Architecture
<img width="1536" height="1024" alt="Architecture" src="https://github.com/user-attachments/assets/dc36a6d1-c5ad-4f1f-ac50-493088c14261" />

The backend connects to the Windows-hosted Ollama instance through: http://host.docker.internal:11434

Application Flow
<img width="1536" height="1024" alt="application flow" src="https://github.com/user-attachments/assets/f5b635c0-6266-41da-9462-f6e2a1e11fe0" />

Example

User request:
Show employees earning more than 50000
The application sends the request to the backend, which sends a structured prompt to Ollama.
A generated SQL statement can be:
SELECT * FROM employees WHERE salary > 50000;
The application can also provide a short explanation of what the generated SQL does.

Authentication
The application uses JWT-based authentication.
The flow is:

<img width="1536" height="1024" alt="authe" src="https://github.com/user-attachments/assets/4ab4c8ae-bcc5-4675-bbd3-4519142d551f" />

Running the Project Locally
Prerequisites
Install:
Java 25
Docker Desktop
Ollama
Git
Node.js / npm (needed for frontend development outside Docker)

The project uses:
qwen2.5-coder:1.5b

If it is not installed:
ollama pull qwen2.5-coder:1.5b

Start the application
From the project root:
docker compose up -d

Check the running containers:
docker compose ps

You should see:
ai-sql-postgres
ai-sql-backend
ai-sql-frontend

Ollama runs separately on the Windows host.

Open the application
Frontend:
http://localhost:3000

Backend:
http://localhost:8080

Useful Docker Commands
Build the application:
docker compose build

Build and start:
docker compose up -d --build

Check services:
docker compose ps

View backend logs:
docker compose logs backend --tail=100

View frontend logs:
docker compose logs frontend --tail=100

Stop the application:
docker compose down

Environment Configuration
The backend uses environment variables for database configuration:
DB_URL
DB_USERNAME
DB_PASSWORD

The frontend uses:
NEXT_PUBLIC_API_URL
Environment files containing secrets should not be committed to GitHub.

Screenshots:
<img width="959" height="410" alt="Screenshot 2026-08-17 164314" src="https://github.com/user-attachments/assets/5f3d97a7-6184-43a1-b6c9-e3ae711b5d83" />

<img width="688" height="412" alt="Screenshot 2026-08-17 164400" src="https://github.com/user-attachments/assets/9b2e0c26-1727-4342-a1f6-563efc881d8a" />

<img width="806" height="413" alt="Screenshot 2026-08-17 164421" src="https://github.com/user-attachments/assets/c33702c5-53e9-4b53-8713-fc15695fe479" />

<img width="809" height="410" alt="Screenshot 2026-08-17 164447" src="https://github.com/user-attachments/assets/abae86d0-6b4a-4fca-94f4-57dc33881599" />


Why Ollama?
-Ollama allows the AI model to run locally instead of requiring a paid cloud AI API.
This project therefore demonstrates integration with a locally hosted coding model while keeping the application usable without a paid AI API key.

Future Improvements
-Deploy the application to a cloud platform
-Add support for multiple AI providers
-Improve SQL validation and safety
-Add more detailed query history management
-Add automated CI/CD deployment
-Add automated backend and frontend tests
-Improve database schema-aware SQL generation

Author
-Developed as a project demonstrating:
-Java and Spring Boot
-REST APIs
-SQL and PostgreSQL
-JWT authentication
-Next.js
-AI integration
-Docker and Docker Compose
-Git and GitHub
