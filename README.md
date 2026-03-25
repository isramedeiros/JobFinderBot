# 💼 JobFinder Bot

A simple Telegram bot that fetches remote job opportunities based on user input using the Jobicy API.

---

## 🚀 Features

* Search remote jobs by keyword (e.g. `java`, `backend`, `python`)
* Clean formatted results (title, company, link)

---

## 🧑‍💻 For Developers

### 1. Clone the project

```bash
git clone <your-repo-url>
cd jobfinder-bot
```

### 2. Requirements

* Java 17+
* Maven or Gradle

### 3. Dependencies

* OkHttp → HTTP client
* Jackson → JSON parsing
* Telegram Bots API

### 4. Environment variables

Create a `.env` file:

```
BOT_TOKEN=your_telegram_bot_token
```
You can create the bot using @BotFather. He will provide you a token.

### 5. Run the project

```bash
mvn clean install
mvn exec:java
```

(or run your main class directly in IntelliJ)

---

## 🧠 How it works

1. User sends a message (e.g. "java");
2. Bot calls Jobicy API;
3. API returns JSON;
4. Bot parses JSON;
5. Bot sends formatted jobs back.

Flow:

```
User → Bot → API → JSON → Parser → Message
```

---

## 🤖 For Users (Telegram)

### Start the bot

Open Telegram and search for @jobsbrasil_bot
https://t.me/jobsbrasil_bot

Send:

```
/start
```

You’ll receive instructions.

---

### Search for jobs

Just type what you’re looking for:

Examples:

```
java
backend
python
data engineer
```

---

### What you’ll get

Each result includes:

* 💼 Job title;
* 🏢 Company name;
* 🔗 Link to apply.

---

## ⚠️ Limitations

* Results depend on Jobicy API;
* Only remote jobs;
* User memory resets when the bot restarts.

---

## 📌 Future Improvements

* Save user preferences;
* Add filters (location, salary, stack);
* Pagination (more results);
* Database for persistent users.

---

## 🧑‍💻 Author

Built as a learning project to understand:

* API integration;
* JSON parsing;
* Telegram bot development.
