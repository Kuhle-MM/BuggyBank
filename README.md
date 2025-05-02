
# 🐞 BuggyBank – Personal Budget Tracker

![BuggyBank Banner]([https://via.placeholder.com/900x200?text=BuggyBank+%7C+Track+Your+Budget+Smartly](https://github.com/VCNMB/BuggyBank.git))

## 📱 Overview

**BuggyBank** is an Android budgeting app built with **Kotlin** and **Firebase**. It empowers users to track income, expenses, and category-specific budgets while visualizing their financial habits through interactive graphs and detailed reports.

---

## ✨ Features

### 🔐 Authentication
- ✅ Sign Up & Log In with Firebase Authentication
- 🔐 User data stored securely per UID

### 📊 Dashboard
- 💰 Overview of total budget
- 📉 Graph view of income vs. expenses
- 📝 Quick access to reports

### 📂 Transactions
- 👀 View all transactions
- 🔍 Filter by:
  - Income or Expense
  - Category
  - Custom date range
- 📸 View attached receipt or photo
- ✏️ Edit / 🗑 Delete entries

### ➕ Create Transaction
- Title, type, amount, date & time
- Category, payment method
- Optional image upload
- Description field

### 🧩 Categories
- Create custom **Income** and **Expense** categories
- Assign monthly budgets to categories

### 📈 Reports
- Auto-generated summaries by date range
- Total spending per category
- Export-friendly structure

---

## 🛠 Tech Stack

| Tool            | Use                                  |
|-----------------|---------------------------------------|
| Kotlin          | Core Android development              |
| Firebase Auth   | User authentication                   |
| Firebase DB     | Realtime data storage                 |
| Android Jetpack | UI & Lifecycle management             |

---

## 🗂 Project Structure

```
BuggyBank/
├── fragments/
│   ├── CreateTransactionFragment.kt
│   ├── ViewTransactionsFragment.kt
│   ├── CreateCategoryFragment.kt
│   ├── DashboardFragment.kt
│   └── ReportFragment.kt
├── adapters/
│   ├── TransactionAdapter.kt
│   └── CategoryAdapter.kt
├── models/
│   ├── Transaction.kt
│   ├── Category.kt
└── utils/
    └── FirebaseUtils.kt
```

---

## 🔧 Getting Started

1. 📥 Clone this repo:
   ```bash
   git clone https://github.com/yourusername/BuggyBank.git
   ```
2. 🛠 Open in Android Studio.
3. 🔑 Set up Firebase:
   - Create Firebase Project
   - Add `google-services.json` to `app/` directory
4. ▶️ Run the app on an emulator or physical device.


---

## 🧠 Future Improvements

- Cloud Storage for image uploads
- Monthly summary notifications
- Dark mode support
- Export to CSV/PDF

---

## 💬 Feedback & Contributions

Feel free to open [issues]([https://github.com/yourusername/BuggyBank/issues](https://github.com/VCNMB/BuggyBank.git)) or submit a pull request to suggest improvements or contribute!
