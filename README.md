<div align="center">

<img src="https://img.shields.io/badge/Manzil-Your%20Guide%20to%20Islamabad-7B2D40?style=for-the-badge&logoColor=white" alt="Manzil Banner"/>

# 🗺️ MANZIL
### *Your Ultimate Guide to Islamabad*

[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![REST API](https://img.shields.io/badge/REST-API-7B2D40?style=flat-square)](https://restfulapi.net/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

> A travel and lifestyle platform that helps you discover the best cafés, parks, viewpoints, and hangout spots in your city — filtered by your mood, budget, and vibe.

</div>

---

## 📖 Table of Contents

- [About](#-about)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Database Design](#-database-design)
- [Schema Overview](#-schema-overview)
- [Normalization](#-normalization)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Team](#-team)

---

## 🌆 About

**Manzil** (منزل — meaning *destination* in Urdu) is a travel and lifestyle app built to help people explore and experience the best their city has to offer. Whether you're looking for a quiet café to study at, a scenic viewpoint to unwind, or a budget-friendly hangout spot with friends — Manzil has you covered.

The platform combines **location-based discovery**, **social recommendations**, and **personalized suggestions** into one seamless experience. Users can filter places by mood, vibe, and budget, and the system learns from their preferences to serve better recommendations over time.

> Built as a Database Systems (CS220) project, Manzil is designed with a rigorously normalized PostgreSQL backend and a Spring Boot REST API layer.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔍 **Multi-Filter Search** | Filter places by vibe, budget range, location, and available discounts |
| ⭐ **Ratings & Reviews** | Users can rate, review, and like reviews for any place |
| 🔖 **Bookmarks** | Save favourite spots to revisit later |
| 💡 **Personalized Recommendations** | Suggestions based on activity history, ratings, and preferences |
| 📈 **Trending Spots** | Dynamically highlighted popular and budget-friendly places |
| 🎟️ **Discount Aggregator** | Student and general discounts collected in one place |
| 📍 **Google Maps Integration** | Location data powered by Google Maps & Google Places API |
| 🛡️ **Admin Moderation** | Approve/reject place submissions, manage categories, vibes, and offers |
| 👤 **Guest Access** | Browse and get recommendations without signing up |
| ➕ **Suggest a Place** | Registered users can submit new places for admin review |

Discount Offers and Google Maps Integration yet to be implemented
---

## 🛠️ Tech Stack

### Database Management
| Tool | Purpose |
|---|---|
| **PostgreSQL** | Primary relational database — chosen for reliability and geospatial support |
| **pgAdmin** | GUI tool for schema design, query testing, and database administration |
| **SQL** | All data definition, manipulation, and querying |

### Backend Framework
| Tool | Purpose |
|---|---|
| **Java** | Core programming language |
| **Spring Boot** | Rapid, modular application development with minimal boilerplate |
| **REST APIs** | Communication layer between frontend clients and the database |

---

## 🗄️ Database Design

### Entity-Relationship Model

The Manzil ER model captures 6 core entities, 1 weak entity, 4 associative entities, 2 subclasses, and 1 multivalued attribute — all mapped to a 13-relation relational schema using a systematic 8-step ER-to-Relational mapping process.

> 📌 *ER Diagram image to be added here*

---

## 📐 Schema Overview

### Core Entities (7)

| Entity | Key Attributes | Relationships |
|---|---|---|
| **User** | user_id, email, password, activity_history | Recommendation (1:M), Place (M:M), Review (1:M) |
| **Place** | place_id, name, city, opening_hours, min_cost, max_cost, avg_rating | DiscountOffer (M:1), Vibe (M:M), Category (M:M), User (M:M) |
| **Review** | review_id, rating_score, comments, review_date, likes_count | User (M:1), Place (M:1) |
| **Category** | category_id, name, icon, description | Place (M:M) |
| **Vibe** | vibe_id, name, description | Place (M:M) |

### Weak Entity

| Entity | Identifying Entity | Composite PK |
|---|---|---|
| **PlaceImages** | Place | (Order, Place_ID) |

### Associative (Junction) Tables (3)

| Table | Resolves |
|---|---|
| **Bookmark** | RegisteredUser ↔ Place |
| **LikedPlaces** | RegisteredUser ↔ Place |
| **PlaceANDVibe** | Place ↔ Vibe |
| **PlaceCategory** | Place ↔ Category |

### Subclasses (2)

```
User
├── RegisteredUser  (preferences, join_date)
└── Admin           (role, permission)

DiscountOffer(Implementation in Future)
├── GroupDiscount        (min_group_size)
├── UniversityDiscount   (universityName, availableFor)
├── TimeBasedDiscount    (start_time, end_time)
└── PaymentDiscount      (payment_partner, cardType, cashback_amount)
```

### 8-Step ER-to-Relational Mapping

| Step | Type | Applied |
|---|---|---|
| 1 | Regular Entities | 6 core tables created |
| 2 | Weak Entities | PlaceImages table with composite PK |
| 3 | 1:1 Relationships | None — skipped |
| 4 | 1:N Relationships | FKs added to Place, Review, DiscountOffer |
| 5 | M:N Relationships | 3 junction tables: LikedPlaces, Bookmark, PlaceANDVibe, UserANDRecommendation |
| 6 | N-ary Relationships | None — skipped |
| 7 | Multivalued Attributes | UserName relation extracted from User |
| 8 | Generalization | 6 subclass tables (User → 2, DiscountOffer → 4) |

---

## ✅ Normalization

All 13 relations satisfy **BCNF** — the highest standard of relational normalization.

| Normal Form | Status | Notes |
|---|---|---|
| **1NF** | ✅ Satisfied | UserName extracted to separate relation to eliminate composite multivalued attribute |
| **2NF** | ✅ Satisfied | No partial dependencies — all non-key attributes depend on the full composite key |
| **3NF** | ✅ Satisfied | No transitive dependencies — all non-key attributes depend directly on the PK |
| **BCNF** | ✅ Satisfied | Every determinant is a candidate key — no non-trivial FD violations |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- PostgreSQL 15+
- pgAdmin (optional, for GUI access)
- Maven

### Setup

```bash
# 1. Clone the repository
git clone https://github.com/your-username/manzil.git
cd manzil

# 2. Create the PostgreSQL database
psql -U postgres -c "CREATE DATABASE manzil;"

# 3. Run the schema SQL
psql -U postgres -d manzil -f db/schema.sql

# 4. (Optional) Seed sample data
psql -U postgres -d manzil -f db/seed.sql

# 5. Configure your database connection
# Edit src/main/resources/application.properties:
# spring.datasource.url=jdbc:postgresql://localhost:5432/manzil
# spring.datasource.username=your_username
# spring.datasource.password=your_password

# 6. Build and run
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 📁 Project Structure

```
manzil/
├── db/
│   ├── schema.sql          # Full DDL — all 18 relations
│   ├── seed.sql            # Sample data for testing
│   └── er-diagram.png      # ER Diagram
├── src/
│   └── main/
│       ├── java/com/manzil/
│       │   ├── controllers/    # REST API endpoints
│       │   ├── models/         # Entity classes
│       │   ├── repositories/   # Database access layer
│       │   └── services/       # Business logic
│       └── resources/
│           └── application.properties
├── README.md
└── pom.xml
```

---

## 🔮 Future Enhancements

- [ ] Expand coverage to all major cities across Pakistan
- [ ] AI-powered personalized travel recommendations
- [ ] Real-time booking and in-app navigation
- [ ] Business owner dashboards for managing listings and offers
- [ ] Advanced database security — RBAC and row-level security
- [ ] Query optimization with composite indexes
- [ ] Backup, recovery, and replication for high availability
- [ ] Real-time analytics and live trending data

---

## 👥 Team

| Name | Role |
|---|---|
| **Maisam Samir** | Database Design, Backend |
| **Manahel Zulqarnain** | Database Design, Frontend |
| **Muhammad Bilal** | Database Design, Backend |

> *Database Systems — CS220 | 20 May, 2026*

---

<div align="center">

Made for Pakistan in Pakistan
[Manzil Demo](https://manzil-production-73bf.up.railway.app)</div>
