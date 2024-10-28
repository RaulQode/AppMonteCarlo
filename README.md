# AppMonteCarlo
Using ZERO ICE we develop a solution for pi value that use Monte Carlo method, with an archichecture (Client-master-worker)

Set-up guide.

  - Git checkout feat/worker
  - ./gradlew clean build
  - java -jar master/build/libs/master.jar
  - java -jar worker/build/libs/worker.jar
  - java -jar client/build/libs/client.jar

In that order
