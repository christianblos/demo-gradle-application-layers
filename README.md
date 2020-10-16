This repository shows how you can **separate application layers with the help of Gradle**.
The demo project contains three layers:

- application (Spring Boot Web Application)
- domain (Pure Business Logic)
- adapter (Implements interfaces of domain layer)

Each of the following examples is on its own branch!

## By folders

On the [folders](https://github.com/christianblos/demo-gradle-application-layers/tree/folders) branch,
there is just one folder per layer (without the help of Gradle).
This is meant as a starting point for the following examples.

```
project
  ├─ src/main/java/com/example/demo
  │    ├─ application
  │    ├─ domain
  │    └─ adapter
  └─ build.gradle
```

## Multi-Project build

On the [multi-project](https://github.com/christianblos/demo-gradle-application-layers/tree/multi-project) branch,
each layer is a sub project in a **Gradle Multi-Project**.

```
project
  ├─ server
  │    ├─ application
  │    │     ├─ src/main/java/com/example/demo/adapter/
  │    │     └─ build.gradle
  │    ├─ domain
  │    │     ├─ src/main/java/com/example/demo/domain/
  │    │     └─ build.gradle
  │    └─ adapter
  │          ├─ src/main/java/com/example/demo/adapter/
  │          └─ build.gradle
  ├─ build.gradle
  └─ settings.gradle
```

## Source Sets

On the [source-sets](https://github.com/christianblos/demo-gradle-application-layers/tree/source-sets) branch,
each layer is in its own source set in a single Gradle project.

```
project
  ├─ src
  │   ├─ application
  │   │     └─ java/com/example/demo/application/
  │   ├─ domain
  │   │     └─ java/com/example/demo/domain/
  │   └─ adapter
  │         └─ java/com/example/demo/adapter/
  └─ build.gradle
```
