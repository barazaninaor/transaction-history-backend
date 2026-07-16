# השתמש בגרסת Java 17 (גרסה יציבה)
FROM openjdk:17-jdk-slim

# העתק את קובץ ה-JAR שנבנה על ידי Maven
COPY target/*.jar app.jar

# הרץ את האפליקציה
ENTRYPOINT ["java","-jar","/app.jar"]