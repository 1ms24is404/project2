# EstateIQ

EstateIQ is a Spring Boot 3.5 backend for real-estate discovery, affordability analysis, and AI-assisted property recommendations.

## Problem Statement

Buying a property is a financial decision that mixes affordability, location desirability, investment upside, and long-term cost. EstateIQ helps users register securely, manage properties, calculate EMI and affordability, compare buy-vs-build decisions, and generate AI-backed recommendations.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT
- Swagger/OpenAPI
- Gemini API integration
- Maven
- Docker

## Architecture

```text
Client / Swagger UI
        |
        v
Controllers
  |-- AuthController
  |-- PropertyController
  |-- FinanceController
  |-- AIRecommendationController
        |
        v
Services
  |-- AuthService
  |-- PropertyService
  |-- FinanceService
  |-- AIRecommendationService
        |
        +--> Security/JWT
        +--> GeminiClient
        +--> Repositories
        |
        v
PostgreSQL
```

## Deployment & Secret Management

### 1. Local Setup with `.env`

To prevent committing sensitive keys to GitHub, EstateIQ uses environment variables. Follow these steps to configure your local environment:

1. Copy the `.env.example` template to create a `.env` file:
   ```bash
   copy .env.example .env
   ```
2. Open the `.env` file and set the required variables:
   - `JWT_SECRET`: A secure random string of at least 32 characters (e.g., `EstateIQSecretKey2026AtSecure!!!`).
   - `GEMINI_API_KEY`: Your Gemini API key from Google AI Studio.
3. Start the application using Docker Compose (which automatically reads the `.env` file):
   ```bash
   docker compose down
   docker compose up --build
   ```
4. Access the frontend app:
   ```text
   http://localhost:8081/index.html
   ```
5. Open Swagger UI:
   ```text
   http://localhost:8081/swagger-ui.html
   ```

### 2. Production Deployment (Render / Railway)

When deploying to cloud platforms like Render or Railway:

1. **Never Commit Secrets**: Do NOT commit your `.env` file to your GitHub repository. The `.gitignore` file is pre-configured to ignore `.env` so it stays safe on your local machine.
2. **Provision Database**: Set up a PostgreSQL database service on your hosting platform.
3. **Configure Environment Variables**: In your web application settings panel on Render/Railway, add the following environment variables:
   - `JWT_SECRET`: A unique, secure 32+ character key.
   - `GEMINI_API_KEY`: Your Google AI Studio key.
   - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`: Set these to map to your production database credentials.
4. **Deploy**: Build and run the project. The platform will inject the variables securely into the running container.

## API Endpoints

### Auth

`POST /api/v1/auth/register`

Request:

```json
{
  "name": "Asha",
  "email": "asha@example.com",
  "password": "secret123",
  "monthlySalary": 75000
}
```

Response:

```json
{
  "token": "eyJhbGciOi...",
  "email": "asha@example.com"
}
```

`POST /api/v1/auth/login`

Request:

```json
{
  "email": "asha@example.com",
  "password": "secret123"
}
```

Response:

```json
{
  "token": "eyJhbGciOi...",
  "email": "asha@example.com"
}
```

### Properties

`POST /api/v1/properties`

```json
{
  "title": "Skyline Residency",
  "location": "Bangalore",
  "builder": "ABC Builders",
  "propertyType": "FLAT",
  "price": 8500000,
  "bhk": 3,
  "area": 1450,
  "amenities": "Pool, Gym, Parking",
  "description": "Premium apartment in a prime location"
}
```

Response:

```json
{
  "id": 1,
  "title": "Skyline Residency",
  "location": "Bangalore",
  "builder": "ABC Builders",
  "propertyType": "FLAT",
  "price": 8500000,
  "bhk": 3,
  "area": 1450,
  "amenities": "Pool, Gym, Parking",
  "description": "Premium apartment in a prime location",
  "createdAt": "2026-07-16T10:15:30"
}
```

`GET /api/v1/properties/{id}`

`PUT /api/v1/properties/{id}`

`DELETE /api/v1/properties/{id}`

`GET /api/v1/properties?location=Bangalore&propertyType=FLAT&bhk=3&minPrice=5000000&maxPrice=10000000`

Response:

```json
{
  "content": [
    {
      "id": 1,
      "title": "Skyline Residency",
      "location": "Bangalore",
      "builder": "ABC Builders",
      "propertyType": "FLAT",
      "price": 8500000,
      "bhk": 3,
      "area": 1450,
      "amenities": "Pool, Gym, Parking",
      "description": "Premium apartment in a prime location",
      "createdAt": "2026-07-16T10:15:30"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1
}
```

### Finance

`POST /api/v1/finance/emi`

```json
{
  "principal": 1000000,
  "annualInterestRate": 12,
  "tenureMonths": 12
}
```

Response:

```json
{
  "emi": 88848.79,
  "totalAmountPayable": 1066185.48,
  "totalInterestPayable": 66185.48
}
```

`POST /api/v1/finance/compare`

```json
{
  "landCost": 2000000,
  "constructionCost": 1000000,
  "buyCost": 4000000,
  "registrationPercent": 5
}
```

Response:

```json
{
  "buildTotalCost": 3100000,
  "buyTotalCost": 4200000,
  "recommendation": "Building is financially better based on the provided costs."
}
```

`POST /api/v1/finance/affordability`

```json
{
  "monthlySalary": 100000,
  "emi": 20000,
  "downPayment": 300000,
  "propertyPrice": 1000000
}
```

Response:

```json
{
  "score": 100,
  "reasoning": "EMI-to-salary ratio is 20.0% and down payment coverage is 30.0%, resulting in an affordability score of 100."
}
```

### AI Recommendation

`POST /api/v1/recommend`

Request:

```json
{
  "monthlySalary": 100000,
  "budget": 9000000,
  "propertyPrice": 8500000,
  "emi": 65000,
  "purpose": "investment",
  "location": "Bangalore"
}
```

Response:

```json
{
  "recommendationText": "Based on the provided salary, budget, EMI, and location, this property appears to be a balanced choice for investment.",
  "affordabilityScore": 78,
  "investmentScore": 85
}
```

## Swagger UI

- Swagger UI: `/swagger-ui.html`
- OpenAPI docs: `/v3/api-docs`
- JWT authorization is available through the Swagger UI Authorize button.