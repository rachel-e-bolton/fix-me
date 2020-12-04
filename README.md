# Simulated FIX Crypto Market

- Building Project

```
mvn clean install / mvn clean package
```

## Router

```
java -jar router/target/router-1.0-SNAPSHOT.jar
```

## Market

```
java -jar market/target/market-1.0-SNAPSHOT.jar
```

## Broker

Simple broker client with console based ui to buy and sell instruments

```
java -jar broker/target/broker-1.0-SNAPSHOT.jar
```

## Reporting

Two simple reports to demonstrate the database logging that has been implemented.

```
java -jar reporting/target/reporting-1.0-SNAPSHOT.jar
```

## Simulation

Requires market and router to be running.

```
python3 run_simulation.py
```
