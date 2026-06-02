# ⚡ Jodo

A fast, minimal task manager that lives in your terminal.

---

## Features

- Add, complete, and delete tasks
- Priority levels — LOW, MEDIUM, HIGH
- Filter tasks by status
- Persistent storage — tasks saved between sessions
- Coloured terminal output via Jansi
- Tab completion and command history via JLine
- Command pattern architecture — easily extendable

---

## Requirements

- Java 21+

---

## Installation

```bash
git clone https://github.com/yourusername/jodo.git
cd jodo
mvn package
```

Then run:

```bash
java -jar target/jodo.jar
```

Or on Windows use the included wrapper:

```bash
.\jodo.bat
```

---

## Commands

| Command | Description |
|---|---|
| `add <title>` | Add a new task |
| `done <id>` | Mark a task as done |
| `delete <id>` | Delete a task |
| `priority <id> <LOW\|MEDIUM\|HIGH>` | Set task priority |
| `list` | List all tasks |
| `help` | Show all commands |
| `exit` | Exit Jodo |

### Examples

```
add Buy groceries
done 1
delete 2
priority 3 HIGH
list
```

---

## Project Structure

```
jodo/
├── src/
│   ├── Main.java
│   ├── commands/
│   │   ├── AddTaskCommand.java
│   │   ├── CompleteTaskCommand.java
│   │   ├── DeleteTaskCommand.java
│   │   ├── ExitCommand.java
│   │   ├── HelpCommand.java
│   │   ├── ListTasksCommand.java
│   │   └── SetPriorityCommand.java
│   ├── enums/
│   │   └── Priority.java
│   ├── interfaces/
│   │   ├── Command.java
│   │   └── Persistable.java
│   ├── model/
│   │   ├── Task.java
│   │   └── PriorityTask.java
│   ├── parser/
│   │   └── CommandParser.java
│   └── service/
│       └── TaskService.java
├── jodo.bat
├── pom.xml
└── tasks.txt
```

---

## Architecture

Jodo follows a layered architecture:

```
Main → CommandParser → Command → TaskService → Task
```

- **Main** — terminal setup, JLine configuration, input loop
- **CommandParser** — parses raw string input into `Command` objects
- **Commands** — one class per command, each implements `Command` interface
- **TaskService** — all task logic, implements `Persistable`
- **Model** — `Task` abstract base class, `PriorityTask` subclass

### Design Patterns Used

- **Command Pattern** — each action is a self-contained class with `execute()`
- **Layered Architecture** — UI, logic, and data strictly separated
- **Interface Segregation** — `Command` and `Persistable` as focused contracts

---

## Built With

- Java 21
- Maven — dependency management and fat JAR packaging
- JLine 3.26.0 — terminal input, history, tab completion
- Jansi 2.4.0 — ANSI colour support on Windows

---

## Roadmap

This project is actively growing. Upcoming features:

- [ ] **Cloud Database Integration** — transition from flat file storage to a distributed relational or cloud database
- [ ] **JSON/YAML Export** — utility commands to export task queues into portable formats for workflow automation
- [ ] **Dynamic Tab Completion** — real-time ID scanning to auto-fill task IDs during tab completion
- [ ] **Custom UI Themes** — configuration files for user-defined 24-bit hex RGB colour palettes
- [ ] **Auth Backend** — multi-user support with JWT auth and REST API
- [ ] **Due Dates** — deadline tracking per task
