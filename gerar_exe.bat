"C:\Program Files\Java\jdk-26\bin\jpackage" ^
--type exe ^
--name SoundCore ^
--input "C:\Users\emers\IdeaProjects\SoundCore" ^
--main-jar SoundCore.jar ^
--main-class Main ^
--icon "C:\Users\emers\IdeaProjects\SoundCore\src\Resource\SoundCore.ico" ^
--module-path "C:\Users\emers\Downloads\javafx-jmods-26.0.1" ^
--add-modules javafx.controls,javafx.media,javafx.swing,java.logging ^
--dest "C:\Users\emers\Desktop\SoundCoreBuild2"
pause