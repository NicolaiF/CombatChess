# CombatChess
The greatest game of chess ever made
## 
For å sette opp prosjektet i IntelliJ.

1. File -> Project From Existing Sources
2. Import model from external model -> Gradle
3. Huk av for auto-import
4. Next, next, finish

Om du får problem med Android SDK lokasjon,http://stackoverflow.com/questions/19794200/gradle-android-and-the-android-home-sdk-location
1. Opprett en fil "local.properties" i øverste mappe
2. Skriv inn "sdk.dir=/path/to/android/sdk" i denne filen
3. File -> Settings -> Path Variables -> Add new variable
4. Legg inn name:ANDROID_HOME og value:path/to/android/sdk