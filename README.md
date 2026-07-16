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

## Local Setup

1. Set the required environment variables:

```bash
export JWT_SECRET="your-32-byte-or-longer-secret-key"
export GEMINI_API_KEY="your-gemini-api-key"
```

2. Start the app and database with Docker Compose:

```bash
docker compose up --build
```

3. Open Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Gemini API Key Setup

- Create a Gemini API key in Google AI Studio.
- Set it in your shell as `GEMINI_API_KEY`.
- The application reads it through `gemini.api.key` from environment variables, so the key is never hardcoded.

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