# AppMonteCarlo
**By:**

Raul Quigua,
Mateo Rubio,
Juan Camilo Tobar and
Sara Lucia Diaz Puerta


Using ZERO ICE we develop a solution for pi value that use Monte Carlo method, with an archichecture (Client-master-worker)

Set-up guide.

  - ./gradlew clean build
  - java -jar master/build/libs/master.jar
  - java -jar worker/build/libs/worker.jar
  - java -jar client/build/libs/client.jar

In that order

Then in client select the mode you want to use, singular gives you the immediate response of the points given depending on the workers available. Test mode allows you to give the points and the number of times you want to run it and the write that info on the file output.txt
